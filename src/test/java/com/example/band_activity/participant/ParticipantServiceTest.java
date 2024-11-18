package com.example.band_activity.participant;



import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityStore;
import com.example.band_activity.activity.command.CloseActivity;
import com.example.band_activity.activity.command.OpenActivity;
import com.example.band_activity.activity.exception.ActivityNotClosedException;
import com.example.band_activity.activity.exception.ActivityNotInClubException;
import com.example.band_activity.core.Command;
import com.example.band_activity.participant.command.AttendActivity;
import com.example.band_activity.participant.command.ConfirmParticipant;
import com.example.band_activity.participant.command.NotAttendActivity;
import com.example.band_activity.participant.form.ParticipantActivityItemDto;
import com.example.band_activity.participant.form.ParticipantDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@Import({ParticipantService.class, ParticipantStore.class, ActivityStore.class})
public class ParticipantServiceTest {

    @MockBean
    ActivityStore activityStore;
    @MockBean
    ParticipantStore participantStore;

    @Autowired
    ParticipantService participantService;

    List<Participant> participants;
    List<Activity> activities;
    List<Command> commands;

    @BeforeEach
    public void saveParticipant(){

        Activity activity = new Activity(new OpenActivity("UserA", 1L, "TestActivityA", "TestImageA", "TestPlace", null, null, null));

        activities = new ArrayList<>();
        activities.add(activity);

        Mockito.when(activityStore.find(1L)).thenReturn(activities.get(0));

        AttendActivity attend1 = new AttendActivity("UserA", 1L, 1L, 1L, "TesterA", null, null);
        AttendActivity attend2 = new AttendActivity("UserB", 1L, 1L, 2L, "TesterB", null, null);
        AttendActivity attend3 = new AttendActivity("UserC", 1L, 1L, 3L, "TesterC", null, null);

        attend1.setActivity(activity);
        attend2.setActivity(activity);
        attend3.setActivity(activity);

        Participant saved1 = new Participant(attend1);
        Participant saved2 = new Participant(attend2);
        Participant saved3 = new Participant(attend3);

        participants = new ArrayList<>();

        participants.add(saved1);
        participants.add(saved2);
        participants.add(saved3);

        Mockito.when(participantStore.findByActivityIdAndMemberId(1L, 1L)).thenReturn(participants.get(0));
        Mockito.when(participantStore.findByActivityIdAndMemberId(1L, 2L)).thenReturn(participants.get(1));
        Mockito.when(participantStore.findByActivityIdAndMemberId(1L, 3L)).thenReturn(participants.get(2));


        Mockito.when(participantStore.findListWithActivityByUsername(1L, "UserC", 0, 2))
                .thenReturn(new PageImpl<>(List.of(participants.get(2)), PageRequest.of(0, 2), 1));
        Mockito.when(participantStore.findListByActivityId(1L, 0, 2))
                .thenReturn(new PageImpl<>(List.of(participants.get(0), participants.get(1)), PageRequest.of(0, 2), 3));
        Mockito.when(participantStore.findListByActivityId(1L, 1, 2))
                .thenReturn(new PageImpl<>(List.of(participants.get(2)), PageRequest.of(1, 2), 3));


        Mockito.when(participantStore.findByActivityIdAndUsername(1L, "UserA"))
                .thenReturn(participants.get(0));


        commands = new ArrayList<>();
        commands.add(new NotAttendActivity("UserA", 1L, 1L, null));
        commands.add(new AttendActivity("TestManager", 1L, 1L, 1L, "TesterA", true, "UserA"));
        commands.add(new AttendActivity("TestManager", 1L, 1L, 4L, "TesterD", true, "UserD"));
        commands.add(new ConfirmParticipant("TestManager", 1L));
    }

    
    
    @Test
    public void attendTest(){

        NotAttendActivity notAttend = (NotAttendActivity) commands.get(0);
        AttendActivity additionalAttend = (AttendActivity) commands.get(1);
        AttendActivity additionalWrongAttend = (AttendActivity) commands.get(2);

        participantService.notAttendActivity(notAttend);

        Assertions.assertThat(participants.get(0).getStatus()).isEqualTo(ParticipantStatus.NOT_ATTEND);

        Assertions.assertThatThrownBy(() -> {
            participantService.attendActivity(additionalAttend);
        }).isInstanceOf(ActivityNotClosedException.class);

        Activity activity = activityStore.find(1L);
        activity.close(new CloseActivity("TestManager", 1L, 1L));

        participantService.attendActivity(additionalAttend);
        Assertions.assertThat(participants.get(0).getStatus()).isEqualTo(ParticipantStatus.ADDITIONAL_ATTEND);


        Assertions.assertThatThrownBy(() -> {
            participantService.attendActivity(additionalWrongAttend);
        }).isInstanceOf(ActivityNotInClubException.class);
    }

    @Test
    public void getActivityListTest(){
        List<ParticipantActivityItemDto> list = participantService.getParticipantActivityList(1L, "UserC", 0, 2);

        ParticipantActivityItemDto dto = new ParticipantActivityItemDto(participants.get(2), list.get(0).getImage());

        Assertions.assertThat(list.size()).isEqualTo(1);
        Assertions.assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("activityId", dto.getActivityId())
                .hasFieldOrPropertyWithValue("activityName", dto.getActivityName())
                .hasFieldOrPropertyWithValue("image", dto.getImage())
                .hasFieldOrPropertyWithValue("memberId", dto.getMemberId())
                .hasFieldOrPropertyWithValue("participantStatus", dto.getParticipantStatus());
    }


    @Test
    public void getListTest(){

        NotAttendActivity notAttend = (NotAttendActivity) commands.get(0);

        Map<String, Object> resultA = participantService.getParticipantList(1L, "UserA", 0);

        participantService.notAttendActivity(notAttend);

        Map<String, Object> resultB = participantService.getParticipantList(1L, "UserA", 1);

        List<ParticipantDto> listA = (List<ParticipantDto>)resultA.get("list");
        List<ParticipantDto> listB = (List<ParticipantDto>)resultB.get("list");
       ParticipantDto dto = new ParticipantDto(participants.get(2));

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(1);
        Assertions.assertThat(listB.get(0))
                .hasFieldOrPropertyWithValue("activityId", dto.getActivityId())
                .hasFieldOrPropertyWithValue("username", dto.getUsername())
                .hasFieldOrPropertyWithValue("memberId", dto.getMemberId())
                .hasFieldOrPropertyWithValue("memberName", dto.getMemberName());

        Assertions.assertThat(resultA.get("attend")).isEqualTo(true);
        Assertions.assertThat(resultB.get("attend")).isEqualTo(false);
    }

    @Test
    public void confirmTest(){

        ConfirmParticipant confirm = (ConfirmParticipant) commands.get(3);

        Integer pageNo = participantService.confirmParticipants(confirm);

        Assertions.assertThat(pageNo).isEqualTo(2);
    }
}
