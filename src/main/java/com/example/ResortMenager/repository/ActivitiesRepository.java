package com.example.ResortMenager.repository;

import com.example.ResortMenager.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivitiesRepository extends JpaRepository<Activity, Long> {
}
