package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.Activity;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.Reservation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    private Long placeId;
    private Long reservationId;
    private Integer activitiesLeft;
}
