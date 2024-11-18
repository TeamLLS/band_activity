package com.example.band_activity.participant.command;

import com.example.band_activity.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
public class ConfirmParticipant extends Command {
    private Long activityId;

    public ConfirmParticipant(String username, Long activityId) {
        super(username, null);
        this.activityId = activityId;
    }
}
