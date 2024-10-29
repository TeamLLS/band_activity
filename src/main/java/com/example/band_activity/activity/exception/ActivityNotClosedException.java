package com.example.band_activity.activity.exception;

import lombok.Getter;

@Getter
public class ActivityNotClosedException extends RuntimeException{
    private Long activityId;

    private String current;

    public ActivityNotClosedException(String message, Long activityId, String status) {
        super(message);
        this.activityId = activityId;
        this.current = status;
    }

    public ActivityNotClosedException(String message) {
        super(message);
    }

    public ActivityNotClosedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActivityNotClosedException(Throwable cause) {
        super(cause);
    }
}
