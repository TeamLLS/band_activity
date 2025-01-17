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
    private Instant changedAt;

    public ParticipantConfirmed(String username, Participant participant) {
        super(UUID.randomUUID().toString(), participant.getActivity().getClubId(), participant.getActivity().getId(),
                participant.getMemberId(), username, participant.getChangedAt());
        this.status = participant.getStatus();
        this.changedAt = participant.getChangedAt();
    }
}
