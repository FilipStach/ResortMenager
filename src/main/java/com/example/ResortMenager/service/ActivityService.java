package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.Activity;
import com.example.ResortMenager.exception.InternalServerError;
import com.example.ResortMenager.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    public List<Activity> getActivities(){
        try {
            return activityRepository.findAll();
        } catch (Exception e){
            throw new InternalServerError("Server error", e);
        }
    }
    public Activity findById(Long activityId){
        try {
            Activity activity = activityRepository.findById(activityId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found"));
            return activity;

        } catch (Exception e){
            throw new InternalServerError("Server error", e);
        }
    }
    public void saveActivity(Activity activity){
        try {
            activityRepository.save(activity);
        } catch (Exception e){
            throw new InternalServerError("Server error", e);
        }
    }
    public void deleteActivity(Long activityId){
        try {
            Activity activity = activityRepository.findById(activityId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found"));
            activityRepository.delete(activity);
        } catch (Exception e){
            throw new InternalServerError("Server error", e);
        }
    }
}
