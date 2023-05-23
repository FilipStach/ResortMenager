package com.example.ResortMenager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Reservation")
@Table(name = "reservation")
public class Reservation {
    @Id
    @SequenceGenerator(
            name = "reservation_id_sequence",
            sequenceName = "reservation_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "number_of_adults",
            nullable = false
    )
    private Integer numberOfAdults;
    @Column(
            name = "number_of_kids",
            nullable = false
    )
    private Integer numberOfKids;
    @Column(
            name = "arrival_date",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime arrivalDate;
    @Column(
            name = "departure_date",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime departureDate;
    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "guest_id",
            updatable = false,
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey( name = "guest_id_fk")
    )
    private Guest guest;
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "reservations"
    )
    private List<Place> places = new ArrayList<>();
    public Reservation(Integer numberOfAdults, Integer numberOfKids, LocalDateTime arrivalDate, LocalDateTime departureDate) {
        this.numberOfAdults = numberOfAdults;
        this.numberOfKids = numberOfKids;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
    @Override
    public String toString() {
        return "Reservation{" +
                "numberOfAdults=" + numberOfAdults +
                ", numberOfKids=" + numberOfKids +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                '}';
    }
    public void setGuest(Guest guest){
        this.guest = guest;
        guest.addReservation(this);
    }
    public void addPlace(Place place){
        if(!places.contains(place)){
            places.add(place);
            place.addReservation(this);
        }
    }
    public void removePlace(Place place){
        places.remove(place);
        place.getReservations().remove(this);
    }
}
