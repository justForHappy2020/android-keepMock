package com.example.myapplication.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MultipleItem implements MultiItemEntity {
    /**
     * 类名：MultipleItem
     * 功能：在构造时根据Share、User、Course、Comment或Movement等实体类生成，相当于一个规范的外包装，利于装入RecyclerView，实现多Item布局、多功能适配器等等
     */
    public static final int TEXTONLY = 1;
    public static final int BUTTON = 2;
    public static final int MINICOURSE = 3;
    public static final int MASONRYPOST = 4;
    public static final int USER = 5;
    public static final int SHARE = 6;
    public static final int ACTION = 7;
    public static final int ADDIMAGE = 8;

    private int itemType;
    private Course course;
    private Share share;
    private User user;
    private String text;
    private Action action;
    private AddImage addimage;

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

    public MultipleItem(int itemType, Action action){
        this.itemType = itemType;
        this.action = action;
    }

    public MultipleItem(int itemType,AddImage addimage) {
        this.itemType = itemType;
        this.addimage = addimage;
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


    public Action getAction(){
        return action;
    }

    public AddImage getAddimage() { return addimage;}
}