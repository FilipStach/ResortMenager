package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.PlaceBooking;
import com.example.ResortMenager.validations.ValidDateOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ValidDateOrder
public class ReservationDTO {
    @NotNull(message = "Reservation's number of adults can't be null")
    @PositiveOrZero(message = "Reservation's number of adults can't be negative")
    private Integer numberOfAdults;
    @NotNull(message = "Reservation's number of kids can't be null")
    @PositiveOrZero(message = "Reservation's number of kids can't be negative")
    private Integer numberOfKids;
    @NotNull(message = "Reservation's arrival date can't be null")
    @Future(message = "Reservation's arrival date must be in future")
    private LocalDateTime arrivalDate;
    @NotNull(message = "Reservation's departure date can't be null")
    private LocalDateTime departureDate;
    @NotNull(message = "Reservation's guest id can't be null")
    private Long guestId;
    @NotNull(message = "Reservation's place id can't be null")
    private Long placeId;

}