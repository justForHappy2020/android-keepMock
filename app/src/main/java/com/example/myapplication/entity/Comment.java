package com.example.myapplication.entity;

public class Comment {
    private User user;
    private String textContent;
    private int commentId;
    private int likeNum;
    private String commentTime;
    private int subCommentNum;

    public Comment() {
    }

    public Comment(User user, String textContent, int commentId,int likeNum,String commentTime,int subCommentNum) {
        this.user = user;
        this.textContent = textContent;
        this.commentId = commentId;
        this.likeNum=likeNum;
        this.commentTime=commentTime;
        this.subCommentNum=subCommentNum;
    }

    public User getUser() {
        return user;
    }

    public String getTextContent() {
        return textContent;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public int getSubCommentNum() {
        return subCommentNum;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public void setSubCommentNum(int subCommentNum) {
        this.subCommentNum = subCommentNum;
    }
}
