package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.ActivitiesCardDTO;
import com.example.ResortMenager.DTO.ActivitiesCardProjectionDTO;
import com.example.ResortMenager.DTO.PlaceDTO;
import com.example.ResortMenager.DTO.ReservationProjectionDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/activitiesCards")
public class ActivitiesCardController {
    private final ActivitiesCardService activitiesCardService;
    private final PlaceService placeService;
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ActivitiesCardProjectionDTO>> getActivitiesCards(){
        List<ActivitiesCard> activitiesCards = activitiesCardService.getActivitiesCards();
        List<ActivitiesCardProjectionDTO> activitiesCardProjectionDTOS = new ArrayList<>();
        for (ActivitiesCard activitiesCard : activitiesCards){
            activitiesCardProjectionDTOS.add(new ActivitiesCardProjectionDTO(activitiesCard));
        }
        return new ResponseEntity<>(activitiesCardProjectionDTOS, HttpStatus.OK);
    }
    @GetMapping(path = "{activitiesCardsId}")
    public ResponseEntity<ActivitiesCardProjectionDTO> getActivitiesCard(
            @PathVariable("activitiesCardsId") Long activitiesCardsId){
                ActivitiesCard activitiesCard = activitiesCardService.findById(activitiesCardsId);
        return new ResponseEntity<>(new ActivitiesCardProjectionDTO(activitiesCard), HttpStatus.OK);
    }
    @PutMapping(path = "{activitiesCardId}")
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
    public ResponseEntity<HttpStatus> deleteActivitiesCard(@PathVariable("activitiesCardId") Long activitiesCardId){
        activitiesCardService.deleteActivitiesCard(activitiesCardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
