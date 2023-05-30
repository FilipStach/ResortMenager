package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.GuestProjectionDTO;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.service.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
@AllArgsConstructor
public class GuestController {
    private final GuestService guestService;
    @GetMapping
    public List<GuestProjectionDTO> getGuests(){
        List<Object[]> objects = guestService.getGuests();
        List<GuestProjectionDTO> guestProjectionDTOS= new ArrayList<>();
        for(Object[] objects1 : objects){
            GuestProjectionDTO guestProjectionDTO = new GuestProjectionDTO(objects1[0],objects1[1]);
            guestProjectionDTOS.add(guestProjectionDTO);
        }
        return guestProjectionDTOS;
    }
    @PostMapping
    public void createNewGuest(@RequestBody Guest guest){
        System.out.println(guest);
//        guestService.saveGuest(guest);
    }
}
