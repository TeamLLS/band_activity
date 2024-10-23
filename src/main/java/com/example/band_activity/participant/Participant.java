package com.example.band_activity.participant;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.participant.command.ChangeParticipantStatus;
import com.example.band_activity.participant.command.CreateParticipant;
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
    private ParticipantStatus status;

    public Participant(CreateParticipant command) {
        this.username = command.getUsername();
        this.activity = command.getActivity();
        this.memberId = command.getMemberId();
        this.memberName = command.getMemberName();
        this.status = ParticipantStatus.ATTEND;
    }

    public ParticipantStatusChanged changeStatus(ChangeParticipantStatus command){
        this.status = command.getStatus();

        if(this.status==ParticipantStatus.ATTEND){
            this.activity.participantNumIncreased();
        }else{
            this.activity.participantNumDecreased();
        }

        return new ParticipantStatusChanged(command.getUsername(), this);
    }
}
