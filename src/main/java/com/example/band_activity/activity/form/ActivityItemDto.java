package com.example.band_activity.activity.form;

import com.example.band_activity.activity.Activity;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
public class ActivityItemDto {

    private Long id;

    private String name;

    private String image;

    private Instant time;

    private String status;

    private Integer participantNum;


    public ActivityItemDto(Activity activity, String imageResource) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.image = imageResource;
        this.time = activity.getStartTime();
        this.status = activity.getStatus().getDisplay();
        this.participantNum = activity.getParticipantNum();
    }
}
