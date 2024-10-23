package com.example.band_activity.participant;

public enum ParticipantStatus {
    ATTEND("참가"),
    NOT_ATTEND("불참"),
    ADDITIONAL_ATTEND("추가 참가"),
    ADDITIONAL_NOT_ATTEND("추가 불참");


    private final String display;

    ParticipantStatus(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
