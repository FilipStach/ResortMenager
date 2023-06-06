package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.PlaceBooking;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    private Integer numberOfAdults;
    private Integer numberOfKids;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private Long guestId;
    private Long placeId;

    public ReservationDTO(Integer numberOfAdults, Integer numberOfKids, LocalDateTime arrivalDate,
                          LocalDateTime departureDate, Long guestId, Long placeId) {
        this.numberOfAdults = numberOfAdults;
        this.numberOfKids = numberOfKids;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        System.out.println("GuestId: " + guestId);
        this.guestId = guestId;
        System.out.println("PlaceId: " + placeId);
        this.placeId = placeId;
    }
}