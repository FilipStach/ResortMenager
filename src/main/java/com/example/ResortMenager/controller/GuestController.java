package com.example.ResortMenager.controller;

import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.service.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@AllArgsConstructor
public class GuestController {
    private final GuestService guestService;
    @GetMapping
    public List<Guest> getGuests(){
        return guestService.getGuests();
    }
    @PostMapping
    public void createNewGuest(@RequestBody Guest guest){
        System.out.println(guest);
//        guestService.saveGuest(guest);
    }
}
