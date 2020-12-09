package com.example.myapplication.entity;

public class AddImage {
    private  int imgUrl;

    public AddImage(int imgUrl){
        this.imgUrl = imgUrl;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }
}
