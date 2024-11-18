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
    public CancelActivity(String username, Long activityId, Long clubId) {
        super(username, clubId);
        this.activityId = activityId;
    }
}
