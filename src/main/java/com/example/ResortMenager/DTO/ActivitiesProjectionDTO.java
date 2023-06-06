package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.Activity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ActivitiesProjectionDTO {
    private Long id;
    private Integer numberOfPeople;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private Boolean ended = false;
    private Long activitiesCardId;

    public ActivitiesProjectionDTO(Activity activity) {
        this.id = activity.getId();
        this.numberOfPeople = activity.getNumberOfPeople();
        this.startDate = activity.getStartDate();
        this.endDate = activity.getEndDate();
        this.name = activity.getName();
        this.ended = activity.getEnded();
        this.activitiesCardId = activity.getActivitiesCard().getId();
    }
}
