package com.example.band_activity.activity;


import com.example.band_activity.activity.command.CreateActivity;
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

    @BeforeEach
    public void saveActivities(){
        activities = new ArrayList<>();

        Activity saved1 = activityStore.save("testUserA", new Activity(new CreateActivity(1L, "testUserA", "testActivityA", null, null, null, null)));
        Activity saved2 = activityStore.save("testUserA", new Activity(new CreateActivity(1L, "testUserA", "testActivityB", null, null, null, null)));
        Activity saved3 = activityStore.save("testUserB", new Activity(new CreateActivity(1L, "testUserB", "testActivityC", null, null, null, null)));

        activities.add(0, saved1);
        activities.add(1, saved2);
        activities.add(2, saved3);
    }

    @Test
    public void findTest(){
        Activity saved1 = activities.get(1);
        Activity find = activityStore.find(saved1.getId());

        Assertions.assertThat(find).isEqualTo(saved1);
    }

    @Test
    public void findListByClubIdTest(){
        List<Activity> listA = activityStore.findListByClubId(1L, 0);
        List<Activity> listB = activityStore.findListByClubId(1L, 1);

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(1);
        Assertions.assertThat(listB).contains(activities.get(2));
    }
}
