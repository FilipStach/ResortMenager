package com.example.ResortMenager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Place")
@Table(name = "place")
public class Place {
    @Id
    @SequenceGenerator(
            name = "place_id_sequence",
            sequenceName = "place_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "place_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
    @Column(
            name = "city",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String city;
    @Column(
            name = "postal_code",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String postalCode;
    @Column(
            name = "street",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String street;
    @Column(
            name = "number",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String number;
    @Column(
            name = "max_guests_number",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private Integer maxGuestsNumber;
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinTable(
            name = "place_booking",
            joinColumns = @JoinColumn(
                    name = "place_id",
                    foreignKey = @ForeignKey(name = "place_booking_place_id_fk")
            ),
            inverseJoinColumns = @JoinColumn (
                    name = "reservation_id",
                    foreignKey = @ForeignKey(name = "place_booking_reservation_id_fk")
            )
    )
    private List<Reservation> reservations= new ArrayList<>();

    public Place(String name, String city, String postalCode, String street, String number, Integer maxGuestsNumber) {
        this.name = name;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.number = number;
        this.maxGuestsNumber = maxGuestsNumber;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", maxGuestsNumber=" + maxGuestsNumber +
                '}';
    }

    public void addReservation(Reservation reservation){
        if(!reservations.contains(reservation)){
            reservations.add(reservation);
        }
    }
}
