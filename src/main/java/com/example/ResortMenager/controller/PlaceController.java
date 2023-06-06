package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.GuestDTO;
import com.example.ResortMenager.DTO.PlaceDTO;
import com.example.ResortMenager.DTO.PlaceProjectionDTO;
import com.example.ResortMenager.DTO.ReservationDTO;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.service.PlaceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/places")
@AllArgsConstructor
public class PlaceController {
    private final PlaceService placeService;
    @GetMapping
    public List<PlaceProjectionDTO> getPlaces(){
        List<PlaceProjectionDTO> placeProjectionDTOS = new ArrayList<>();
        List<Place> places = placeService.getPlaces();
        for(Place place : places){
            placeProjectionDTOS.add((new PlaceProjectionDTO(place)));
        }
        return placeProjectionDTOS;
    }
    @GetMapping(path = "{placeId}")
    public PlaceProjectionDTO getPlace(@PathVariable("placeId") Long placeId){
        List<Place> places = placeService.getPlaces();
        for(Place place : places){
            if(place.getId() == placeId)
                return new PlaceProjectionDTO(place);
        }
        return null;
    }
    @PostMapping
    public void addPlace(@RequestBody PlaceDTO placeDTO) {
        Place place = new Place(placeDTO.getName(), placeDTO.getCity(),placeDTO.getPostalCode(),placeDTO.getStreet(),
                placeDTO.getNumber(),placeDTO.getMaxGuestsNumber());
        placeService.savePlace(place);

    }
    @PutMapping(path = "{placeId}")
    public void updatePlace(@RequestBody PlaceDTO placeDTO, @PathVariable ("placeId") long placeId) {
        List<Place> places = placeService.getPlaces();
        for(Place place : places){
            if(place.getId() == placeId){
                place.setName(placeDTO.getName());
                place.setCity(placeDTO.getCity());
                place.setPostalCode(placeDTO.getPostalCode());
                place.setStreet(placeDTO.getStreet());
                place.setNumber(placeDTO.getNumber());
                place.setMaxGuestsNumber(placeDTO.getMaxGuestsNumber());
                placeService.savePlace(place);
            }
        }
    }
    @DeleteMapping(path = "{placeId}")
    public void deletePlace(@PathVariable ("placeId") Long placeId ){
        placeService.deletePlace(placeId);
    }
}
