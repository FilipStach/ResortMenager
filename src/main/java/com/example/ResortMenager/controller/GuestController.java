package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.GuestDTO;
import com.example.ResortMenager.DTO.GuestProjectionDTO;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.exception.ApiRequestException;
import com.example.ResortMenager.service.GuestService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/guests")
@AllArgsConstructor
public class GuestController {

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
    private final GuestService guestService;
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
    public ResponseEntity<HttpStatus> addGuest(@Valid @RequestBody GuestDTO guestDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            throw new ApiRequestException(errorMessage.toString());
        }
        Guest guest = new Guest(guestDTO.getName(), guestDTO.getSurrname(), guestDTO.getEmail());

        guestService.saveGuest(guest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(path = "{guestId}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<HttpStatus> updateGuest(@AuthenticationPrincipal Jwt principal,
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
            guest.setName(guestDTO.getName());
            guest.setSurrname(guestDTO.getSurrname());
            guest.setEmail(guestDTO.getEmail());
            guestService.saveGuest(guest);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping(path = "{guestId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<HttpStatus> deleteGuest(@PathVariable ("guestId") Long guestId){
        guestService.deleteGuest(guestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}