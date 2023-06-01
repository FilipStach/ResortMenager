package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.PlaceBooking;
import com.example.ResortMenager.domain.Reservation;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservationProjectionDTO {
    private Long id;
    private int numberOfAdults;
    private int numberOfKids;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private Long guest_id;
    private List<Long> place_ids;
    private List<Long> activities_card_ids;

    public ReservationProjectionDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.numberOfAdults = reservation.getNumberOfAdults();
        this.numberOfKids = reservation.getNumberOfKids();
        this.arrivalDate = reservation.getArrivalDate();
        this.departureDate = reservation.getDepartureDate();
        this.guest_id = reservation.getGuest().getId();
        this.place_ids = new ArrayList<>();
        this.activities_card_ids = new ArrayList<>();
        List<PlaceBooking> placeBookings = reservation.getPlaceBookings();
        List<ActivitiesCard> activitiesCards = reservation.getAcitivitiesCards();
        for(PlaceBooking placeBooking : placeBookings){
            System.out.println(placeBooking.getPlace().getId());
            this.place_ids.add(placeBooking.getPlace().getId());
        }
        for(ActivitiesCard activitiesCard : activitiesCards){
            this.activities_card_ids.add(activitiesCard.getId());
        }
    }
}
