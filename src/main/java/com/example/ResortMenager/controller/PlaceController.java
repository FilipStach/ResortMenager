package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.GuestDTO;
import com.example.ResortMenager.DTO.PlaceDTO;
import com.example.ResortMenager.DTO.PlaceProjectionDTO;
import com.example.ResortMenager.DTO.ReservationDTO;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.exception.ApiRequestException;
import com.example.ResortMenager.service.PlaceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/places")
@AllArgsConstructor
public class PlaceController {
    private final PlaceService placeService;
    @GetMapping
    public ResponseEntity<List<PlaceProjectionDTO>> getPlaces(){
        List<PlaceProjectionDTO> placeProjectionDTOS = new ArrayList<>();
        List<Place> places = placeService.getPlaces();
        for(Place place : places){
            placeProjectionDTOS.add((new PlaceProjectionDTO(place)));
        }
        return new ResponseEntity<>(placeProjectionDTOS, HttpStatus.OK);
    }
    @GetMapping(path = "{placeId}")
    public ResponseEntity<PlaceProjectionDTO> getPlace(@PathVariable("placeId") Long placeId){
        List<Place> places = placeService.getPlaces();
        for(Place place : places){
            if(place.getId() == placeId)
                return new ResponseEntity<>(new PlaceProjectionDTO(place), HttpStatus.OK);
        }
        return null;
    }
    @PostMapping
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<HttpStatus> addPlace(@Valid @RequestBody PlaceDTO placeDTO, BindingResult result) {
        if(result.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            throw new ApiRequestException(errorMessage.toString());
        }
        Place place = new Place(placeDTO.getName(), placeDTO.getCity(),placeDTO.getPostalCode(),placeDTO.getStreet(),
                placeDTO.getNumber(),placeDTO.getMaxGuestsNumber());
        placeService.savePlace(place);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping(path = "{placeId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<HttpStatus> updatePlace(@Valid @RequestBody PlaceDTO placeDTO,BindingResult result,
                            @PathVariable ("placeId") long placeId) {
        if(result.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            for(FieldError error : result.getFieldErrors()){
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
        }
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(path = "{placeId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<HttpStatus> deletePlace(@PathVariable ("placeId") Long placeId ){
        placeService.deletePlace(placeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
