package com.example.myapplication.entity;

public class Share {
    private int post_img;
    private int portrait_img;
    private String title;
    private String textContent;
    private String userName;
    private String notificationNum;

    public Share(int img, int portrait_img, String title, String textContent, String userName, String notificationNum) {
        this.post_img = img;
        this.portrait_img = portrait_img;
        this.title = title;
        this.textContent = textContent;
        this.userName = userName;
        this.notificationNum = notificationNum;
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

    public String getNotificationNum(){
        return  notificationNum;
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

    public void setNotificationNum(int num){
        this.notificationNum = String.valueOf(num);
    }


}
