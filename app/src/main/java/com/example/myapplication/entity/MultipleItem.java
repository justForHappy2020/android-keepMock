package com.example.myapplication.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MultipleItem implements MultiItemEntity {
    public static final int TEXTONLY = 1;
    public static final int BUTTON = 2;
    public static final int MINICOURSE = 3;
    public static final int MASONRYPOST = 4;
    public static final int USER = 5;
    public static final int SHARE = 6;
    public static final int MOVEMENT = 7;

    private int itemType;
    private Course course;
    private Share share;
    private User user;
    private String text;
    private Movement movement;

    public MultipleItem(int itemType,String text) {
        this.itemType = itemType;
        this.text = text;
    }

    public MultipleItem(int itemType, Share share) {
        this.itemType = itemType;
        this.share = share;
    }

    public MultipleItem(int itemType,Course course) {
        this.itemType = itemType;
        this.course=course;
    }
    public MultipleItem(int itemType, User user){
        this.itemType = itemType;
        this.user = user;
    }

    public MultipleItem(int itemType, Movement movement){
        this.itemType = itemType;
        this.movement = movement;
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

    public Share getShare(){
        return share;
    }

    public User getUser() {
        return user;
    }

    public Movement getMovement(){
        return movement;
    }
}