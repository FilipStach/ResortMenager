package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.GuestDTO;
import com.example.ResortMenager.DTO.GuestProjectionDTO;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.exception.ApiRequestException;
import com.example.ResortMenager.service.GuestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
@AllArgsConstructor
public class GuestController {
    private final GuestService guestService;
    @GetMapping
    public ResponseEntity<List<GuestProjectionDTO>> getGuests(){
        List<Guest> guests = guestService.getGuests();

        List<GuestProjectionDTO> guestProjectionDTOS= new ArrayList<>();
        for(Guest guest : guests){
            GuestProjectionDTO guestProjectionDTO = new GuestProjectionDTO(guest);
            guestProjectionDTOS.add(guestProjectionDTO);
        }
        return new ResponseEntity<>(guestProjectionDTOS, HttpStatus.OK);
    }
    @GetMapping(path = "{guestId}")
    public ResponseEntity<GuestProjectionDTO> getGuest(@PathVariable ("guestId") long guestId){
        Guest guest = guestService.findById(guestId);
        return new ResponseEntity<>(new GuestProjectionDTO(guest), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<HttpStatus> addGuest(@Valid @RequestBody GuestDTO guestDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            throw new ApiRequestException(errorMessage.toString());
        }
        Guest guest = new Guest(guestDTO.getName(), guestDTO.getSurrname(), guestDTO.getEmail());

        guestService.saveGuest(guest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(path = "{guestId}")
    public ResponseEntity<HttpStatus> updateGuest(@Valid @RequestBody GuestDTO guestDTO,BindingResult result,
                            @PathVariable ("guestId") long guestId) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            throw new ApiRequestException(errorMessage.toString());
        }
        Guest guest = guestService.findById(guestId);
        guest.setName(guestDTO.getName());
        guest.setSurrname(guestDTO.getSurrname());
        guest.setEmail(guestDTO.getEmail());
        guestService.saveGuest(guest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(path = "{guestId}")
    public ResponseEntity<HttpStatus> deleteGuest(@PathVariable ("guestId") Long guestId){
        guestService.deleteGuest(guestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}