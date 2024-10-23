package com.example.band_activity.participant;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityStore;
import com.example.band_activity.participant.command.ChangeParticipantStatus;
import com.example.band_activity.participant.command.CreateParticipant;
import com.example.band_activity.participant.form.ParticipantDto;
import com.example.band_activity.policy.ActivityStatusAccessPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantStore participantStore;
    private final ActivityStore activityStore;

    public void registerParticipant(CreateParticipant command){
        Activity activity = activityStore.find(command.getActivityId());

        ActivityStatusAccessPolicy.isOpened(activity);

        command.setActivity(activity);
        participantStore.save(command.getUsername(), new Participant(command));
        activity.participantNumIncreased();
    }

    public void changeParticipantStatus(ChangeParticipantStatus command){
        Participant participant = participantStore.findByActivityIdAndMemberId(command.getActivityId(), command.getMemberId());
        ActivityStatusAccessPolicy.isOpened(participant.getActivity());
        participantStore.saveEvent(participant.changeStatus(command));
    }

    @Transactional(readOnly = true)
    public List<ParticipantDto> getParticipantList(Long activityId, int pageNo){
        return participantStore.findListByActivityId(activityId, pageNo).stream()
                .map(ParticipantDto::new).toList();
    }
}
