package com.example.myapplication.entity;

public class Movement {
    private String movementId;
    private String movementName;
    private String duration;
    private String backgroundUrl;

    public Movement(String movementId, String movementName, String duration, String backgroundUrl) {
        this.movementId = movementId;
        this.movementName = movementName;
        this.duration = duration;
        this.backgroundUrl = backgroundUrl;
    }

    public String getMovementId() {
        return movementId;
    }

    public void setMovementId(String movementId) {
        this.movementId = movementId;
    }

    public String getMovementName() {
        return movementName;
    }

    public void setMovementName(String movementName) {
        this.movementName = movementName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }
}
