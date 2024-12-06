package com.example.band_activity.participant.event;

import com.example.band_activity.core.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Getter
@NoArgsConstructor
public abstract class ParticipantEvent extends Event {

    private Long activityId;
    private Long memberId;

    public ParticipantEvent(String eventId, Long clubId, Long activityId, Long memberId, String triggeredBy, Instant time) {
        super(eventId, clubId, triggeredBy, time);
        this.activityId = activityId;
        this.memberId = memberId;
    }
}
