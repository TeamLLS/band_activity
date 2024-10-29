package com.example.band_activity.activity;

import com.example.band_activity.activity.event.ActivityCreated;
import com.example.band_activity.activity.event.ActivityEvent;
import com.example.band_activity.activity.event.ActivityEventJpo;
import com.example.band_activity.activity.event.ActivityEventRepository;
import com.example.band_activity.participant.event.ParticipantEvent;
import com.example.band_activity.participant.event.ParticipantEventJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ActivityStore {

    private final ActivityRepository activityRepository;

    private final ActivityEventRepository activityEventRepository;


    public Activity save(String username, Activity activity){
        Activity saved = activityRepository.save(activity);
        ActivityEvent activityCreated = new ActivityCreated(username, saved);
        activityEventRepository.save(new ActivityEventJpo(activityCreated));

        return saved;
    }

    public Activity find(Long activityId){
        return activityRepository.findById(activityId).orElseThrow();
    }

    public Page<Activity> findListByClubId(Long clubId, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return activityRepository.findAllByClubId(clubId, pageable);
    }

    public ActivityEventJpo saveEvent(ActivityEvent event){
        return activityEventRepository.save(new ActivityEventJpo(event));
    }

    //테스트용
    public ActivityEventJpo findEvent(String eventId){
        return activityEventRepository.findById(eventId).orElseThrow();
    }
    
}
