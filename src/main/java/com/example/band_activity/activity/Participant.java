package com.example.band_activity.activity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Participant {

    @Id
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @NotNull
    private String username;
    @NotNull
    private Long memberId;
    private String memberName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status;
}
