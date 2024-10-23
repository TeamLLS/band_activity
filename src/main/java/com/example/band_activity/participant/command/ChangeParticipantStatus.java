package com.example.band_activity.participant.command;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.participant.ParticipantStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ChangeParticipantStatus {


    @NotNull
    private Long activityId;

    @NotNull
    private Long memberId;

    @NotNull
    private ParticipantStatus status;

    @NotNull
    private String username;
}
