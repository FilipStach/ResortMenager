package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ActivitiesCardProjectionDTO {
    private Long id;
    private int activitiesLeft;
    private Long reservationId;
    private Long placeId;
    private List<Activity> activities;

    public ActivitiesCardProjectionDTO(ActivitiesCard activitiesCard) {
        this.id = activitiesCard.getId();
        this.activitiesLeft = activitiesCard.getActivitiesLeft();
        this.reservationId = activitiesCard.getReservation().getId();
        this.placeId = activitiesCard.getPlace().getId();
        this.activities = activitiesCard.getActivities();
    }
}
