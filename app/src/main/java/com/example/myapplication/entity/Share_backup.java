package com.example.myapplication.entity;

import java.util.Date;

public class Share_backup {
    private int shareType;//[动态类型][dailyShare1]
    private Long shareId;
    private Long userId;
    private String headPortraitUrl;
    private String nickname;
    private int relations;//[陌生人0/关注1/粉丝2/互相关注3]
    private String imgUrls;
    private String content;//[部分内容？]
    private int likeNumbers;
    private int commentNumbers;
    private Date createTime;


    private int post_img;
    private int portrait_img;
    private String title;
    private String textContent;
    private String userName;


    public Share_backup(int img, int portrait_img, String title, String textContent, String userName, int likeNumbers) {
        this.post_img = img;
        this.portrait_img = portrait_img;
        this.title = title;
        this.textContent = textContent;
        this.userName = userName;
        this.likeNumbers = likeNumbers;
    }

    public int getPostImg() {
        return post_img;
    }

    public int getPortraitImg(){
        return  portrait_img;
    }

    public void setPostImg(int post_img) {
        this.post_img = post_img;
    }

    public void setPortraitImg(int portrait_img){
        this.portrait_img = portrait_img;
    }

    public String getTitle() {
        return title;
    }

    public String getTextContent(){
        return textContent;
    }

    public  String getUserName(){
        return userName;
    }

    public int getLikeNumbers(){
        return likeNumbers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextContent(String textContent){
        this.textContent=textContent;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setLikeNumbers(int num){
        this.likeNumbers = num;
    }


}
