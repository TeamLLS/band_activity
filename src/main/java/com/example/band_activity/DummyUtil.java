package com.example.band_activity;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityRepository;
import com.example.band_activity.activity.command.OpenActivity;
import com.example.band_activity.participant.Participant;
import com.example.band_activity.participant.ParticipantRepository;
import com.example.band_activity.participant.command.AttendActivity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DummyUtil {

    private final ActivityRepository activityRepository;
    private final ParticipantRepository participantRepository;

    @Transactional
    @PostConstruct
    public void makeDummy(){

        OpenActivity commandA1 = new OpenActivity("Dummy_userB", 1L, "활동1", "common/activity/default.png", "장소1", "for test1",
                Instant.parse("2024-12-14T09:30:00+09:00"), Instant.parse("2024-12-15T12:30:00+09:00"));
        OpenActivity commandA2 = new OpenActivity("Dummy_userB", 1L, "활동2", "common/activity/default.png", "장소2", "for test2",
                Instant.parse("2024-12-17T10:30:00+09:00"), Instant.parse("2024-12-18T16:30:00+09:00"));
        OpenActivity commandA3 = new OpenActivity("Dummy_userA", 1L, "활동3", "common/activity/default.png", "장소3", "for test3",
                Instant.parse("2024-12-21T12:30:00+09:00"), Instant.parse("2024-12-23T17:30:00+09:00"));

        Activity activity1 = activityRepository.save(new Activity(commandA1));
        Activity activity2 = activityRepository.save(new Activity(commandA2));
        Activity activity3 = activityRepository.save(new Activity(commandA3));

        String[] profileNames1 = {"Dummy_userA", "Dummy_userB", "Dummy_userD"};
        String[] memberNames1 = {"허연준", "임윤빈", "최은"};
        Long[] memberIds1 = {1L, 2L, 4L};

        String[] profileNames2 = {"Dummy_userA", "Dummy_userB", "Dummy_userE"};
        String[] memberNames2 = {"허연준", "임윤빈", "하도준"};
        Long[] memberIds2 = {1L, 2L, 5L};

        String[] profileNames3 = {"Dummy_userA", "Dummy_userB", "Dummy_userC", "Dummy_userD"};
        String[] memberNames3 = {"허연준", "임윤빈", "권미르", "최은"};
        Long[] memberIds3 = {1L, 2L, 3L, 4L};


        AttendActivity commandP;

        for(int i=0; i<3; i++){
            commandP = new AttendActivity(profileNames1[i], activity1.getId(), 1L, memberIds1[i], memberNames1[i], null, null);
            commandP.setActivity(activity1);
            participantRepository.save(new Participant(commandP));
        }

        for(int i=0; i<3; i++){
            commandP = new AttendActivity(profileNames2[i], activity2.getId(), 1L, memberIds2[i], memberNames2[i], null, null);
            commandP.setActivity(activity2);
            participantRepository.save(new Participant(commandP));
        }

        for(int i=0; i<4; i++){
            commandP = new AttendActivity(profileNames3[i], activity3.getId(), 1L, memberIds3[i], memberNames3[i], null, null);
            commandP.setActivity(activity3);
            participantRepository.save(new Participant(commandP));
        }

    }



}
