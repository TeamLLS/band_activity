package com.example.band_activity.activity.event;

import com.example.band_activity.external.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public abstract class ActivityEvent {
    private String eventId;
    private Long activityId;
    private Long clubId;
    private String triggeredBy;
    private Instant time;

    public ActivityEvent(String eventId, Long activityId, Long clubId, String triggeredBy, Instant time) {
        this.eventId = eventId;
        this.activityId = activityId;
        this.clubId = clubId;
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
