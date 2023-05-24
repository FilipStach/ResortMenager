package com.example.ResortMenager;

import com.example.ResortMenager.domain.*;
import com.example.ResortMenager.repository.ActivitiesCardRepository;
import com.example.ResortMenager.repository.GuestRepository;
import com.example.ResortMenager.repository.PlaceRepository;
import com.example.ResortMenager.repository.ReservationRepository;
import com.example.ResortMenager.service.ActivitiesCardService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ResortMenagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResortMenagerApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(GuestRepository guestRepository,
										ReservationRepository reservationRepository,
										PlaceRepository placeRepository,
										ActivitiesCardRepository activitiesCardRepository){
		return args -> {
			Guest guest = new Guest("Filip", "Stach", "filip.stach00@gmail.com");
			Place place = new Place("Stachowka", "Zab", "33-220", "Pod Ogrod", "12/a",
					6);
			Reservation reservation = new Reservation(2,2,LocalDateTime.now().plusDays(21),
					LocalDateTime.now().plusDays(28));
			reservation.setGuest(guest);
			place.addReservation(reservation);
			ActivitiesCard activitiesCard = new ActivitiesCard(
					new ActivitiesCardId(place.getId(), reservation.getId()),place,reservation,4);
			Activity pool = new Activity(4, LocalDateTime.now(),
					LocalDateTime.now().plusHours(1), "Pool");
			pool.setActivitiesCard(activitiesCard);
			activitiesCardRepository.save(activitiesCard);
//			reservation.addActivitesCard(acitivitiesCard);
//			place.addActivitesCard(acitivitiesCard);
//			reservation.addPlace(place);
			placeRepository.save(place);
			reservationRepository.save(reservation);
			List<Reservation> reservationList = reservationRepository.findAll();
			System.out.println("Number of reservations: "+ reservationList.size());
		};
	}

}
