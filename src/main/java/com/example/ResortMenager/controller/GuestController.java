package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.GuestDTO;
import com.example.ResortMenager.DTO.GuestProjectionDTO;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.util.UserRepresentationGuest;
import com.example.ResortMenager.exception.ApiRequestException;
import com.example.ResortMenager.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

//import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {
    private final Keycloak keycloak;
    private final GuestService guestService;
    public List<String> getRoles(@AuthenticationPrincipal Jwt principal){
        Map<String,Object> objectMap = principal.getClaims();
        try {
            Map<String,Object> object = (Map<String, java.lang.Object>) objectMap.get("resource_access");
            Map<String,Object> elements = (Map<String, java.lang.Object>) object.get("resortManager-rest-api");
            List<String> roles = (List<String>) elements.get("roles");
            return roles;
        } catch (Exception e){
            return null;
        }
    }
    @GetMapping
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<List<GuestProjectionDTO>> getGuests(@AuthenticationPrincipal Jwt principal){
        List<Guest> guests = guestService.getGuests();
        List<GuestProjectionDTO> guestProjectionDTOS= new ArrayList<>();
        for(Guest guest : guests){
            GuestProjectionDTO guestProjectionDTO = new GuestProjectionDTO(guest);
            guestProjectionDTOS.add(guestProjectionDTO);
        }
        return new ResponseEntity<>(guestProjectionDTOS, HttpStatus.OK);
    }
    @GetMapping(path = "{guestId}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<GuestProjectionDTO> getGuest(@AuthenticationPrincipal Jwt principal,
                                                       @PathVariable ("guestId") long guestId){
        Guest guest = guestService.findById(guestId);
        if(guest.getEmail().equals(principal.getClaims().get("email")) || getRoles(principal).contains("client_admin")){
            return new ResponseEntity<>(new GuestProjectionDTO(guest), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<String> addGuest(@Valid @RequestBody GuestDTO guestDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            throw new ApiRequestException(errorMessage.toString());
        }
        Guest guest = new Guest(guestDTO.getName(), guestDTO.getSurrname(), guestDTO.getEmail());
        UserRepresentationGuest userRepresentationGuest = new UserRepresentationGuest(guestDTO,
                guestService.getGuests().size());
        try(Response response = keycloak.realm("resortManagerKeyCloak")
                .users().create(userRepresentationGuest)) {
            if (response.getStatus() == 201) {
                guestService.saveGuest(guest);
                return ResponseEntity.ok("User created successfully");
            } else {
                return ResponseEntity.status(response.getStatus()).body(response.getStatusInfo().getReasonPhrase());
            }
        }
    }
    @PutMapping(path = "{guestId}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<?> updateGuest(@AuthenticationPrincipal Jwt principal,
                                                  @Valid @RequestBody GuestDTO guestDTO,BindingResult result,
                                                  @PathVariable ("guestId") long guestId) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            throw new ApiRequestException(errorMessage.toString());
        }
        Guest guest = guestService.findById(guestId);
        if(guest.getEmail().equals(principal.getClaims().get("email")) || getRoles(principal).contains("client_admin")){
            UserRepresentationGuest userRepresentationGuest = new UserRepresentationGuest(guestDTO,
                    guestService.getGuests().size());
            List<UserRepresentation> userRepresentations =
                    keycloak.realm("resortManagerKeyCloak").users().searchByEmail(
                            guestService.findById(guestId).getEmail(), true);
            System.out.println("PRzed resotMaango");
            try (Response response1 = keycloak.realm("resortManagerKeyCloak")
                    .users().delete(userRepresentations.get(0).getId())) {
                if (response1.getStatus() == 204) {
                    guestService.deleteGuest(guestId);
                } else {
                    return ResponseEntity.status(response1
                            .getStatus()).body(response1.getStatusInfo().getReasonPhrase());
                }
            }
            try (Response response = keycloak.realm("resortManagerKeyCloak")
                    .users().create(userRepresentationGuest)) {
                if (response.getStatus() == 201) {
                    guest.setName(guestDTO.getName());
                    guest.setSurrname(guestDTO.getSurrname());
                    guest.setEmail(guestDTO.getEmail());
                    guestService.saveGuest(guest);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return ResponseEntity.status(response
                            .getStatus()).body(response.getStatusInfo().getReasonPhrase());
                }
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping(path = "{guestId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<?> deleteGuest(@PathVariable ("guestId") Long guestId){
        List<UserRepresentation> userRepresentations =
                keycloak.realm("resortManagerKeyCloak").users().searchByEmail(
                        guestService.findById(guestId).getEmail(),true);
        try(Response response =keycloak.realm("resortManagerKeyCloak")
                .users().delete(userRepresentations.get(0).getId())) {
            if (response.getStatus() == 204) {
                guestService.deleteGuest(guestId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.status(response.getStatus()).body(response.getStatusInfo().getReasonPhrase());
            }
        }
    }
}