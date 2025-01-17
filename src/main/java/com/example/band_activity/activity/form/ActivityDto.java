package com.example.band_activity.activity.form;

import com.example.band_activity.activity.Activity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;

@Getter
@Setter
public class ActivityDto {

    private Long id;
    private Long clubId;
    private String name;
    private String image;

    private String location;
    private String description;
    private String contactInfo;

    private Instant startTime;
    private Instant endTime;
    private Instant deadline;

    private String status;

    private Integer participantNum;


    public ActivityDto(Activity activity, String imageResource) {
        this.id = activity.getId();
        this.clubId = activity.getClubId();
        this.name = activity.getName();
        this.image = imageResource;
        this.location = activity.getLocation();
        this.description = activity.getDescription();
        this.contactInfo = activity.getContactInfo();
        this.startTime = activity.getStartTime();
        this.endTime = activity.getEndTime();
        this.deadline = activity.getDeadline();
        this.status = activity.getStatus().getDisplay();
        this.participantNum = activity.getParticipantNum();
    }
}
