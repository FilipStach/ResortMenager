package com.example.ResortMenager.repository;

import com.example.ResortMenager.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
