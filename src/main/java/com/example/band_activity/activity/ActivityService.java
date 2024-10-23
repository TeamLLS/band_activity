package com.example.band_activity.activity;

import com.example.band_activity.activity.command.CreateActivity;
import com.example.band_activity.activity.form.ActivityDto;
import com.example.band_activity.activity.form.ActivityItemDto;
import com.example.band_activity.participant.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityStore activityStore;
    private final ParticipantService participantService;

    @Value("${spring.cloud.aws.cdn.url}")
    private String production;

    public void createActivity(CreateActivity command){
        activityStore.save(command.getUsername(), new Activity(command));
    }

    @Transactional(readOnly = true)
    public ActivityDto getActivityInfo(Long activityId){
        Activity find = activityStore.find(activityId);
        String imageResource = production + "/" + find.getImage();
        return new ActivityDto(find, imageResource);
    }

    @Transactional(readOnly = true)
    public List<ActivityItemDto> getActivityList(Long clubId, int pageNo){
        return activityStore.findListByClubId(clubId, pageNo).stream()
                .map(a -> new ActivityItemDto(a, production + "/" + a.getImage())).toList();
    }

    private void closeActivity(){

    }
}
