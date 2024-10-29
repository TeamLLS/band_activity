package com.example.band_activity.activity.exception;

import lombok.Getter;

@Getter
public class ActivityNotOpenedException extends RuntimeException{
    private Long activityId;

    private String current;

    public ActivityNotOpenedException(String message, Long activityId, String status) {
        super(message);
        this.activityId = activityId;
        this.current = status;
    }

    public ActivityNotOpenedException(String message) {
        super(message);
    }

    public ActivityNotOpenedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActivityNotOpenedException(Throwable cause) {
        super(cause);
    }
}
