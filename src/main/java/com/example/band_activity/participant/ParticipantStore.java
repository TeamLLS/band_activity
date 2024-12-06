package com.example.band_activity.participant;

import com.example.band_activity.external.kafka.KafkaProducerService;
import com.example.band_activity.participant.event.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ParticipantStore {

    private final KafkaProducerService kafkaProducerService;
    private final ParticipantRepository participantRepository;
    private final ParticipantEventRepository participantEventRepository;

    public Participant save(String username, Participant participant){
        Participant saved = participantRepository.save(participant);
        saveEvent(new ParticipantCreated(username, saved));

        return saved;
    }

    public Page<Participant> findListWithActivityByUsername(Long clubId, String username, int pageNo, int pageSize){
        return participantRepository.findAllWithActivityByUsername(clubId,username, PageRequest.of(pageNo, pageSize));
    }

    public Page<Participant> findListByActivityId(Long activityId, int pageNo, int pageSize){
        return participantRepository.findAllByActivityId(activityId, PageRequest.of(pageNo, pageSize));
    }

    public Participant findByActivityIdAndMemberId(Long activityId, Long memberId){
        return participantRepository.findByActivityIdAndMemberId(activityId, memberId).orElseThrow();
    }

    public Participant findByActivityIdAndUsername(Long activityId, String username){
        return participantRepository.findByActivityIdAndUsername(activityId, username).orElseThrow();
    }

    public ParticipantEventJpo saveEvent(ParticipantEvent event){
        ParticipantEventJpo saved = participantEventRepository.save(new ParticipantEventJpo(event));

        if(event instanceof ParticipantConfirmed){
            kafkaProducerService.sendEventToKafka(event);
        }

        return saved;
    }

    //테스트용
    public ParticipantEventJpo findEvent(String eventId){
        return participantEventRepository.findById(eventId).orElseThrow();
    }

}
