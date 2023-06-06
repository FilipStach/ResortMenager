package com.example.ResortMenager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @NotBlank
    @Column(
            name = "name",
//            nullable = false,
            columnDefinition = "TEXT",
            updatable = false
    )
    private String name;
    @NotBlank
    @Column(
            name = "city",
//            nullable = false,
            columnDefinition = "TEXT"
    )
    private String city;
    @NotBlank
    @Column(
            name = "postal_code",
//            nullable = false,
            columnDefinition = "TEXT"
    )
    private String postalCode;
    @NotBlank
    @Column(
            name = "street",
//            nullable = false,
            columnDefinition = "TEXT"
    )
    private String street;
    @NotBlank
    @Column(
            name = "number",
//            nullable = false,
            columnDefinition = "TEXT"
    )
    private String number;
    @NotNull
    @Column(
            name = "max_guests_number",
//            nullable = false,
            columnDefinition = "TEXT"
    )
    private Integer maxGuestsNumber;
    @JsonManagedReference
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "place"
    )
    private List<PlaceBooking> placeBookings= new ArrayList<>();
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "place"
    )
    @JsonManagedReference
    private List<ActivitiesCard> acitivitiesCards = new ArrayList<>();
    public Place(String name, String city, String postalCode, String street, String number, Integer maxGuestsNumber) {
        this.name = name;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.number = number;
        this.maxGuestsNumber = maxGuestsNumber;
    }
    public Place(Place place){
        System.out.println("Place copy constructor");
        this.name = place.getName();
        this.city = place.getCity();
        this.postalCode = place.getPostalCode();
        this.street = place.getStreet();
        this.number = place.getNumber();
        this.maxGuestsNumber = place.getMaxGuestsNumber();
        addPlaceBooking(place.getPlaceBookings().get(0));
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
    public void addPlaceBooking(PlaceBooking placeBooking){
        if(!placeBookings.contains(placeBooking)){
            this.placeBookings.add(placeBooking);
        }
    }
    public void removePlaceBooking(PlaceBooking placeBooking){
        placeBookings.remove(placeBooking);
    }
    public void addActivitesCard(ActivitiesCard acitivitiesCard){
        if(!acitivitiesCards.contains(acitivitiesCard)){
            acitivitiesCards.add(acitivitiesCard);
            acitivitiesCard.setPlace(this);
        }
    }
    public void removeActivitesCard(ActivitiesCard acitivitiesCard){
        acitivitiesCards.remove(acitivitiesCard);
    }
}
