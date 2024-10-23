package com.example.band_activity.activity;

import com.example.band_activity.activity.command.CreateActivity;
import com.example.band_activity.activity.form.ActivityDto;
import com.example.band_activity.activity.form.ActivityItemDto;
import net.bytebuddy.build.ToStringPlugin;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Import({ActivityService.class, ActivityStore.class})
public class ActivityServiceTest {

    @MockBean
    ActivityStore activityStore;

    @Autowired
    ActivityService activityService;

    List<Activity> activities;

    @BeforeEach
    public void saveActivities(){

        Activity saved1 = new Activity(new CreateActivity(1L, "testUserA", "testActivityA", null, null, null, null));
        Activity saved2 = new Activity(new CreateActivity(1L, "testUserA", "testActivityB", null, null, null, null));
        Activity saved3 = new Activity(new CreateActivity(1L, "testUserB", "testActivityC", null, null, null, null));

        Mockito.when(activityStore.find(1L)).thenReturn(saved1);
        Mockito.when(activityStore.findListByClubId(1L, 0)).thenReturn(List.of(saved1, saved2));
        Mockito.when(activityStore.findListByClubId(1L, 1)).thenReturn(List.of(saved3));

        activities = new ArrayList<>();
        activities.add(0, saved1);
        activities.add(1, saved2);
        activities.add(2, saved3);
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
}
