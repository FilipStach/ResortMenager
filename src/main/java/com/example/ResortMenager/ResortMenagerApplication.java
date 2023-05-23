package com.example.ResortMenager;

import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.domain.Place;
import com.example.ResortMenager.domain.Reservation;
import com.example.ResortMenager.repository.GuestRepository;
import com.example.ResortMenager.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class ResortMenagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResortMenagerApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(GuestRepository guestRepository,
										ReservationRepository reservationRepository){
		return args -> {
			Guest guest = new Guest("Filip", "Stach", "filip.stach00@gmail.com");
			Place place = new Place("Stachowka", "Zab", "33-220", "Pod Ogrod", "12/a",
					6);
			Reservation reservation = new Reservation(2,2,LocalDateTime.now().plusDays(21),
					LocalDateTime.now().plusDays(28));
			reservation.addPlace(place);
			reservation.setGuest(guest);
			reservationRepository.save(reservation);
		};
	}

}
