package com.example.band_activity.activity.command;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class CreateActivity {

    @NotNull
    private Long clubId;

    @NotNull
    private String username;

    @NotNull
    private String name;

    private String image;

    private String description;

    private Instant startTime;

    private Instant endTime;
}
