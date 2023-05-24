package com.example.ResortMenager.controller;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.service.ActivitiesCardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/activitiesCards")
public class ActivitiesCardController {
    private final ActivitiesCardService activitiesCardService;
    @GetMapping
    public List<ActivitiesCard> getActivitiesCards(){
        return activitiesCardService.getActivitiesCards();
    }
}
