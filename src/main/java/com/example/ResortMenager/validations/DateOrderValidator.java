package com.example.ResortMenager.validations;

import com.example.ResortMenager.DTO.ReservationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOrderValidator implements ConstraintValidator<ValidDateOrder, ReservationDTO> {
    @Override
    public boolean isValid(ReservationDTO reservationDTO, ConstraintValidatorContext context) {
        if (reservationDTO.getArrivalDate() == null || reservationDTO.getDepartureDate() == null) {
            return true;  // or false, if these fields are mandatory
        }
        return reservationDTO.getDepartureDate().isAfter(reservationDTO.getArrivalDate());
    }
}
