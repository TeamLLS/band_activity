package com.example.band_activity.activity.event;

import com.example.band_activity.core.Event;
import com.example.band_activity.external.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public abstract class ActivityEvent extends Event {
    private Long activityId;

    public ActivityEvent(String eventId, Long activityId, Long clubId, String triggeredBy, Instant time) {
        super(eventId, clubId, triggeredBy, time);
        this.activityId = activityId;
    }

}
