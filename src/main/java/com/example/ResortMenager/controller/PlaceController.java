package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.PlaceProjectionDTO;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.service.PlaceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
