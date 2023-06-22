package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.*;
import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.service.ActivitiesCardService;
import com.example.ResortMenager.service.PlaceService;
import com.example.ResortMenager.service.ReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/activitiesCards")
public class ActivitiesCardController {
    private final ActivitiesCardService activitiesCardService;
    private final PlaceService placeService;
    private final ReservationService reservationService;

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
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<List<ActivitiesCardProjectionDTO>> getActivitiesCards(@AuthenticationPrincipal Jwt principal){
        List<ActivitiesCard> activitiesCards = activitiesCardService.getActivitiesCards();
        List<ActivitiesCardProjectionDTO> activitiesCardProjectionDTOS = new ArrayList<>();
        if(getRoles(principal).contains("client_admin")){
            for (ActivitiesCard activitiesCard : activitiesCards){
                activitiesCardProjectionDTOS.add(new ActivitiesCardProjectionDTO(activitiesCard));
            }
            return new ResponseEntity<>(activitiesCardProjectionDTOS, HttpStatus.OK);
        }
        else {
            for (ActivitiesCard activitiesCard : activitiesCards){
                if(activitiesCard.getReservation().getGuest().getEmail().equals(principal.getClaims().get("email")))
                    activitiesCardProjectionDTOS.add(new ActivitiesCardProjectionDTO(activitiesCard));
            }
            if(activitiesCardProjectionDTOS.size()==0)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(activitiesCardProjectionDTOS, HttpStatus.OK);
        }
    }
    @GetMapping(path = "{activitiesCardsId}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<ActivitiesCardProjectionDTO> getActivitiesCard(
            @PathVariable("activitiesCardsId") Long activitiesCardsId, @AuthenticationPrincipal Jwt principal){
        ActivitiesCard activitiesCard = activitiesCardService.findById(activitiesCardsId);
        if(getRoles(principal).contains("client_admin") ||
                activitiesCard.getReservation().getGuest().getEmail().equals(principal.getClaims().get("email"))) {
            return new ResponseEntity<>(new ActivitiesCardProjectionDTO(activitiesCard), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping(path = "{activitiesCardId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<HttpStatus> updateActivitiesCard(@Valid @PathVariable("activitiesCardId") Long activitiesCardId,
                                     BindingResult result, @RequestBody ActivitiesCardDTO activitiesCardDTO){
        if (result.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            for(FieldError error : result.getFieldErrors()){
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
        }
        List<ActivitiesCard> activitiesCards = activitiesCardService.getActivitiesCards();
        for(ActivitiesCard activitiesCard : activitiesCards){
            activitiesCard.setActivitiesLeft(activitiesCardDTO.getActivitiesLeft());
            activitiesCardService.save(activitiesCard);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(path = "{activitiesCardId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<HttpStatus> deleteActivitiesCard(@PathVariable("activitiesCardId") Long activitiesCardId){
        activitiesCardService.deleteActivitiesCard(activitiesCardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
