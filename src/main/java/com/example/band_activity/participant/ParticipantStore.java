package com.example.band_activity.participant;

import com.example.band_activity.participant.event.ParticipantCreated;
import com.example.band_activity.participant.event.ParticipantEvent;
import com.example.band_activity.participant.event.ParticipantEventJpo;
import com.example.band_activity.participant.event.ParticipantEventRepository;
import lombok.AllArgsConstructor;
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

    public List<Participant> findListByActivityId(Long activityId, int pageNo){
        Pageable pageable = PageRequest.of(pageNo, 2);

        return participantRepository.findAllByActivityId(activityId, pageable).getContent();
    }

    public Participant findByActivityIdAndMemberId(Long activityId, Long memberId){
        return participantRepository.findByActivityIdAndMemberId(activityId, memberId).orElseThrow(); //적절한 오류 생성
    }

    public void saveEvent(ParticipantEvent event){
        participantEventRepository.save(new ParticipantEventJpo(event));
    }

}
