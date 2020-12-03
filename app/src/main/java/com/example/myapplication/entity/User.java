package com.example.myapplication.entity;


import android.widget.ImageView;

public class User {
    private String title;
    private String imgUrl;
    private int user_head;

    public User(String title, int user_head){
        this.title = title;
        this.user_head = user_head;
    }

    //生成set、get方法

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getUser_head() {
        return user_head;
    }

    public void setUser_head(int user_head) {
        this.user_head = user_head;
    }


    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}