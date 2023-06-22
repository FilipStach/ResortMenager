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
			Reservation reservation2 = new Reservation(2,2,LocalDateTime.now().plusDays(11),
					LocalDateTime.now().plusDays(18));
			guest.addReservation(reservation);
			Guest guest2 = new Guest("jan", "llo", "lll.stach00@gmail.com");
			guest2.addReservation(reservation2);
			guestRepository.save(new Guest("ann", "mal", "sasa.aadd@gmail.com"));
			guestRepository.save(new Guest("filip", "stach", "Janek.stach00@gmail.com"));
			System.out.println("PLAce id: " + place.getId());
			System.out.println("Reservation id: " + reservation.getId());
			PlaceBooking placeBooking = new PlaceBooking(place,
					reservation);
			PlaceBooking placeBooking2 = new PlaceBooking(place,
					reservation2);
			place.addPlaceBooking(placeBooking);
			place.addPlaceBooking(placeBooking2);
			reservation.addPlaceBooking(placeBooking);
			reservation2.addPlaceBooking(placeBooking2);
//			placeRepository.save(place);
			ActivitiesCard activitiesCard = new ActivitiesCard(
					place,reservation);
			ActivitiesCard activitiesCard2 = new ActivitiesCard(
					place,reservation2);
			place.addActivitesCard(activitiesCard);
			place.addActivitesCard(activitiesCard2);
			activitiesCard.addActivity(new Activity(2,LocalDateTime.now(),
					LocalDateTime.now().plusDays(7),"cycling"));
			reservation.addActivitesCard(activitiesCard);
			reservation2.addActivitesCard(activitiesCard2);
			reservationRepository.save(reservation);
			reservationRepository.save(reservation2);
//			placeRepository.save(place);
//			activitiesCardRepository.save(activitiesCard);
//			System.out.println(activitiesCard.getPlace().getCity());
			System.out.println(reservation.getId()) ;
//			reservation.addActivitesCard(acitivitiesCard);
//			place.addActivitesCard(acitivitiesCard);
//			reservation.addPlace(place);

//			placeRepository.save(place);
//			reservationRepository.save(reservation);
//			List<Reservation> reservationList = reservationRepository.findAll();
//			System.out.println(activitiesCard.getPlace().getCity());
//			System.out.println("Number of reservations: "+ reservationList.size());
		};
	}

}
