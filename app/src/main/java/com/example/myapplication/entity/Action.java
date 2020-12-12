package com.example.myapplication.entity;

import java.io.Serializable;

public class Action implements Serializable {
    private Long actionId;
    private String actionName;
    private String actionImgs;
    private String actionUrl;
    private String duration;
    private Long introId;
    private String intro;

    public Action() {

    }

    public Action(Long actionId, String actionName, String actionImgs, String actionUrl, String duration, Long introId, String intro) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.actionImgs = actionImgs;
        this.actionUrl = actionUrl;
        this.duration = duration;
        this.introId = introId;
        this.intro = intro;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionImgs() {
        return actionImgs;
    }

    public void setActionImgs(String actionImgs) {
        this.actionImgs = actionImgs;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getIntroId() {
        return introId;
    }

    public void setIntroId(Long introId) {
        this.introId = introId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
