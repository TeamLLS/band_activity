package com.example.band_activity.participant.event;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor
public class ParticipantEventJpo {

    @Id
    private String eventId;
    private Long activityId;
    private Long memberId;
    private String triggeredBy;
    private String eventType;
    @Lob
    private String payload;
    private Instant time;

    public ParticipantEventJpo(ParticipantEvent participantEvent) {
        this.eventId = participantEvent.getEventId();
        this.activityId = participantEvent.getActivityId();
        this.memberId = participantEvent.getMemberId();
        this.triggeredBy = participantEvent.getTriggeredBy();
        this.eventType = participantEvent.typeName();
        this.payload = participantEvent.Payload();
        this.time = participantEvent.getTime();
    }

}
