package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    public List<Place> getPlaces(){
        return placeRepository.findAll();
    }
    public void savePlace(Place place){
        placeRepository.save(place);
    }
}
