package com.example.ResortMenager.service;

import com.example.ResortMenager.domain.ActivitiesCard;
import com.example.ResortMenager.domain.Activity;
import com.example.ResortMenager.domain.Guest;
import com.example.ResortMenager.repository.ActivitiesCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class ActivitiesCardService {
    private final ActivitiesCardRepository activitiesCardRepository;
    private final ActivityService activityService;

    public List<ActivitiesCard> getActivitiesCards(){
        return activitiesCardRepository.findAll();
    }

    public void setActivitiesCard(ActivitiesCard activitiesCard){
        activitiesCardRepository.save(activitiesCard);
    }
    public ActivitiesCard findById(Long id){
        ActivitiesCard activitiesCard = activitiesCardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActivitiesCard not found"));
        return activitiesCard;
    }
    public void save(ActivitiesCard activitiesCard){
        activitiesCardRepository.save(activitiesCard);
    }
    public void deleteActivitiesCard(Long activitiesCardId){
        ActivitiesCard activitiesCard = activitiesCardRepository.findById(activitiesCardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActivitiesCard not found"));
        List<Activity> activities = activitiesCard.getActivities();
        for(Activity activity : activities){
            activityService.deleteActivity(activity.getId());
        }
        activitiesCardRepository.delete(activitiesCard);
    }

}
