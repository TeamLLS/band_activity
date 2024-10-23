package com.example.band_activity.activity.event;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor
public class ActivityEventJpo {

    @Id
    private String eventId;
    private Long clubId;
    private Long activityId;
    private String triggeredBy;
    private String eventType;
    @Lob
    private String payload;
    private Instant time;

    public ActivityEventJpo(ActivityEvent activityEvent) {
        this.eventId = activityEvent.getEventId();
        this.clubId = activityEvent.getClubId();
        this.activityId = activityEvent.getActivityId();
        this.triggeredBy = activityEvent.getTriggeredBy();
        this.eventType = activityEvent.typeName();
        this.payload = activityEvent.Payload();;
        this.time =activityEvent.getTime();
    }
}
