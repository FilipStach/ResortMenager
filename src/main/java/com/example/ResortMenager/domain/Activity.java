package com.example.ResortMenager.domain;

import jakarta.persistence.*;
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
    @Column(
            name = "number_of_people",
            nullable = false
    )
    private Integer numberOfPeople;
    @Column(
            name = "start_date",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime startDate;
    @Column(
            name = "end_date",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime endDate;
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
    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                name = "reservation_id",
                referencedColumnName = "place_id",
                nullable = false,
                foreignKey = @ForeignKey(
                        name = "activity_reservation_id_fk"
                )
            ),
                @JoinColumn(
                        name = "place_id",
                        referencedColumnName = "reservation_id",
                        nullable = false,
                        foreignKey = @ForeignKey(
                                name = "activity_place_id_fk"
                        )
                )
    })
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
