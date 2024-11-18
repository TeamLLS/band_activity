package com.example.band_activity.participant.event;


import com.example.band_activity.participant.Participant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ParticipantCreated extends ParticipantEvent{

    private String memberName;

    public ParticipantCreated(String username, Participant participant) {
        super(UUID.randomUUID().toString(), participant.getActivity().getClubId(), participant.getActivity().getId(),
                participant.getMemberId(), username, Instant.now());
        this.memberName = participant.getMemberName();
    }
}
