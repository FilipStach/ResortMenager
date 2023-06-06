package com.example.ResortMenager.repository;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.ActivitiesCardId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivitiesCardRepository extends JpaRepository<ActivitiesCard, Long> {
}
