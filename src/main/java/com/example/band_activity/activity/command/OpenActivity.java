package com.example.band_activity.activity.command;

import com.example.band_activity.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class OpenActivity extends Command{

    @NotNull
    private Long clubId;

    @NotNull
    private String name;

    private String image;

    private String description;

    private Instant startTime;

    private Instant endTime;

    public OpenActivity(String username, Long clubId, String name, String image, String description, Instant startTime, Instant endTime) {
        super(username);
        this.clubId = clubId;
        this.name = name;
        this.image = image;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
