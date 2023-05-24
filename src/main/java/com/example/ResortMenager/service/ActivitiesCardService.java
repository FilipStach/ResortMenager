package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.repository.ActivitiesCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ActivitiesCardService {
    private final ActivitiesCardRepository activitiesCardRepository;

    public List<ActivitiesCard> getActivitiesCards(){
        return activitiesCardRepository.findAll();
    }
}
