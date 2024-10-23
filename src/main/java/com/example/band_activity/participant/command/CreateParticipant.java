package com.example.band_activity.participant.command;


import com.example.band_activity.activity.Activity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateParticipant {

    @NotNull
    private Long activityId;
    private Activity activity; //반드시 별도세팅

    @NotNull
    private Long memberId;
    private String memberName;

    @NotNull
    private String username;
}
