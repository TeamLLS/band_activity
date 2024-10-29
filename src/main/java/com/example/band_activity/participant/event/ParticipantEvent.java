package com.example.band_activity.participant.event;

import com.example.band_activity.external.JsonUtil;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Getter
@NoArgsConstructor
public abstract class ParticipantEvent {

    private String eventId;
    private Long activityId;
    private Long memberId;
    private String triggeredBy;
    private Instant time;

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
