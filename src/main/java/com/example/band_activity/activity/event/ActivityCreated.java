package com.example.band_activity.activity.event;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.activity.ActivityStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ActivityCreated extends ActivityEvent{
    private String name;
    private String image;

    private String location;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private Instant deadline;


    public ActivityCreated(String username, Activity activity) {
        super(UUID.randomUUID().toString(), activity.getId(), activity.getClubId(), username, activity.getCreatedAt());
        this.name = activity.getName();
        this.image = activity.getImage();
        this.location = activity.getLocation();
        this.description = activity.getDescription();
        this.startTime = activity.getStartTime();
        this.endTime = activity.getEndTime();
        this.deadline = activity.getDeadline();
    }
}
