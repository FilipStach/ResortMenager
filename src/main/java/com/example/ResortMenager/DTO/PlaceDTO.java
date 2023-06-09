package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.PlaceBooking;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class PlaceDTO {
    @NotBlank(message = "Place's name can't be blank")
    private String name;
    @NotBlank(message = "Place's city name can't be blank")
    private String city;
    @NotBlank(message = "Place's postalCode can't be blank")
    private String postalCode;
    @NotBlank(message = "Place's street can't be blank")
    private String street;
    @NotBlank(message = "Place's street number can't be blank")
    private String number;
    @NotNull(message = "Place's max guests number can't be null")
    @Min(value = 1, message = "Place's max guests number can't be lower then one")
    private Integer maxGuestsNumber;
}
