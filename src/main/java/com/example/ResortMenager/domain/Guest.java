package com.example.ResortMenager.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;
    @NotBlank
    @Column(
            name = "surrname",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String surrname;
    @NotBlank
    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;
    @JsonManagedReference("firstRef")
    @OneToMany(
            orphanRemoval = true,
            mappedBy = "guest",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
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
                "name='" + name + '\'' +"BLOCK"+
                ", surrname='" + surrname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void addReservation(Reservation reservation){
        if(!this.reservations.contains(reservation)){
            this.reservations.add(reservation);
            reservation.setGuest(this);
        }
    }
}
