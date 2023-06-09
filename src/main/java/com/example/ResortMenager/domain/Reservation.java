package com.example.ResortMenager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
            fetch = FetchType.EAGER
    )
    @JsonBackReference
    @JoinColumn(
            name = "guest_id",
            updatable = false,
            referencedColumnName = "id",
            nullable = true,
            foreignKey = @ForeignKey( name = "reservation_guest_id_fk")
    )
    private Guest guest;
    @JsonManagedReference("firstRef")
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "reservation",
            fetch = FetchType.LAZY

    )
    private List<PlaceBooking> placeBookings = new ArrayList<>();
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "reservation"
    )
    @JsonManagedReference
    private List<ActivitiesCard> acitivitiesCards = new ArrayList<>();
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
    }
    public Reservation(Reservation reservation){
        System.out.println("Reservation copy constructor");
        this.numberOfAdults = reservation.getNumberOfAdults();
        this.numberOfKids = 5;
        this.arrivalDate = reservation.getArrivalDate();
        this.departureDate = reservation.getDepartureDate();
    }
    public void addActivitesCard(ActivitiesCard acitivitiesCard){
        if(!acitivitiesCards.contains(acitivitiesCard)){
            acitivitiesCards.add(acitivitiesCard);
            acitivitiesCard.setReservation(this);
        }
    }
    public void removeActivitesCard(ActivitiesCard acitivitiesCard){
            acitivitiesCards.remove(acitivitiesCard);
    }
    public void addPlaceBooking(PlaceBooking placeBooking){
        if(!placeBookings.contains(placeBooking)){
            placeBookings.add(placeBooking);
            placeBooking.setReservation(this);
        }
    }

    public void removePlaceBooking(PlaceBooking placeBooking){
        placeBookings.remove(placeBooking);
    }
}
