package com.example.band_activity.simulation.command;

import com.example.band_activity.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
public class CreateActivityDummy {


    @NotNull
    private String username;
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
    private Instant time;

    public CreateActivityDummy(String username, Long clubId, String name, String image, String location, String description, Instant startTime, Instant endTime, Instant deadline, Instant time) {
        this.username = username;
        this.clubId = clubId;
        this.name = name;
        this.image = image;
        this.location = location;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deadline = deadline;
        this.time = time;
    }
}
