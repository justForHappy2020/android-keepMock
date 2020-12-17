package com.example.myapplication.entity;

public class ActionCom {
    private Long actionId;
    private String actionName;
    private String actionImgs;
    private String actionUrl;
    private String duration;
    private Long introId;
    private String actionGif;
    private String keyPoint;
    private String breath;
    private String feel;
    private String fellImg;
    private String mistake;

    public ActionCom(Long actionId, String actionName, String actionImgs, String actionUrl, String duration, Long introId, String actionGif, String keyPoint, String breath, String feel, String fellImg, String mistake, String detail, String detailImg) {
        this.actionId = actionId;
        this.actionName = actionName;
        this.actionImgs = actionImgs;
        this.actionUrl = actionUrl;
        this.duration = duration;
        this.introId = introId;
        this.actionGif = actionGif;
        this.keyPoint = keyPoint;
        this.breath = breath;
        this.feel = feel;
        this.fellImg = fellImg;
        this.mistake = mistake;
        this.detail = detail;
        this.detailImg = detailImg;
    }

    private String detail;
    private String detailImg;

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

    public String getActionGif() {
        return actionGif;
    }

    public void setActionGif(String actionGif) {
        this.actionGif = actionGif;
    }

    public String getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(String keyPoint) {
        this.keyPoint = keyPoint;
    }

    public String getBreath() {
        return breath;
    }

    public void setBreath(String breath) {
        this.breath = breath;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public String getFellImg() {
        return fellImg;
    }

    public void setFellImg(String fellImg) {
        this.fellImg = fellImg;
    }

    public String getMistake() {
        return mistake;
    }

    public void setMistake(String mistake) {
        this.mistake = mistake;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }
}
