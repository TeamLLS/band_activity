package com.example.band_activity.participant;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.participant.command.*;
import com.example.band_activity.participant.event.ParticipantConfirmed;
import com.example.band_activity.participant.event.ParticipantStatusChanged;
import com.example.band_activity.simulation.command.RegisterParticipantDummy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;


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

    private Instant changedAt;


    public Participant(AttendActivity command) {
        this.username = command.isAdditional()? command.getProfileName(): command.getUsername();
        this.activity = command.getActivity();
        this.memberId = command.getMemberId();
        this.memberName = command.getMemberName();
        this.changedAt = Instant.now();
        this.status = command.isAdditional()?ParticipantStatus.ADDITIONAL_ATTEND:ParticipantStatus.ATTEND;
    }

    //테스트용
    public Participant(RegisterParticipantDummy command) {
        this.username = command.getProfileName();
        this.activity = command.getActivity();
        this.memberId = command.getMemberId();
        this.memberName = command.getMemberName();
        this.changedAt = command.getTime();
        this.status = command.getStatus();
    }


    public ParticipantStatusChanged attend(AttendActivity command){
        this.activity.participantNumIncreased();
        this.status=command.isAdditional()?ParticipantStatus.ADDITIONAL_ATTEND:ParticipantStatus.ATTEND;
        this.changedAt = Instant.now();
        return new ParticipantStatusChanged(command.getUsername(), this);
    }

    public ParticipantStatusChanged notAttend(NotAttendActivity command){
        this.activity.participantNumDecreased();
        this.status=command.isAdditional()?ParticipantStatus.ADDITIONAL_NOT_ATTEND:ParticipantStatus.NOT_ATTEND;
        this.changedAt = Instant.now();
        return new ParticipantStatusChanged(command.getUsername(), this);
    }

    public ParticipantConfirmed confirm(ConfirmParticipant command){
        return new ParticipantConfirmed(command.getUsername(), this);
    }

}
