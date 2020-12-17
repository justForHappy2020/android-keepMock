package com.example.myapplication.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private Long courseId;

    private String courseName;

    //private String courseUrl;

    private String bodyPart;

    private String degree;

    private String duration;

    private int hits;

    private String createTime;

    private String backgroundUrl;

    private int calorie;

    private String courseIntro;

    private List<Action> actionList = new ArrayList();


    public String getCourseIntro() {
        return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public String getDegree() {
        return degree;
    }

    public String getDuration() {
        return duration;
    }

    public int getHits() {
        return hits;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }
}
