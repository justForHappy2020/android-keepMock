package com.example.myapplication.Beans;

public class TaskInfo {
    private String name;//文件名
    private String path;//文件路径
    private String url;//链接
    private long contentLen;//文件总长度
    private volatile long completedLen;//已完成长度

    public TaskInfo(String name, String path, String url) {
        this.name = name;
        this.path = path;
        this.url = url;
    }

    public TaskInfo(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getContentLen() {
        return contentLen;
    }

    public void setContentLen(long contentLen) {
        this.contentLen = contentLen;
    }

    public long getCompletedLen() {
        return completedLen;
    }

    public void setCompletedLen(long completedLen) {
        this.completedLen = completedLen;
    }
}