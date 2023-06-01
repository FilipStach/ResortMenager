package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Object[]> getGuests(){
        return guestRepository.findAllGuestsWithReservations();
    }
    public List<Object[]> getGuestsWithOutReservation(){
        return guestRepository.findAllGuestsWithOutReservations();
    }
    public void saveGuest(Guest guest){
        guestRepository.save(guest);
    }
}
