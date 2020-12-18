package com.example.myapplication.entity;

public class AddImage {
    private  String imgUrl;
    private int iId;

    public AddImage(int iID, String imgUrl){
        this.iId = iId;
        this.imgUrl = imgUrl;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
