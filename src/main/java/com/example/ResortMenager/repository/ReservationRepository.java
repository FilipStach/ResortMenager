package com.example.ResortMenager.repository;

import com.example.ResortMenager.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
