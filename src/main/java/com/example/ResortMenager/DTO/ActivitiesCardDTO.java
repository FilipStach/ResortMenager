package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.Activity;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.Reservation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivitiesCardDTO {
    @NotNull(message = "ActivitiesCard's place id can't be null")
    private Long placeId;
    @NotNull(message = "ActivitiesCard's reservation id can't be null")
    private Long reservationId;
    @NotNull(message = "ActivitiesCard's activities left parameter can't be null")
    @Min(value = 1, message = "ActivitiesCard's activities left parameter can't be lower then one")
    private Integer activitiesLeft;
}
