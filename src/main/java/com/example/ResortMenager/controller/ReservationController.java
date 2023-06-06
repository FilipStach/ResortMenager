package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.*;
import com.example.ResortMenager.domain.*;
import com.example.ResortMenager.service.GuestService;
import com.example.ResortMenager.service.PlaceService;
import com.example.ResortMenager.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final PlaceService placeService;
    private final GuestService guestService;

    @Autowired
    public ReservationController(ReservationService reservationService, PlaceService placeService, GuestService guestService) {
        this.reservationService = reservationService;
        this.placeService = placeService;
        this.guestService = guestService;
    }

    @GetMapping
    List<ReservationProjectionDTO> getReservations(){
        List<Reservation> reservations = reservationService.getReservations();
        List<ReservationProjectionDTO> reservationProjectionDTOS = new ArrayList<>();
        for(Reservation reservation : reservations){
            reservationProjectionDTOS.add(new ReservationProjectionDTO(reservation));
        }
        return reservationProjectionDTOS;
    }
    @GetMapping(path = "{reservationId}")
    public ReservationProjectionDTO getReservation(@PathVariable("reservationId") Long reservationId){
        List<Reservation> reservations = reservationService.getReservations();
        for(Reservation reservation : reservations){
            if(reservation.getId() == reservationId)
                return new ReservationProjectionDTO(reservation);
        }
        return null;
    }
    @PostMapping
    public void addReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation(reservationDTO.getNumberOfAdults(),reservationDTO.getNumberOfKids(),
                reservationDTO.getArrivalDate(),reservationDTO.getDepartureDate());
        System.out.println("Post RESERVATION");
        System.out.println(reservationDTO.getPlaceId() + " " + reservationDTO.getGuestId());
        System.out.println("Post RESERVATION2");
        PlaceBooking placeBooking = new PlaceBooking(placeService.findById(reservationDTO.getPlaceId()), reservation);
        reservation.setGuest(guestService.getGuest(reservationDTO.getGuestId()));
        reservation.addPlaceBooking(placeBooking);
        ActivitiesCard acitivitiesCard = new ActivitiesCard(placeBooking.getPlace(), reservation);
        reservation.addActivitesCard(acitivitiesCard);
        placeBooking.getPlace().addPlaceBooking(placeBooking);
        placeBooking.getPlace().addActivitesCard(acitivitiesCard);
        guestService.getGuest(reservationDTO.getGuestId());
        reservationService.saveReservation(reservation);
    }
    @PutMapping(path = "{reservationId}")
    public void updateReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable ("reservationId") Long reservationId) {
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
    }
    @DeleteMapping(path = "{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long reservationId){
        reservationService.deleteReservation(reservationId);
    }
}
