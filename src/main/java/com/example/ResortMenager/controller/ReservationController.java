package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.ReservationProjectionDTO;
import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
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

}
