package com.example.myapplication.entity;

public class Share {
    private String users_id;
    private String contents;
    private int user_heads;
    private int content_pics;
    private String praises;
    private String comments;
    private User user;

    public Share(String user_id, String content, int user_head, int content_pic, String praise, String comment,User user){
        this.users_id = user_id;
        this.contents = content;
        this.user_heads = user_head;
        this.content_pics = content_pic;
        this.praises = praise;
        this.comments = comment;
        this.user=user;
    }

    public int getContent_pics() {return content_pics; }

    public int getUser_heads() { return user_heads; }

    public String getComments() {return comments; }

    public String getPraises() { return praises; }

    public String getContents() {
        return contents;
    }

    public String getUsers_id() {
        return users_id;
    }

    public User getUser() {
        return user;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setContent_pics(int content_pics) {
        this.content_pics = content_pics;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setPraises(String praises) {
        this.praises = praises;
    }

    public void setUser_heads(int user_heads) {
        this.user_heads = user_heads;
    }

    public void setUsers_id(String users_id) {
        this.users_id = users_id;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

