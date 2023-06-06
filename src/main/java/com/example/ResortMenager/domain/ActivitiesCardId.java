package com.example.ResortMenager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ActivitiesCardId implements Serializable {
    @NotBlank
    @Column( name = "reservation_id")
    private Long reservationId;
    @NotBlank
    @Column( name = "place_id")
    private Long placeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivitiesCardId that = (ActivitiesCardId) o;
        return Objects.equals(reservationId, that.reservationId) && Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, placeId);
    }
}
