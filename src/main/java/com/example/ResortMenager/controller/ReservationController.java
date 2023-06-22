package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.*;
import com.example.ResortMenager.domain.*;
import com.example.ResortMenager.exception.ApiRequestException;
import com.example.ResortMenager.service.GuestService;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final PlaceService placeService;
    private final GuestService guestService;
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
    public ResponseEntity<List<ReservationProjectionDTO>> getReservations(@AuthenticationPrincipal Jwt principal){
        List<Reservation> reservations = reservationService.getReservations();
        List<ReservationProjectionDTO> reservationProjectionDTOS = new ArrayList<>();
        for(Reservation reservation : reservations){
            if(reservation.getGuest().getEmail().equals(principal.getClaims().get("email")) ||
                    getRoles(principal).contains("client_admin")){
                reservationProjectionDTOS.add(new ReservationProjectionDTO(reservation));
            }
        }
        if(reservationProjectionDTOS.size()==0){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(reservationProjectionDTOS, HttpStatus.OK);
    }
    @GetMapping(path = "{reservationId}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<ReservationProjectionDTO> getReservation(@AuthenticationPrincipal Jwt principal,
                                                                   @PathVariable("reservationId") Long reservationId){

        Reservation reservation = reservationService.findById(reservationId);
        if(reservation.getGuest().getEmail().equals(principal.getClaims().get("email")) ||
                getRoles(principal).contains("client_admin")){
            return new ResponseEntity<>(new ReservationProjectionDTO(reservation), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<HttpStatus> addReservation(@Valid @RequestBody ReservationDTO reservationDTO,
                                                     BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            for (ObjectError error : result.getGlobalErrors()) {
                errorMessage.append(error.getObjectName()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            throw new ApiRequestException(errorMessage.toString());
        }
        Reservation reservation = new Reservation(reservationDTO.getNumberOfAdults(),reservationDTO.getNumberOfKids(),
                reservationDTO.getArrivalDate(),reservationDTO.getDepartureDate());
        PlaceBooking placeBooking = new PlaceBooking(placeService.findById(reservationDTO.getPlaceId()), reservation);
        reservation.setGuest(guestService.findById(reservationDTO.getGuestId()));
        reservation.addPlaceBooking(placeBooking);
        ActivitiesCard activitiesCard = new ActivitiesCard(placeBooking.getPlace(), reservation);
        reservation.addActivitesCard(activitiesCard);
        placeBooking.getPlace().addPlaceBooking(placeBooking);
        placeBooking.getPlace().addActivitesCard(activitiesCard);
        guestService.findById(reservationDTO.getGuestId());
        reservationService.saveReservation(reservation);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(path = "{reservationId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<HttpStatus> updateReservation(@Valid @RequestBody ReservationDTO reservationDTO,
                                                        BindingResult result,
                                                        @PathVariable ("reservationId") Long reservationId) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            throw new ApiRequestException(errorMessage.toString());
        }
        List<Reservation> reservations = reservationService.getReservations();
        for(Reservation reservation : reservations){
            if(reservation.getId() == reservationId){
                reservation.setNumberOfAdults(reservationDTO.getNumberOfAdults());
                reservation.setNumberOfKids(reservationDTO.getNumberOfKids());
                reservation.setArrivalDate(reservationDTO.getArrivalDate());
                reservation.setDepartureDate(reservationDTO.getDepartureDate());
                reservationService.saveReservation(reservation);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(path = "{reservationId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable("reservationId") Long reservationId){
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
