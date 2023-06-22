package com.example.ResortMenager.controller;

import com.example.ResortMenager.DTO.ActivitiesProjectionDTO;
import com.example.ResortMenager.DTO.ActivityDTO;
import com.example.ResortMenager.DTO.GuestProjectionDTO;
import com.example.ResortMenager.domain.Activity;
import com.example.ResortMenager.service.ActivitiesCardService;
import com.example.ResortMenager.service.ActivityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;
    private final ActivitiesCardService activitiesCardService;
    public List<String> getRoles(@AuthenticationPrincipal Jwt principal){
        Map<String,Object> objectMap = principal.getClaims();
        try {
            Map<String,Object> object = (Map<String, java.lang.Object>) objectMap.get("resource_access");
            Map<String,Object> elements = (Map<String, java.lang.Object>) object.get("resortManager-rest-api");
            List<String> roles = (List<String>) elements.get("roles");
            return roles;
        } catch (Exception e){
            return null;
        }
    }
    @GetMapping
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<List<ActivitiesProjectionDTO>> getActivities(@AuthenticationPrincipal Jwt principal){
        List<Activity> activities = activityService.getActivities();
        List<ActivitiesProjectionDTO>activitiesProjectionDTOS = new ArrayList<>();
        for(Activity activity : activities){
            if(activity.getActivitiesCard().getReservation().getGuest().getEmail().equals(
                    principal.getClaims().get("email")) || getRoles(principal).contains("client_admin")) {
                activitiesProjectionDTOS.add(new ActivitiesProjectionDTO(activity));
            }
        }
        if(activitiesProjectionDTOS.size()==0){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(activitiesProjectionDTOS, HttpStatus.OK);
    }
    @GetMapping(path = "{activityId}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<ActivitiesProjectionDTO> getActivitie(@AuthenticationPrincipal Jwt principal,
                                                                @PathVariable("activityId") Long activityId){
        Activity activity = activityService.findById(activityId);
        ActivitiesProjectionDTO activitiesProjectionDTO = new ActivitiesProjectionDTO(activity);
        if(activity.getActivitiesCard().getReservation().getGuest().getEmail().equals(
                principal.getClaims().get("email")) || getRoles(principal).contains("client_admin")){
            return new ResponseEntity<>(activitiesProjectionDTO, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<HttpStatus> addActivity(@AuthenticationPrincipal Jwt principal,
                                                  @Valid @RequestBody ActivityDTO activityDTO, BindingResult result) {
        if(result.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            for(FieldError error : result.getFieldErrors()){
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
        }
        Activity activity = new Activity(activityDTO.getNumberOfPeople(), activityDTO.getStartDate(),
                activityDTO.getEndDate(), activityDTO.getName());

        activity.setActivitiesCard(activitiesCardService.findById(activityDTO.getActivitiesCardId()));
        if(activity.getActivitiesCard().getReservation().getGuest().getEmail().equals(
                principal.getClaims().get("email")) || getRoles(principal).contains("client_admin")){
            activityService.saveActivity(activity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PutMapping(path = "{activityId}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<HttpStatus> updateActivity(@AuthenticationPrincipal Jwt principal,
                                                     @Valid @PathVariable("activityId") Long activityId,
                                                     BindingResult result, @RequestBody ActivityDTO activityDTO){
        boolean found = false;
        if(result.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            for(FieldError error : result.getFieldErrors()){
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
        }
        Activity activity = activityService.findById(activityId);
        if (activity.getActivitiesCard().getReservation().getGuest().
                getEmail().equals(principal.getClaims().get("email")) ||
                getRoles(principal).contains("client_admin")){
            activity.setNumberOfPeople(activityDTO.getNumberOfPeople());
            activity.setStartDate(activityDTO.getStartDate());
            activity.setEndDate(activityDTO.getEndDate());
            activity.setName(activityDTO.getName());
            activityService.saveActivity(activity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @DeleteMapping(path = "{activityId}")
    @PreAuthorize("hasRole('client_admin') or hasRole('client_guest')")
    public ResponseEntity<HttpStatus> deleteActivity(@AuthenticationPrincipal Jwt principal,
                                                     @PathVariable("activityId") Long activityId){
        Activity activity = activityService.findById(activityId);
        if (activity.getActivitiesCard().getReservation().getGuest().
                getEmail().equals(principal.getClaims().get("email")) ||
                getRoles(principal).contains("client_admin")){
            activityService.deleteActivity(activityId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
