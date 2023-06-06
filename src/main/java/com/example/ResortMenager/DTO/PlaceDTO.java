package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.PlaceBooking;
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
public class PlaceDTO {
    private String name;
    private String city;
    private String postalCode;
    private String street;
    private String number;
    private Integer maxGuestsNumber;
}
