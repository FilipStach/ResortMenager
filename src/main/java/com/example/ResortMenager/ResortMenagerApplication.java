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
			guest.addReservation(reservation);
			guestRepository.save(new Guest("Filip", "Stach", "filip.stach00@gmail.com"));
			guestRepository.save(new Guest("ANN", "mal", "sasa.aadd@gmail.com"));
			guestRepository.save(new Guest("Filip", "Stach", "Janek.stach00@gmail.com"));
			guestRepository.save(new Guest("JAN", "LLO", "LLL.stach00@gmail.com"));
			System.out.println("PLAce id: " + place.getId());
			System.out.println("Reservation id: " + reservation.getId());
			PlaceBooking placeBooking = new PlaceBooking(place,
					reservation);
			place.addPlaceBooking(placeBooking);
			reservation.addPlaceBooking(placeBooking);
//			placeRepository.save(place);
			ActivitiesCard activitiesCard = new ActivitiesCard(
					place,reservation,5);
			place.addActivitesCard(activitiesCard);
			reservation.addActivitesCard(activitiesCard);
			reservationRepository.save(reservation);
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
