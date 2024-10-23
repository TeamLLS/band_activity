package com.example.band_activity.activity;

import com.example.band_activity.activity.event.ActivityCreated;
import com.example.band_activity.activity.event.ActivityEvent;
import com.example.band_activity.activity.event.ActivityEventJpo;
import com.example.band_activity.activity.event.ActivityEventRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Activity> findListByClubId(Long clubId, int pageNo){
        Pageable pageable = PageRequest.of(pageNo, 2);

        return activityRepository.findAllByClubId(clubId, pageable).getContent();
    }

}
