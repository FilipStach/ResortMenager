package com.example.ResortMenager.service;

import com.example.ResortMenager.DTO.ActivitiesProjectionDTO;
import com.example.ResortMenager.domain.Activity;
import com.example.ResortMenager.repository.ActivitiesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ActivitiesService {
    private final ActivitiesRepository activitiesRepository;
    public List<Activity> getActivities(){
        return  activitiesRepository.findAll();
    }
}
