package com.example.ResortMenager.controller;

import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.service.PlaceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@AllArgsConstructor
public class PlaceController {
    private final PlaceService placeService;
    @GetMapping
    public List<Place> getPlaces(){
        return placeService.getPlaces();
    }
}
