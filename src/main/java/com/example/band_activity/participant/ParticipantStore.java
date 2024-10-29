package com.example.band_activity.participant;

import com.example.band_activity.participant.event.ParticipantCreated;
import com.example.band_activity.participant.event.ParticipantEvent;
import com.example.band_activity.participant.event.ParticipantEventJpo;
import com.example.band_activity.participant.event.ParticipantEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ParticipantStore {

    private final ParticipantRepository participantRepository;
    private final ParticipantEventRepository participantEventRepository;

    public Participant save(String username, Participant participant){
        Participant saved = participantRepository.save(participant);
        ParticipantEvent participantCreated = new ParticipantCreated(username, saved);
        participantEventRepository.save(new ParticipantEventJpo(participantCreated));

        return saved;
    }

    public Page<Participant> findListWithActivityByUsername(Long clubId, String username, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return participantRepository.findAllWithActivityByUsername(clubId,username, pageable);
    }

    public Page<Participant> findListByActivityId(Long activityId, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return participantRepository.findAllByActivityId(activityId, pageable);
    }

    public Participant findByActivityIdAndMemberId(Long activityId, Long memberId){
        return participantRepository.findByActivityIdAndMemberId(activityId, memberId).orElseThrow();
    }

    public Participant findByActivityIdAndUsername(Long activityId, String username){
        return participantRepository.findByActivityIdAndUsername(activityId, username).orElseThrow();
    }

    public ParticipantEventJpo saveEvent(ParticipantEvent event){
        return participantEventRepository.save(new ParticipantEventJpo(event));
    }

    //테스트용
    public ParticipantEventJpo findEvent(String eventId){
        return participantEventRepository.findById(eventId).orElseThrow();
    }

}
