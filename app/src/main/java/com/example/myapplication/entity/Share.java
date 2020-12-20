package com.example.myapplication.entity;

public class Share {

    private int user_heads;
    private String content_pics;
    private int shareType;
    private int shareId;
    private int userId;
    private String nickname;
    private String headPortraitUrl;
    private String relations;
    private String imgUrls;
    private String contents;
    private String likeNumbers;
    private String commentsNumbers;
    private String createTime;
    public Share(){

    }
    public Share(String user_id, String content, int user_head, String content_pic, String praise, String comment){
        this.nickname = user_id;
        this.contents = content;
        this.user_heads = user_head;
        this.content_pics = content_pic;
        this.likeNumbers = praise;
        this.commentsNumbers = comment;
    }
    public Share(String user_id, String content, int user_head, String content_pic, String comment){
        this.nickname = user_id;
        this.contents = content;
        this.user_heads = user_head;
        this.content_pics = content_pic;
        this.commentsNumbers = comment;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public String getCreateTime() {
        return createTime;
    }
    private String users_id;
    private String praises;
    private String comments;



    public String getComments() {return comments; }

    public String getPraises() { return praises; }


    public String getContents() {
        return contents;
    }

    public int getUser_heads() {
        return user_heads;
    }

    public String getContent_pics() {
        return content_pics;
    }

    public int getShareId() {
        return shareId;
    }

    public int getShareType() {
        return shareType;
    }

    public int getUserId() {
        return userId;
    }

    public String getCommentsNumbers() {
        return commentsNumbers;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public String getLikeNumbers() {
        return likeNumbers;
    }

    public String getRelations() {
        return relations;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUser_heads(int user_heads) {
        this.user_heads = user_heads;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public String getUsers_id() {
        return users_id;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setContent_pics(String content_pics) {
        this.content_pics = content_pics;
    }


    public void setCommentsNumbers(String commentsNumbers) {
        this.commentsNumbers = commentsNumbers;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public void setLikeNumbers(String likeNumbers) {
        this.likeNumbers = likeNumbers;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public void setPraises(String praises) {
        this.praises = praises;
    }


    public void setUsers_id(String users_id) {
        this.users_id = users_id;

    }
}

