package com.example.band_activity.participant;


import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityStore;
import com.example.band_activity.activity.command.OpenActivity;
import com.example.band_activity.external.kafka.KafkaProducerService;
import com.example.band_activity.participant.command.AttendActivity;
import com.example.band_activity.participant.event.ParticipantConfirmed;
import com.example.band_activity.participant.event.ParticipantEventJpo;
import com.example.band_activity.participant.event.ParticipantStatusChanged;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;


import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Import({ParticipantStore.class, ActivityStore.class, KafkaProducerService.class})
public class ParticipantStoreTest {

    @MockBean
    KafkaProducerService kafkaProducerService;

    @Autowired
    ActivityStore activityStore;
    @Autowired
    ParticipantStore participantStore;

    List<Activity> activities;
    List<Participant> participants;
    List<ParticipantEventJpo> participantEvents;

    @BeforeEach
    public void saveParticipant(){

        Activity activity = activityStore.save("UserA", new Activity(new OpenActivity("UserA", 1L, "TestActivityA", "TestImageA", "TestPlace", null, null, null, null)));

        activities = new ArrayList<>();
        activities.add(activity);

        AttendActivity attend1 = new AttendActivity("UserA", 2L, 1L, 1L, "TesterA", null, null);
        AttendActivity attend2 = new AttendActivity("UserB", 2L, 1L, 2L, "TesterB", null, null);
        AttendActivity attend3 = new AttendActivity("UserC", 2L, 1L, 3L, "TesterC", null, null);

        attend1.setActivity(activity);
        attend2.setActivity(activity);
        attend3.setActivity(activity);

        Participant saved1 = new Participant(attend1);
        Participant saved2 = new Participant(attend2);
        Participant saved3 = new Participant(attend3);


        participantStore.save("UserA", saved1);
        participantStore.save("UserB", saved2);
        participantStore.save("UserC", saved3);

        participants = new ArrayList<>();

        participants.add(saved1);
        participants.add(saved2);
        participants.add(saved3);

        ParticipantEventJpo savedEvent1 = participantStore.saveEvent(new ParticipantStatusChanged("UserA", saved1));
        ParticipantEventJpo savedEvent2 = participantStore.saveEvent(new ParticipantStatusChanged("UserB", saved2));

        participantEvents = new ArrayList<>();
        participantEvents.add(savedEvent1);
        participantEvents.add(savedEvent2);
    }

    @Test
    public void findTest(){
        Participant saved = participants.get(0);

        Participant find1 = participantStore.findByActivityIdAndUsername(saved.getActivity().getId(), saved.getUsername());
        Participant find2 = participantStore.findByActivityIdAndMemberId(saved.getActivity().getId(), saved.getMemberId());

        Assertions.assertThat(find1).isEqualTo(saved);
        Assertions.assertThat(find2).isEqualTo(saved);
    }

    @Test
    public void findListTest(){

        List<Participant> listA = participantStore.findListByActivityId(activities.get(0).getId(), 0, 2).getContent();
        List<Participant> listB = participantStore.findListByActivityId(activities.get(0).getId(), 1, 2).getContent();

        Assertions.assertThat(listA.size()).isEqualTo(2);
        Assertions.assertThat(listB.size()).isEqualTo(1);
        Assertions.assertThat(listB).contains(participants.get(2));

        List<Participant> listC = participantStore
                .findListWithActivityByUsername(1L, participants.get(0).getUsername(), 0, 2).getContent();

        Assertions.assertThat(listC.size()).isEqualTo(1);
        Assertions.assertThat(listC).contains(participants.get(0));
        Assertions.assertThat(listC.get(0).getActivity().getName()).isEqualTo(activities.get(0).getName());
    }

    @Test
    public void findEventTest(){
        ParticipantEventJpo savedEvent = participantEvents.get(0);

        ParticipantEventJpo find = participantStore.findEvent(savedEvent.getEventId());

        Assertions.assertThat(find).isEqualTo(savedEvent);
    }
}
