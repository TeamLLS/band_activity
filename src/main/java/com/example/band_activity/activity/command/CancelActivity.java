package com.example.band_activity.activity.command;

import com.example.band_activity.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CancelActivity extends Command {
    @NotNull
    private Long activityId;
    @NotNull
    private Long clubId;

    public CancelActivity(String username, Long activityId, Long clubId) {
        super(username);
        this.activityId = activityId;
        this.clubId = clubId;
    }
}
