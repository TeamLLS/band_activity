package com.example.band_activity.participant.command;

import com.example.band_activity.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class NotAttendActivity extends Command {

    @NotNull
    private Long activityId;

    @NotNull
    private Long memberId;

    private Boolean additional;

    public boolean isAdditional() {
        return additional != null ? additional : false;
    }

    public NotAttendActivity(String username, Long activityId, Long memberId, Boolean additional) {
        super(username);
        this.activityId = activityId;
        this.memberId = memberId;
        this.additional = additional;
    }
}
