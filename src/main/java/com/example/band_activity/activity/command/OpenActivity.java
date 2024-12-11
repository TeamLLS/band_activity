package com.example.band_activity.activity.command;

import com.example.band_activity.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class OpenActivity extends Command{

    @NotNull
    private String name;

    private String image;
    private String location;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private Instant deadline;
    private String contactInfo;

    public OpenActivity(String username, Long clubId, String name, String image, String location, String description,
                        Instant startTime, Instant endTime, Instant deadline, String contactInfo) {
        super(username, clubId);
        this.name = name;
        this.image = image;
        this.location = location;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deadline = deadline;
        this.contactInfo = contactInfo;
    }
}
