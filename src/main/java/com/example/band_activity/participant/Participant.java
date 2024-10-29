package com.example.band_activity.participant;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.participant.command.*;
import com.example.band_activity.participant.event.ParticipantConfirmed;
import com.example.band_activity.participant.event.ParticipantStatusChanged;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Participant {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @NotNull
    private String username;    //프로필 조회용

    @NotNull
    private Long memberId;
    private String memberName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ParticipantStatus status;


    public Participant(AttendActivity command) {
        this.username = command.isAdditional()? command.getProfileName(): command.getUsername();
        this.activity = command.getActivity();
        this.memberId = command.getMemberId();
        this.memberName = command.getMemberName();
        this.status = command.isAdditional()?ParticipantStatus.ADDITIONAL_ATTEND:ParticipantStatus.ATTEND;
    }

    public ParticipantStatusChanged attend(AttendActivity command){
        this.activity.participantNumIncreased();
        this.status=command.isAdditional()?ParticipantStatus.ADDITIONAL_ATTEND:ParticipantStatus.ATTEND;
        return new ParticipantStatusChanged(command.getUsername(), this);
    }

    public ParticipantStatusChanged notAttend(NotAttendActivity command){
        this.activity.participantNumDecreased();
        this.status=command.isAdditional()?ParticipantStatus.ADDITIONAL_NOT_ATTEND:ParticipantStatus.NOT_ATTEND;
        return new ParticipantStatusChanged(command.getUsername(), this);
    }

    public ParticipantConfirmed confirmed(ConfirmParticipant command){
        return new ParticipantConfirmed(command.getUsername(), this);
    }

}
