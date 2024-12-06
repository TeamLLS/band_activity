package com.example.band_activity.simulation.command;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.core.Command;
import com.example.band_activity.participant.ParticipantStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
public class RegisterParticipantDummy {

    @NotNull
    private String username;
    @NotNull
    private Long clubId;
    @NotNull
    private Long activityId;
    private Activity activity; //반드시 별도세팅
    @NotNull
    private Long memberId;
    private String memberName;
    private ParticipantStatus status;
    private String profileName;
    private Instant time;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public RegisterParticipantDummy(String username, Long clubId, Long activityId, Activity activity, Long memberId, String memberName, ParticipantStatus status, String profileName, Instant time) {
        this.username = username;
        this.clubId = clubId;
        this.activityId = activityId;
        this.activity = activity;
        this.memberId = memberId;
        this.memberName = memberName;
        this.status = status;
        this.profileName = profileName;
        this.time = time;
    }
}
