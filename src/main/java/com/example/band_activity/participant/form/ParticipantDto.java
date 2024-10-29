package com.example.band_activity.participant.form;

import com.example.band_activity.participant.Participant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipantDto {

    private Long id;
    private Long activityId;

    private String username;
    private Long memberId;
    private String memberName;

    private String status;

    public ParticipantDto(Participant participant) {
        this.id = participant.getId();
        this.activityId = participant.getActivity().getId();
        this.username = participant.getUsername();
        this.memberId = participant.getMemberId();
        this.memberName = participant.getMemberName();
        this.status = participant.getStatus().getDisplay();
    }
}
