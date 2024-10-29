package com.example.band_activity.activity;


import com.example.band_activity.activity.command.CancelActivity;
import com.example.band_activity.activity.command.CloseActivity;
import com.example.band_activity.activity.command.OpenActivity;
import com.example.band_activity.activity.event.ActivityCanceled;
import com.example.band_activity.activity.event.ActivityClosed;
import com.example.band_activity.activity.event.ActivityEventJpo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Import(ActivityStore.class)
public class ActivityStoreTest {

    @Autowired
    ActivityStore activityStore;

    List<Activity> activities;
    List<ActivityEventJpo> activityEvents;

    @BeforeEach
    public void saveActivities(){

        //엔티티 인스턴스 생성
        Activity saved1 = new Activity(new OpenActivity("UserA", 1L, "TestActivityA", "TestImageA", null, null, null));
        Activity saved2 = new Activity(new OpenActivity("UserA", 1L, "TestActivityB", "TestImageB",null, null, null));
        Activity saved3 = new Activity(new OpenActivity("UserB", 1L, "TestActivityC", "TestImageC", null, null, null));


        //영속성 컨텍스트 업로드
        activityStore.save("UserA", saved1);
        activityStore.save("UserA", saved2);
        activityStore.save("UserB", saved3);

        activities = new ArrayList<>();
        activities.add(0, saved1);
        activities.add(1, saved2);
        activities.add(2, saved3);

        ActivityEventJpo savedEvent1 = activityStore.saveEvent(new ActivityCanceled("UserA", saved2));
        ActivityEventJpo savedEvent2 = activityStore.saveEvent(new ActivityClosed("UserB", saved3));

        activityEvents = new ArrayList<>();
        activityEvents.add(0, savedEvent1);
        activityEvents.add(1, savedEvent2);
    }

    @Test
    public void findTest(){
        Activity saved1 = activities.get(1);
        //영속성 컨텍스트 조회
        Activity find = activityStore.find(saved1.getId());

        //영속성 컨텍스트 때문에 가능
        Assertions.assertThat(find).isEqualTo(saved1);
    }

    @Test
    public void findListByClubIdTest(){
        List<Activity> listA = activityStore.findListByClubId(1L, 0, 2).getContent();
        List<Activity> listB = activityStore.findListByClubId(1L, 1, 2).getContent();

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(1);
        Assertions.assertThat(listB).contains(activities.get(2));
    }


    @Test
    public void findEventTest(){
        ActivityEventJpo savedEvent1 = activityEvents.get(0);
        ActivityEventJpo findEvent = activityStore.findEvent(savedEvent1.getEventId());

        Assertions.assertThat(findEvent).isEqualTo(savedEvent1);
    }
}
