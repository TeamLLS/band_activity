package com.example.band_activity.participant.form;

import com.example.band_activity.participant.Participant;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ParticipantActivityItemDto {

    private Long activityId;
    private String activityName;
    private String image;
    private Instant startTime;

    private Long memberId;
    private String participantStatus;

    public ParticipantActivityItemDto(Participant participant, String imageResource) {
        this.activityId = participant.getActivity().getId();
        this.activityName = participant.getActivity().getName();
        this.image = imageResource;
        this.startTime = participant.getActivity().getStartTime();
        this.memberId = participant.getMemberId();
        this.participantStatus = participant.getStatus().getDisplay();
    }
}
