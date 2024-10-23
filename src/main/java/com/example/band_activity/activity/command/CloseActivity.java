package com.example.band_activity.activity.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CloseActivity {
    @NotNull
    private Long activityId;

    @NotNull
    private Long clubId;

    @NotNull
    private String username;
}
