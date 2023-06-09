package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.ActivitiesProjectionDTO;
import com.example.ResortMenager.DTO.ActivityDTO;
import com.example.ResortMenager.domain.Activity;
import com.example.ResortMenager.service.ActivitiesCardService;
import com.example.ResortMenager.service.ActivityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;
    private final ActivitiesCardService activitiesCardService;
    @GetMapping
    public ResponseEntity<List<ActivitiesProjectionDTO>> getActivities(){
        List<Activity> activities = activityService.getActivities();
        List<ActivitiesProjectionDTO>activitiesProjectionDTOS = new ArrayList<>();
        for(Activity activity : activities){
            activitiesProjectionDTOS.add(new ActivitiesProjectionDTO(activity));
        }
        return new ResponseEntity<>(activitiesProjectionDTOS, HttpStatus.OK);
    }
    @GetMapping(path = "{activityId}")
    public ResponseEntity<ActivitiesProjectionDTO> getActivitie(@PathVariable("activityId") Long activityId){
        Activity activity = activityService.findById(activityId);
        ActivitiesProjectionDTO activitiesProjectionDTO = new ActivitiesProjectionDTO(activity);

        return new ResponseEntity<>(activitiesProjectionDTO, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<HttpStatus> addActivity(@Valid @RequestBody ActivityDTO activityDTO, BindingResult result) {
        if(result.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            for(FieldError error : result.getFieldErrors()){
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
        }
        Activity activity = new Activity(activityDTO.getNumberOfPeople(), activityDTO.getStartDate(),
                activityDTO.getEndDate(), activityDTO.getName());
        activity.setActivitiesCard(activitiesCardService.findById(activityDTO.getActivitiesCardId()));
        activityService.saveActivity(activity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(path = "{activityId}")
    public ResponseEntity<HttpStatus> updateActivity(@Valid @PathVariable("activityId") Long activityId,BindingResult result,
                               @RequestBody ActivityDTO activityDTO){
        if(result.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            for(FieldError error : result.getFieldErrors()){
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
        }
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(path = "{activityId}")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable("activityId") Long activityId){
        activityService.deleteActivity(activityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
