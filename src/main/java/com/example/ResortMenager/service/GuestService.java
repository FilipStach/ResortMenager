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

    public List<Guest> getGuests(){
        return guestRepository.findAll();
    }
    public void saveGuest(Guest guest){
        guestRepository.save(guest);
    }
}
