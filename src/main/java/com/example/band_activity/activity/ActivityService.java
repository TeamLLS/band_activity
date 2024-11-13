package com.example.band_activity.activity;

import com.example.band_activity.activity.command.CancelActivity;
import com.example.band_activity.activity.command.CloseActivity;
import com.example.band_activity.activity.command.OpenActivity;
import com.example.band_activity.activity.form.ActivityDto;
import com.example.band_activity.activity.form.ActivityItemDto;
import com.example.band_activity.participant.ParticipantService;
import com.example.band_activity.participant.command.ConfirmParticipant;
import com.example.band_activity.activity.policy.ActivityClubAccessPolicy;
import com.example.band_activity.activity.policy.ActivityStatusAccessPolicy;
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

    public Long createActivity(OpenActivity command){
        return activityStore.save(command.getUsername(), new Activity(command)).getId();
    }

    @Transactional(readOnly = true)
    public ActivityDto getActivityInfo(Long activityId){
        Activity find = activityStore.find(activityId);
        String imageResource = production + "/" + find.getImage();
        return new ActivityDto(find, imageResource);
    }

    @Transactional(readOnly = true)
    public List<ActivityItemDto> getActivityList(Long clubId, int pageNo, int pageSize){
        return activityStore.findListByClubId(clubId, pageNo, pageSize).getContent().stream()
                .map(a -> new ActivityItemDto(a, production + "/" + a.getImage())).toList();
    }

    public Long closeActivity(CloseActivity command){
        Activity activity = activityStore.find(command.getActivityId());

        ActivityStatusAccessPolicy.isOpened(activity);
        ActivityClubAccessPolicy.isInClub(activity, command.getClubId());

        participantService.confirmParticipants(new ConfirmParticipant(command.getUsername(), activity.getId()));
        activityStore.saveEvent(activity.close(command));

        return activity.getId();
    }

    public Long cancelActivity(CancelActivity command){
        Activity activity = activityStore.find(command.getActivityId());

        ActivityStatusAccessPolicy.isOpened(activity);
        ActivityClubAccessPolicy.isInClub(activity, command.getClubId());

        activityStore.saveEvent(activity.cancel(command));

        return activity.getId();
    }

}
