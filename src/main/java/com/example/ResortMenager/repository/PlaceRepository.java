package com.example.ResortMenager.repository;

import com.example.ResortMenager.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
