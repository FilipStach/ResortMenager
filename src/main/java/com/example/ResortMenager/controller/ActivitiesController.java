package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.ActivitiesProjectionDTO;
import com.example.ResortMenager.DTO.ActivityDTO;
import com.example.ResortMenager.domain.Activity;
import com.example.ResortMenager.service.ActivitiesCardService;
import com.example.ResortMenager.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {
    private final ActivityService activityService;
    private final ActivitiesCardService activitiesCardService;
    @GetMapping
    public List<ActivitiesProjectionDTO> getActivities(){
        List<Activity> activities = activityService.getActivities();
        List<ActivitiesProjectionDTO>activitiesProjectionDTOS = new ArrayList<>();
        for(Activity activity : activities){
            activitiesProjectionDTOS.add(new ActivitiesProjectionDTO(activity));
        }
        return activitiesProjectionDTOS;
    }
    @GetMapping(path = "{activityId}")
    public ActivitiesProjectionDTO getActivitie(@PathVariable("activityId") Long activityId){
        List<Activity> activities = activityService.getActivities();
        for(Activity activity : activities){
            if(activity.getId() == activityId)
                return new ActivitiesProjectionDTO(activity);
        }
        return null;
    }
    @PostMapping
    public void addActivity(@RequestBody ActivityDTO activityDTO) {
        System.out.println(activityDTO);
        Activity activity = new Activity(activityDTO.getNumberOfPeople(), activityDTO.getStartDate(),
                activityDTO.getEndDate(), activityDTO.getName());
        activity.setActivitiesCard(activitiesCardService.findById(activityDTO.getActivitiesCardId()));
        activityService.saveActivity(activity);
    }
    @PutMapping(path = "{activityId}")
    public void updateActivity(@PathVariable("activityId") Long activityId, @RequestBody ActivityDTO activityDTO){
        List<Activity> activities = activityService.getActivities();
        for(Activity activity : activities){
            if(activity.getId() == activityId){
                activity.setNumberOfPeople(activityDTO.getNumberOfPeople());
                activity.setStartDate(activityDTO.getStartDate());
                System.out.println(activityDTO.getEndDate());
                activity.setEndDate(activityDTO.getEndDate());
                activity.setName(activityDTO.getName());
                activityService.saveActivity(activity);
            }
        }
    }
    @DeleteMapping(path = "{activityId}")
    public void deleteActivity(@PathVariable("activityId") Long activityId){
        activityService.deleteActivity(activityId);
    }
}
