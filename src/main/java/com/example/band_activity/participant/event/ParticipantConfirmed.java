package com.example.band_activity.participant.event;


import com.example.band_activity.participant.Participant;
import com.example.band_activity.participant.ParticipantStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ParticipantConfirmed extends ParticipantEvent{

    private ParticipantStatus status;

    public ParticipantConfirmed(String username, Participant participant) {
        super(UUID.randomUUID().toString(), participant.getActivity().getId(), participant.getMemberId(), username, Instant.now());
        this.status = participant.getStatus();
    }
}
