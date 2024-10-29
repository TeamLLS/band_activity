package com.example.band_activity.core;

import com.example.band_activity.activity.command.CancelActivity;
import com.example.band_activity.activity.command.CloseActivity;
import com.example.band_activity.activity.command.OpenActivity;
import com.example.band_activity.participant.command.AttendActivity;
import com.example.band_activity.participant.command.NotAttendActivity;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OpenActivity.class, name = "OpenActivity"),
        @JsonSubTypes.Type(value = CancelActivity.class, name = "CancelActivity"),
        @JsonSubTypes.Type(value = CloseActivity.class, name = "CloseActivity"),
        @JsonSubTypes.Type(value = AttendActivity.class, name = "AttendActivity"),
        @JsonSubTypes.Type(value = NotAttendActivity.class, name = "NotAttendActivity"),
})
public abstract class Command {

    @NotNull
    private String username;

    public Command() {
    }

    public Command(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
