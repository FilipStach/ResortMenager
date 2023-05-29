package com.example.ResortMenager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "PlaceBooking")
@Table(name = "place_booking")
public class PlaceBooking {
    @EmbeddedId
    private ActivitiesCardId id;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @MapsId("placeId")
    @JsonBackReference
    @JoinColumn(
            name = "place_id",
            foreignKey = @ForeignKey(name = "activitiesCard_place_id_fk"),
            nullable = false
    )
    private Place place;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @MapsId("reservationId")
    @JsonBackReference
    @JoinColumn(
            name = "reservation_id",
            foreignKey = @ForeignKey(name = "activitiesCard_reservation_id_fk"),
            nullable = false

    )
    private Reservation reservation;

    public PlaceBooking(Place place, Reservation reservation) {
        this.id = new ActivitiesCardId(1L, 1L);
        this.place = place;
        this.reservation = reservation;
    }
}
