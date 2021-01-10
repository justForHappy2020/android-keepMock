package com.example.myapplication.entity;

import java.io.Serializable;

public class Share implements Serializable {

    private User user;
    private int shareType;
    private int shareId;
    private String imgUrls;
    private String contents;
    private String likeNumbers;
    private String commentsNumbers;
    private String createTime;
    private int relations;
    private boolean like;

    public Share(){

    }


    public Share(User user, int shareType, int shareId, String imgUrls, String contents, String likeNumbers, String commentsNumbers, String createTime, int relations, boolean like) {
        this.user = user;
        this.shareType = shareType;
        this.shareId = shareId;
        this.imgUrls = imgUrls;
        this.contents = contents;
        this.likeNumbers = likeNumbers;
        this.commentsNumbers = commentsNumbers;
        this.createTime = createTime;
        this.relations = relations;
        this.like = like;
    }

    public int getRelations() {
        return relations;
    }

    public void setRelations(int relations) {
        this.relations = relations;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getLikeNumbers() {
        return likeNumbers;
    }

    public void setLikeNumbers(String likeNumbers) {
        this.likeNumbers = likeNumbers;
    }

    public String getCommentsNumbers() {
        return commentsNumbers;
    }

    public void setCommentsNumbers(String commentsNumbers) {
        this.commentsNumbers = commentsNumbers;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

