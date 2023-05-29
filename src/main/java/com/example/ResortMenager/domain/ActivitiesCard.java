package com.example.ResortMenager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AcitivitiesCard")
@Table(name = "activites_card")
public class ActivitiesCard {
    @Id
    @SequenceGenerator(
            name = "activites_card_id_sequence",
            sequenceName = "activites_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "activites_card_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @JsonBackReference
    @JoinColumn(

        name = "place_id",
        updatable = false,
        referencedColumnName = "id",
        nullable = true,
        foreignKey = @ForeignKey( name = "activites_card_place_id_fk")
    )
    private Place place;
    @JsonBackReference
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(

            name = "reservation_id",
            updatable = false,
            referencedColumnName = "id",
            nullable = true,
            foreignKey = @ForeignKey( name = "activites_card_reservation_id_fk")
    )
    private Reservation reservation;
        @Column(
            nullable = false,
            name = "activities_left"
    )
    private Integer activitiesLeft;

    public ActivitiesCard(Place place, Reservation reservation, Integer activitiesLeft) {
        this.place = place;
        this.reservation = reservation;
        this.activitiesLeft = activitiesLeft;
    }

    @Override
    public String toString() {
        return "ActivitiesCard{" +
                "place=" + place.getName() +
                ", reservation=" + reservation.getId() +
                ", activitiesLeft=" + activitiesLeft +
                '}';
    }
    @JsonManagedReference
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            mappedBy = "activitiesCard"
    )
    private List<Activity> activities;
    //        @EmbeddedId
//    private ActivitiesCardId id;
//    @ManyToOne(
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.ALL}
//    )
//    @MapsId("placeId")
//    @JoinColumn(
//            name = "place_id",
//            foreignKey = @ForeignKey(name = "activitiesCard_place_id_fk"),
//            nullable = false
//    )
//    private Place place;
//    @ManyToOne(
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.ALL}
//    )
//    @MapsId("reservationId")
//    @JoinColumn(
//            name = "reservation_id",
//            foreignKey = @ForeignKey(name = "activitiesCard_reservation_id_fk"),
//            nullable = false
//
//    )
//    private Reservation reservation;
//    @Column(
//            nullable = false,
//            name = "activities_left"
//    )
//    private Integer activitiesLeft;
//
//    public ActivitiesCard(ActivitiesCardId activitiesCardId, Place place, Reservation reservation, Integer activitiesLeft) {
//        this.id = activitiesCardId;
//        this.place = place;
//        this.reservation = reservation;
//        this.activitiesLeft = activitiesLeft;
//    }
//
//    public ActivitiesCard(ActivitiesCardId id, Integer activitiesLeft) {
//        this.id = id;
//        this.activitiesLeft = activitiesLeft;
//    }
//
//    public ActivitiesCard(Place place, Reservation reservation, Integer activitiesLeft) {
//        this.place = new Place(place);
////        this.place = place;
//        this.reservation = new Reservation(reservation);
////        this.reservation = reservation;
//        this.activitiesLeft = activitiesLeft;
//        this.id = new ActivitiesCardId(1L, 1L);
//    }


}

