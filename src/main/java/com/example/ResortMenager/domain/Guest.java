package com.example.ResortMenager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Guest")
@Table(name = "guest")
public class Guest {
    @Id
    @SequenceGenerator(
            name = "guest_id_sequence",
            sequenceName = "guest_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guest_id_sequence"
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
            name = "surrname",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String surrname;
    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;
    @OneToMany(
            orphanRemoval = true,
            mappedBy = "guest",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Reservation> reservations = new ArrayList<>();

    public Guest(String name, String surrname, String email) {
        this.name = name;
        this.surrname = surrname;
        this.email = email;
    }
    @Override
    public String toString() {
        return "Guest{" +
                "name='" + name + '\'' +
                ", surrname='" + surrname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void addReservation(Reservation reservation){
        if(!this.reservations.contains(reservation)){
            this.reservations.add(reservation);
        }
    }
}
