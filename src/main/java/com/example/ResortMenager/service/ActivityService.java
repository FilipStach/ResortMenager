package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.Activity;
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
        return  activityRepository.findAll();
    }
    public void saveActivity(Activity activity){
        activityRepository.save(activity);
    }
    public void deleteActivity(Long activityId){
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found"));
        activityRepository.delete(activity);
    }
}
