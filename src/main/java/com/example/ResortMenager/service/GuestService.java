package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.exception.InternalServerError;
import com.example.ResortMenager.exception.NotFoundException;
import com.example.ResortMenager.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GuestService {
    private final GuestRepository guestRepository;
    private final ReservationService reservationService;

    public List<Guest> getGuests(){
        try {
            return guestRepository.findAll();
        } catch (Exception e){
            throw new InternalServerError("Server error", e);
        }
    }
    public Guest findById(Long id){
        try {
            Guest guest = guestRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Guest with id " + id + " not found"));
            return guest;
        } catch (Exception e){
            throw new InternalServerError("Server error", e);
        }
    }
    public void saveGuest(Guest guest){
        try {
            guestRepository.save(guest);
        } catch (Exception e){
            throw new InternalServerError("Server error", e);
        }
    }
    public void deleteGuest(Long guestId) {
        try {
            Guest guest = guestRepository.findById(guestId)
                    .orElseThrow(() -> new NotFoundException("Guest with id " + guestId + " not found"));
            List<Reservation> reservations = guest.getReservations();
            for (Reservation reservation : reservations) {
                reservationService.deleteReservation(reservation.getId());
            }
            guestRepository.delete(guest);
        } catch (Exception e) {
            throw new InternalServerError("Server error", e);
        }
    }
}
