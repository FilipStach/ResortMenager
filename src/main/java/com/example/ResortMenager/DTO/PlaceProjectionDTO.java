package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.PlaceBooking;
import com.example.ResortMenager.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class PlaceProjectionDTO {
    private Long id;
    private String name;
    private String city;
    private String postalCode;
    private String street;
    private String number;
    private int maxGuestsNumber;
    private List<Long> reservation_ids;
    private List<Long> activities_card_ids;

    public PlaceProjectionDTO(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.city = place.getCity();
        this.postalCode = place.getPostalCode();
        this.street = place.getStreet();
        this.number = place.getNumber();
        this.maxGuestsNumber = place.getMaxGuestsNumber();
        this.reservation_ids = new ArrayList<>();
        this.activities_card_ids = new ArrayList<>();
        List<PlaceBooking> placeBookings = place.getPlaceBookings();
        List<ActivitiesCard> activitiesCards = place.getAcitivitiesCards();
        for(PlaceBooking placeBooking : placeBookings){
            reservation_ids.add(placeBooking.getReservation().getId());
        }
        for(ActivitiesCard activitiesCard : activitiesCards){
            activities_card_ids.add(activitiesCard.getId());
        }
    }
}
