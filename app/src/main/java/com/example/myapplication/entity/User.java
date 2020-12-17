package com.example.myapplication.entity;


import android.widget.ImageView;

public class User {
    private Long userId;
    private String phoneNumber;
    private String nickname;
    private String gender;
    private String headPortraitUrl;
    private int type;
    private String createTime;
    private String intro;
    private String password;
    private String token;
    private int user_head;
      private String title;
    private String imgUrl;

    public User(){
    }
    public User(String title, String user_head){
        this.nickname = title;
        this.headPortraitUrl = user_head;
    }

    public User(String title,int user_haed){
        this.nickname = title;
        this.user_head = user_haed;
    }
    //生成set、get方法


    public int getType() {
        return type;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getGender() {
        return gender;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public String getIntro() {
        return intro;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUser_head(int user_head) {
        this.user_head = user_head;
    }

    public int getUser_head() {
        return user_head;
    }


    //生成set、get方法

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }





    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}