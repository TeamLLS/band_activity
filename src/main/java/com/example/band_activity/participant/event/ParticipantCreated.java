package com.example.band_activity.participant.event;


import com.example.band_activity.participant.Participant;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ParticipantCreated extends ParticipantEvent{

    private final String memberName;

    public ParticipantCreated(String username, Participant participant) {
        super(UUID.randomUUID().toString(), participant.getActivity().getId(), participant.getMemberId(), username, participant.getActivity().getCreatedAt());
        this.memberName = participant.getMemberName();
    }
}
