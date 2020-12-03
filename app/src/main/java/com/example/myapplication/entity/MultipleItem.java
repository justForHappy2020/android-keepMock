package com.example.myapplication.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MultipleItem implements MultiItemEntity {
    public static final int TEXTONLY = 1;
    public static final int BUTTON = 2;
    public static final int MINICOURSE = 3;
    public static final int MASONRYPOST = 4;
    public static final int USER = 5;
    public static final int SHARE = 6;

    private int itemType;
    private Course course;
    private Post post;
    private User user;
    private String text;
    private Share share;

    public MultipleItem(int itemType,String text) {
        this.itemType = itemType;
        this.text = text;
    }

    public MultipleItem(int itemType,Post post) {
        this.itemType = itemType;
        this.post=post;
    }

    public MultipleItem(int itemType,Course course) {
        this.itemType = itemType;
        this.course=course;
    }
    public MultipleItem(int itemType, User user){
        this.itemType = itemType;
        this.user = user;
    }
    public MultipleItem(int itemType, Share share){
        this.itemType = itemType;
        this.share = share;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getText(){
        return text;
    }

    public Course getCourse(){
        return course;
    }

    public Post getPost(){
        return post;
    }

    public User getUser() {
        return user;
    }

    public Share getShare() { return share; }
}