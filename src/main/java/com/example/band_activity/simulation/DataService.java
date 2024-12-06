package com.example.band_activity.simulation;


import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityStore;
import com.example.band_activity.participant.Participant;
import com.example.band_activity.participant.ParticipantStatus;
import com.example.band_activity.participant.ParticipantStore;
import com.example.band_activity.participant.command.ConfirmParticipant;
import com.example.band_activity.simulation.command.RegisterParticipantDummy;
import com.example.band_activity.simulation.command.CreateActivityDummy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DataService {

    private final ActivityStore activityStore;
    private final ParticipantStore participantStore;

    public Long createActivity(CreateActivityDummy command){
        return activityStore.save(command.getUsername(), new Activity(command)).getId();
    }

    public Participant registerParticipant(RegisterParticipantDummy command){
        Activity activity = activityStore.find(command.getActivityId());
        command.setActivity(activity);

        if(command.getStatus() == ParticipantStatus.ATTEND || command.getStatus() == ParticipantStatus.ADDITIONAL_ATTEND) {
            activity.participantNumIncreased();
        }else if(command.getStatus() == ParticipantStatus.ADDITIONAL_NOT_ATTEND){
            activity.participantNumDecreased();
        }

        Participant participant = participantStore.save(command.getUsername(), new Participant(command));
        participantStore.saveEvent(participant.confirm(new ConfirmParticipant(command.getUsername(), participant.getActivity().getId())));

        return participant;
    }

}
