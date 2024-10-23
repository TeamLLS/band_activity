package com.example.band_activity.activity.exception;

import lombok.Getter;

@Getter
public class NotOpenedActivityException extends RuntimeException{
    private Long activityId;

    private String status;

    public NotOpenedActivityException(String message, Long activityId, String status) {
        super(message);
        this.activityId = activityId;
        this.status = status;
    }

    public NotOpenedActivityException(String message) {
        super(message);
    }

    public NotOpenedActivityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotOpenedActivityException(Throwable cause) {
        super(cause);
    }
}
