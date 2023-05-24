package com.example.ResortMenager.controller;

import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.service.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
