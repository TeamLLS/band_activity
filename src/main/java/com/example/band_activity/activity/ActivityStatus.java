package com.example.band_activity.activity;


public enum ActivityStatus {
    OPENED("모금중"),
    CANCELED("모금 취소"),
    CLOSED("모금 종료");

    private final String display;

    ActivityStatus(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
