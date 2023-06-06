package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.exception.NotFoundException;
import com.example.ResortMenager.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class GuestService {
    private final GuestRepository guestRepository;
    private final ReservationService reservationService;

    public List<Guest> getGuests(){
        return guestRepository.findAll();
    }
    public Guest getGuest(Long id){
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Guest with id "+ id + " not found"));
        System.out.println("GUEST: "+guest);
        return guest;

    }
    public void saveGuest(Guest guest){
        guestRepository.save(guest);
    }
    public void updateGuest(Guest guest){
        guestRepository.save(guest);
    }
    public void deleteGuest(Long guestId){
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found"));
        List<Reservation> reservations = guest.getReservations();
        for(Reservation reservation : reservations){
            reservationService.deleteReservation(reservation.getId());
        }
        guestRepository.delete(guest);
    }
}
