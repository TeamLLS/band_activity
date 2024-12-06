package com.example.band_activity.core;


import com.example.band_activity.external.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Getter
@NoArgsConstructor
public abstract class Event {

    private String eventId;
    private Long clubId;
    private String triggeredBy;
    private Instant time;


    public Event(String eventId, Long clubId, String triggeredBy, Instant time) {
        this.eventId = eventId;
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
