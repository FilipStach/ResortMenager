package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.GuestDTO;
import com.example.ResortMenager.DTO.GuestProjectionDTO;
import com.example.ResortMenager.DTO.ReservationDTO;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.service.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        List<Guest> guests = guestService.getGuests();

        List<GuestProjectionDTO> guestProjectionDTOS= new ArrayList<>();
        for(Guest guest : guests){
            GuestProjectionDTO guestProjectionDTO = new GuestProjectionDTO(guest);
            guestProjectionDTOS.add(guestProjectionDTO);
        }
        return guestProjectionDTOS;
    }
    @GetMapping(path = "{guestId}")
    public GuestProjectionDTO getGuest(@PathVariable ("guestId") long guestId){
        Guest guest = guestService.getGuest(guestId);
        return new GuestProjectionDTO(guest);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Guest> getGuestById(@PathVariable("id") Long id) {
//        Guest guest = guestService.findByd(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found"));
//        return new ResponseEntity<>(guest, HttpStatus.OK);
//    }
    @PostMapping
    public void addGuest(@RequestBody GuestDTO guestDTO) {
        Guest guest = new Guest(guestDTO.getName(), guestDTO.getSurrname(), guestDTO.getEmail());

        guestService.saveGuest(guest);
    }
    @PutMapping(path = "{guestId}")
    public void updateGuest(@RequestBody GuestDTO guestDTO,@PathVariable ("guestId") long guestId) {
        List<Guest> guests = guestService.getGuests();
        for(Guest guest : guests){
            if(guest.getId() == guestId){
                guest.setName(guestDTO.getName());
                guest.setSurrname(guestDTO.getSurrname());
                guest.setEmail(guestDTO.getEmail());
                guestService.saveGuest(guest);
            }
        }
    }
    @DeleteMapping(path = "{guestId}")
    public void deleteGuest(@PathVariable ("guestId") Long guestId){
        guestService.deleteGuest(guestId);
    }
}