package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.ActivitiesProjectionDTO;
import com.example.ResortMenager.DTO.GuestProjectionDTO;
import com.example.ResortMenager.domain.Activity;
import com.example.ResortMenager.service.ActivitiesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {
    private final ActivitiesService activitiesService;
    @GetMapping
    public List<ActivitiesProjectionDTO> getActivities(){
        List<Activity> activities = activitiesService.getActivities();
        List<ActivitiesProjectionDTO>activitiesProjectionDTOS = new ArrayList<>();
        for(Activity activity : activities){
            activitiesProjectionDTOS.add(new ActivitiesProjectionDTO(activity));
        }
        return activitiesProjectionDTOS;
    }
}
