package com.example.ResortMenager.repository;

import com.example.ResortMenager.DTO.GuestProjectionDTO;
import com.example.ResortMenager.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

public interface GuestRepository extends JpaRepository<Guest, Long> {
//    @Query("SELECT g from Guest g JOIN fetch g.reservations")
//    List<Guest> findAllGuestsWithReservations();
    @Query(value = "SELECT g, r.id FROM guest g JOIN reservation r ON g.id = r.guest_id", nativeQuery = true)
    List<Object[]> findAllGuestsWithReservations();
}
