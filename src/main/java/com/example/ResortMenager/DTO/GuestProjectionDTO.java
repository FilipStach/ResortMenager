package com.example.ResortMenager.DTO;

import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.Reservation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GuestProjectionDTO {
    private Long id;
    private String email;
    private String name;
    private String surrname;
    private List<Long> reservationIds = new ArrayList<>();

    public GuestProjectionDTO(Guest guest) {
            List<Reservation>  reservations = guest.getReservations();
            this.id = guest.getId();
            this.email = guest.getEmail();
            this.name = guest.getName();
            this.surrname = guest.getSurrname();
            for (Reservation reservation : reservations){
                reservationIds.add(reservation.getId());
            }
    }
}