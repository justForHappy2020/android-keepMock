package com.example.myapplication.Beans;

public class HttpRequest {
    private int code;
    private String message;
    private DailyShareRetBean data;

    public HttpRequest() {
    }

    public HttpRequest(int code, String message, DailyShareRetBean data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DailyShareRetBean getData() {
        return data;
    }

    public void setData(DailyShareRetBean data) {
        this.data = data;
    }
}
