package com.example.band_activity.activity.event;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ActivityClosed extends ActivityEvent{
    private ActivityStatus status;

    public ActivityClosed(String username, Activity activity) {
        super(UUID.randomUUID().toString(), activity.getId(), activity.getClubId(), username, activity.getClosedAt());
        this.status = ActivityStatus.CLOSED;
    }
}
