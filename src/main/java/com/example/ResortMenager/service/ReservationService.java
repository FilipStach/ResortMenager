package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public List<Reservation> getReservations(){
        return reservationRepository.findAll();
    }
    public void saveReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }
}
