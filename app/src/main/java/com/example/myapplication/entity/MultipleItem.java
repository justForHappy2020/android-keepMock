package com.example.myapplication.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MultipleItem implements MultiItemEntity {
    public static final int TEXTONLY = 1;
    public static final int BUTTON = 2;
    public static final int MINICOURSE = 3;
    public static final int MASONRYPOST = 4;

    private int itemType;
    private Course course;
    private Share share;
    private String text;

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

}