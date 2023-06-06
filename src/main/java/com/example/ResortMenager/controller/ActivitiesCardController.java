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
import lombok.AllArgsConstructor;
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
    public List<ActivitiesCardProjectionDTO> getActivitiesCards(){
        List<ActivitiesCard> activitiesCards = activitiesCardService.getActivitiesCards();
        List<ActivitiesCardProjectionDTO> activitiesCardProjectionDTOS = new ArrayList<>();
        for (ActivitiesCard activitiesCard : activitiesCards){
            activitiesCardProjectionDTOS.add(new ActivitiesCardProjectionDTO(activitiesCard));
        }
        return activitiesCardProjectionDTOS;
    }
    @GetMapping(path = "{activitiesCardsId}")
    public ActivitiesCardProjectionDTO getActivitiesCard(@PathVariable("activitiesCardsId") Long activitiesCardsId){
        List<ActivitiesCard> activitiesCards = activitiesCardService.getActivitiesCards();
        for(ActivitiesCard activitiesCard : activitiesCards){
            if(activitiesCard.getId() == activitiesCardsId)
                return new ActivitiesCardProjectionDTO(activitiesCard);
        }
        return null;
    }
    @PutMapping(path = "{activitiesCardId}")
    public void updateActivitiesCard(@PathVariable("activitiesCardId") Long activitiesCardId,
                                     @RequestBody ActivitiesCardDTO activitiesCardDTO){
        List<ActivitiesCard> activitiesCards = activitiesCardService.getActivitiesCards();
        for(ActivitiesCard activitiesCard : activitiesCards){
            activitiesCard.setActivitiesLeft(activitiesCardDTO.getActivitiesLeft());
            activitiesCardService.save(activitiesCard);
        }
    }
    @DeleteMapping(path = "{activitiesCardId}")
    public void deleteActivitiesCard(@PathVariable("activitiesCardId") Long activitiesCardId){
        activitiesCardService.deleteActivitiesCard(activitiesCardId);
    }
}
