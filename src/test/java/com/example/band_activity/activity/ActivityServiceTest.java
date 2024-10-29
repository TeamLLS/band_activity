package com.example.band_activity.activity;

import com.example.band_activity.activity.command.CancelActivity;
import com.example.band_activity.activity.command.CloseActivity;
import com.example.band_activity.activity.command.OpenActivity;
import com.example.band_activity.activity.exception.ActivityNotInClubException;
import com.example.band_activity.activity.exception.ActivityNotOpenedException;
import com.example.band_activity.activity.form.ActivityDto;
import com.example.band_activity.activity.form.ActivityItemDto;
import com.example.band_activity.core.Command;
import com.example.band_activity.participant.ParticipantService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Import({ActivityService.class, ActivityStore.class, ParticipantService.class})
public class ActivityServiceTest {

    @MockBean
    ActivityStore activityStore;
    @MockBean
    ParticipantService participantService;

    @Autowired
    ActivityService activityService;

    List<Activity> activities;
    List<Command> commands;

    @BeforeEach
    public void saveActivities(){

        Activity saved1 = new Activity(new OpenActivity("UserA", 1L, "TestActivityA", "TestImageA", null, null, null));
        Activity saved2 = new Activity(new OpenActivity("UserA", 1L, "TestActivityB", "TestImageB",null, null, null));
        Activity saved3 = new Activity(new OpenActivity("UserB", 1L, "TestActivityC", "TestImageC", null, null, null));

        activities = new ArrayList<>();
        activities.add(0, saved1);
        activities.add(1, saved2);
        activities.add(2, saved3);

        Mockito.when(activityStore.find(1L)).thenReturn(activities.get(0));
        Mockito.when(activityStore.find(2L)).thenReturn(activities.get(1));
        Mockito.when(activityStore.find(3L)).thenReturn(activities.get(2));
        Mockito.when(activityStore.findListByClubId(1L, 0, 2))
                .thenReturn(new PageImpl<>(List.of(activities.get(0), activities.get(1)), PageRequest.of(0, 2), 3));
        Mockito.when(activityStore.findListByClubId(1L, 1, 2))
                .thenReturn(new PageImpl<>(List.of(activities.get(2)), PageRequest.of(1, 2), 3));


        commands = new ArrayList<>();
        commands.add(0, new CloseActivity("UserA", 2L, 1L));
        commands.add(1, new CloseActivity ("UserA", 2L, 2L));

        commands.add(2, new CancelActivity ("UserA", 2L, 1L));
        commands.add(3, new CancelActivity ("UserA", 2L, 2L));
    }



    @Test
    public void getTest(){
        Activity saved1 = activities.get(0);
        ActivityDto find = activityService.getActivityInfo(1L);
        ActivityDto dto = new ActivityDto(saved1, find.getImage());

        Assertions.assertThat(find)
                .hasFieldOrPropertyWithValue("clubId", dto.getClubId())
                .hasFieldOrPropertyWithValue("name", dto.getName());
    }

    @Test
    public void getListTest(){
        List<ActivityItemDto> listA = activityService.getActivityList(1L, 0);
        List<ActivityItemDto> listB = activityService.getActivityList(1L, 1);

        ActivityItemDto dto = new ActivityItemDto(activities.get(2), listB.get(0).getImage());

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(1);
        Assertions.assertThat(listB.get(0))
                .hasFieldOrPropertyWithValue("name", dto.getName());
    }

    @Test
    public void closeTest(){
        CloseActivity rightCmd = (CloseActivity) commands.get(0);
        CloseActivity wrongCmd = (CloseActivity) commands.get(1);


        Assertions.assertThatThrownBy(() -> {
            activityService.closeActivity(wrongCmd);
        }).isInstanceOf(ActivityNotInClubException.class);

        activityService.closeActivity(rightCmd);
        Assertions.assertThat(activityStore.find(2L).getStatus()).isEqualTo(ActivityStatus.CLOSED);

        Assertions.assertThatThrownBy(() -> {
            activityService.closeActivity(rightCmd);
        }).isInstanceOf(ActivityNotOpenedException.class);

    }


    @Test
    public void cancelTest(){
        CancelActivity rightCmd = (CancelActivity) commands.get(2);
        CancelActivity wrongCmd = (CancelActivity) commands.get(3);


        Assertions.assertThatThrownBy(() -> {
            activityService.cancelActivity(wrongCmd);
        }).isInstanceOf(ActivityNotInClubException.class);

        activityService.cancelActivity(rightCmd);
        Assertions.assertThat(activityStore.find(2L).getStatus()).isEqualTo(ActivityStatus.CANCELED);

        Assertions.assertThatThrownBy(() -> {
            activityService.cancelActivity(rightCmd);
        }).isInstanceOf(ActivityNotOpenedException.class);

    }
}
