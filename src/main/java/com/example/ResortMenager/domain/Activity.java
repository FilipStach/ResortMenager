package com.example.ResortMenager.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "Activity")
@Table(name = "activity")
public class Activity {
    @Id
    @SequenceGenerator(
            name = "activity_id_sequence",
            sequenceName = "activity_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "activity_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    private Integer numberOfPeople;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private Boolean ended;
}
