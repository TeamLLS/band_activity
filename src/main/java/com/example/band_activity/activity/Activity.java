package com.example.band_activity.activity;

import com.example.band_activity.activity.command.CancelActivity;
import com.example.band_activity.activity.command.CloseActivity;
import com.example.band_activity.activity.command.OpenActivity;
import com.example.band_activity.activity.event.ActivityCanceled;
import com.example.band_activity.activity.event.ActivityClosed;
import com.example.band_activity.simulation.command.CreateActivityDummy;
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
    private String location;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private Instant deadline;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ActivityStatus status;
    private Integer participantNum;

    private Instant createdAt;
    private Instant closedAt;

    public Activity(OpenActivity command) {
        this.clubId = command.getClubId();
        this.name = command.getName();
        this.image = command.getImage();
        this.location = command.getLocation();
        this.description = command.getDescription();
        this.startTime = command.getStartTime();
        this.endTime = command.getEndTime();
        this.deadline = command.getDeadline();
        this.status = ActivityStatus.OPENED;
        this.participantNum = 0;
        this.createdAt = Instant.now();
    }

    //테스트용
    public Activity(CreateActivityDummy command){
        this.clubId = command.getClubId();
        this.name = command.getName();
        this.image = command.getImage();
        this.location = command.getLocation();
        this.description = command.getDescription();
        this.startTime = command.getStartTime();
        this.endTime = command.getEndTime();
        this.deadline = command.getDeadline();
        this.status = ActivityStatus.CLOSED;
        this.participantNum = 0;
        this.createdAt = command.getTime();
        this.closedAt = command.getDeadline().plusSeconds(3600);
    }

    public ActivityClosed close(CloseActivity command){
        this.status=ActivityStatus.CLOSED;
        this.closedAt=Instant.now();
        return new ActivityClosed(command.getUsername(), this);
    }

    public ActivityCanceled cancel(CancelActivity command){
        this.status = ActivityStatus.CANCELED;
        this.closedAt=Instant.now();
        return new ActivityCanceled(command.getUsername(), this);
    }


    public void participantNumIncreased(){
        participantNum++;
    }

    public void participantNumDecreased(){
        participantNum--;
    }


}
