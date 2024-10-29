package com.example.band_activity.activity.exception;


import lombok.Getter;

@Getter
public class ActivityNotInClubException extends RuntimeException{
    private Long activityId;

    private Long clubId;

    public ActivityNotInClubException (String message, Long activityId, Long clubId) {
        super(message);
        this.activityId = activityId;
        this.clubId = clubId;
    }

    public ActivityNotInClubException (String message) {
        super(message);
    }

    public ActivityNotInClubException (String message, Throwable cause) {
        super(message, cause);
    }

    public ActivityNotInClubException (Throwable cause) {
        super(cause);
    }
}
