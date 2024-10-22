package com.example.band_activity.activity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;


import java.time.Instant;

@Entity
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
}
