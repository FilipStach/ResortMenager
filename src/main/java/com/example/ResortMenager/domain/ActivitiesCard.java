package com.example.ResortMenager.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AcitivitiesCard")
@Table(name = "activites_card")
public class ActivitiesCard {
    @EmbeddedId
    private ActivitiesCardId id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @MapsId("placeId")
    @JoinColumn(
            name = "place_id",
            foreignKey = @ForeignKey(name = "activitiesCard_place_id_fk")
    )
    private Place place;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @MapsId("reservationId")
    @JoinColumn(
            name = "reservation_id",
            foreignKey = @ForeignKey(name = "activitiesCard_reservation_id_fk")
    )
    private Reservation reservation;
    @Column(
            nullable = false,
            name = "activities_left"
    )
    private Integer activitiesLeft;
//    @OneToMany(
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
//            mappedBy = "acitivitiesCard"
//    )
//    private List<Activity> activities = new ArrayList<>();

    public ActivitiesCard(ActivitiesCardId activitiesCardId, Place place, Reservation reservation, Integer activitiesLeft) {
        this.id = activitiesCardId;
        this.place = place;
        this.reservation = reservation;
        this.activitiesLeft = activitiesLeft;
    }

//    public void addActivity(Activity activity){
//        if(!activities.contains(activity) && activitiesLeft>0){
//            activities.add(activity);
//            activity.setActivitiesCard(this);
//            activitiesLeft--;
//        }
//        else if(activitiesLeft==0){
//            System.out.println("Not enough activities exception");
//        }
//        else{
//            System.out.println("Acitivity already added exception");
//        }
//    }
//    public void removeActivity(Activity activity) {
//        activities.remove(activity);
//    }
}

