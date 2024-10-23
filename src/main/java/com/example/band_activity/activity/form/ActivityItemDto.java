package com.example.band_activity.activity.form;

import com.example.band_activity.activity.Activity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActivityItemDto {

    private Long id;

    private String name;

    private String image;

    private String status;

    private Integer participantNum;


    public ActivityItemDto(Activity activity, String imageResource) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.image = imageResource;
        this.status = activity.getStatus().getDisplay();
        this.participantNum = activity.getParticipantNum();
    }
}
