package com.example.band_activity.participant.event;

import com.example.band_activity.external.JsonUtil;
import lombok.Getter;

import java.time.Instant;


@Getter
public abstract class ParticipantEvent {

    private final String eventId;
    private final Long activityId;
    private final Long memberId;
    private final String triggeredBy;
    private final Instant time;

    public ParticipantEvent(String eventId, Long activityId, Long memberId, String triggeredBy, Instant time) {
        this.eventId = eventId;
        this.activityId = activityId;
        this.memberId = memberId;
        this.triggeredBy = triggeredBy;
        this.time = time;
    }

    public String typeName(){
        return this.getClass().getTypeName();
    }

    public String Payload(){
        return JsonUtil.toJson(this);
    }
}
