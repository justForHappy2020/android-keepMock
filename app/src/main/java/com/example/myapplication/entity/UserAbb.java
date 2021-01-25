package com.example.myapplication.entity;

public class UserAbb {
    private Long userId;
    private String nickname;
    private String gender;
    private String headPortraitUrl;
    private int type;
    private String intro;

    public UserAbb() {
    }

    public UserAbb(Long userId, String nickname, String gender, String headPortraitUrl, int type, String intro) {
        this.userId = userId;
        this.nickname = nickname;
        this.gender = gender;
        this.headPortraitUrl = headPortraitUrl;
        this.type = type;
        this.intro = intro;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
