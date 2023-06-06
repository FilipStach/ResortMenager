package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.PlaceBooking;
import com.example.ResortMenager.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ReservationService reservationService;
    public List<Place> getPlaces(){
        return placeRepository.findAll();
    }
    public void savePlace(Place place){
        placeRepository.save(place);
    }
    public Place findById(Long id){
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        return place;
    }
    public void deletePlace(Long placeId){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        List<PlaceBooking> placeBookings = place.getPlaceBookings();
        for(PlaceBooking placeBooking : placeBookings){
            reservationService.deleteReservation(placeBooking.getReservation().getId());
        }
        placeRepository.delete(place);
    }
}
