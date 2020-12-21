package com.example.myapplication.entity;


public class User_backup {
    private int userId;
    private String userName;
    private int portraitImg;

    public User_backup(int userId,String userName,int portraitImg){
        this.userId=userId;
        this.userName=userName;
        this.portraitImg=portraitImg;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getPortraitImg() {
        return portraitImg;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPortraitImg(int portraitImg) {
        this.portraitImg = portraitImg;
    }
}
