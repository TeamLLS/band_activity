package com.example.band_activity.activity;


public enum ActivityStatus {
    OPENED("모집중"),
    CANCELED("취소됨"),
    CLOSED("모집종료");

    private final String display;

    ActivityStatus(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
