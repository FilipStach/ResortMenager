package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ActivityDTO {
    @NotNull(message = "Activity's number of people can't be null")
    @Min(value = 1, message = "Activity's number of people can't be lower then one")
    private Integer numberOfPeople;
    @NotNull(message = "Activity's start date can't be null")
    @Future(message = "Activity's start date must be in the future")
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @NotBlank(message = "Activity's name can't be blank")
    private String name;
    @NotNull(message = "Activity's card id can't be null")
    private Long activitiesCardId;

    public ActivityDTO(Integer numberOfPeople, LocalDateTime startDate, String name, Long activitiesCardId) {
        this.numberOfPeople = numberOfPeople;
        this.startDate = startDate;
        this.endDate = startDate.plusDays(1);
        this.name = name;
        this.activitiesCardId = activitiesCardId;
    }
}
