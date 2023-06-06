package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ActivitiesCardService activitiesCardService;

    public List<Reservation> getReservations(){
        return reservationRepository.findAll();
    }
    public void saveReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }
    public Reservation findById(Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        return reservation;
    }
    public void deleteReservation(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
        List<ActivitiesCard> activitiesCards = reservation.getAcitivitiesCards();
        for(ActivitiesCard activitiesCard : activitiesCards){
            activitiesCardService.deleteActivitiesCard(activitiesCard.getId());
        }
        reservationRepository.delete(reservation);
    }
}
