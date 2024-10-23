package com.example.band_activity.activity;

import com.example.band_activity.activity.command.CreateActivity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long clubId;

    @NotNull
    private String name;

    private String image;

    private String description;

    private Instant startTime;

    private Instant endTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    ActivityStatus status;

    private Integer participantNum;

    private Instant createdAt;
    private Instant closedAt;

    public Activity(CreateActivity command) {
        this.clubId = command.getClubId();
        this.name = command.getName();
        this.image = command.getImage();
        this.description = command.getDescription();
        this.startTime = command.getStartTime();
        this.endTime = command.getEndTime();
        this.status = ActivityStatus.OPENED;
        this.participantNum = 0;
        this.createdAt = Instant.now();
    }

    public void participantNumIncreased(){
        participantNum++;
    }

    public void participantNumDecreased(){
        participantNum--;
    }

}
