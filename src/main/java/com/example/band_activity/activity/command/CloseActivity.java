package com.example.band_activity.activity.command;

import com.example.band_activity.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CloseActivity extends Command {
    @NotNull
    private Long activityId;

    public CloseActivity(String username, Long activityId, Long clubId) {
        super(username, clubId);
        this.activityId = activityId;
    }
}
