package com.example.ResortMenager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
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
    @NotNull
    @Column(
            name = "number_of_people",
            nullable = false
    )
    private Integer numberOfPeople;
    @NotNull
    @Column(
            name = "start_date",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime startDate;
    @NotNull
    @Column(
            name = "end_date",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime endDate;
    @NotBlank
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
    @Column(
            name = "ended"
    )
    private Boolean ended = false;
    @NotNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(
        name = "activitiesCard_id",
        referencedColumnName = "id",
        nullable = false,
        foreignKey = @ForeignKey(
                name = "activity_activitiesCard_id_fk"
        )
    )
    private ActivitiesCard activitiesCard;

    public Activity(Integer numberOfPeople, LocalDateTime startDate, LocalDateTime endDate, String name) {
        this.numberOfPeople = numberOfPeople;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Activity{" +
                "numberOfPeople=" + numberOfPeople +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", ended=" + ended +
                '}';
    }

}
