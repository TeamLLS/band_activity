package com.example.band_activity.activity;


public enum ActivityStatus {
    OPENED("모집중"),
    CANCELED("활동 취소"),
    CLOSED("모집 종료");

    private final String display;

    ActivityStatus(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
