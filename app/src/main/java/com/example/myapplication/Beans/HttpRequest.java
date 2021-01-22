package com.example.myapplication.Beans;

public class HttpRequest {
    private int code;
    private String message;
    private ResponseDataBean data;

    public HttpRequest() {
    }

    public HttpRequest(int code, String message, ResponseDataBean data) {
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

    public ResponseDataBean getData() {
        return data;
    }

    public void setData(ResponseDataBean data) {
        this.data = data;
    }
}
