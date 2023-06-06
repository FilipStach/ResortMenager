package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ActivityDTO {
    private Integer numberOfPeople;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private Long activitiesCardId;
}
