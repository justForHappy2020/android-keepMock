package com.example.myapplication.entity;

import java.io.Serializable;

public class ShareAbb implements Serializable {

    private Long userId;
    private String headPortraitUrl;
    private String nickName;
    private int relations;

    //private User user;

    private int shareType;
    private int shareId;
    private String imgUrls;
    private String content;
    private String likeNumbers;
    private String commentNumbers;
    private String createTime;
    private boolean like;

    public ShareAbb(){

    }

    public ShareAbb(Long userId, String headPortraitUrl, String nickName, int relations, int shareType, int shareId, String imgUrls, String content, String likeNumbers, String commentNumbers, String createTime, boolean like) {
        this.userId = userId;
        this.headPortraitUrl = headPortraitUrl;
        this.nickName = nickName;
        this.relations = relations;
        this.shareType = shareType;
        this.shareId = shareId;
        this.imgUrls = imgUrls;
        this.content = content;
        this.likeNumbers = likeNumbers;
        this.commentNumbers = commentNumbers;
        this.createTime = createTime;
        this.like = like;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    /*
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
     */

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLikeNumbers() {
        return likeNumbers;
    }

    public void setLikeNumbers(String likeNumbers) {
        this.likeNumbers = likeNumbers;
    }

    public String getCommentNumbers() {
        return commentNumbers;
    }

    public void setCommentNumbers(String commentNumbers) {
        this.commentNumbers = commentNumbers;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

