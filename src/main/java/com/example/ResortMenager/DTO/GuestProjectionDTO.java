package com.example.ResortMenager.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuestProjectionDTO {
    private Object id;
    private Object email;
    private Object firstName;
    private Object lastName;
    private Object reservationId;

    public GuestProjectionDTO(Object first, Object second) {
        if (first instanceof Object[]) {
            Object[] firstArray = (Object[]) first;
            this.id = firstArray[0];
            this.email = (String) firstArray[1];
            this.firstName = (String) firstArray[2];
            this.lastName = (String) firstArray[3];
            this.reservationId = second;
        }
    }
}