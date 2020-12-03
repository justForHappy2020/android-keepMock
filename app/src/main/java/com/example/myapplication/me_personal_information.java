package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class me_personal_information extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_personal_information);
        initView();
    }
    public void initView(){
        ImageButton bt_perif_goback = findViewById(R.id.personinfor_goback);
        onClick();
    }
    public void onClick(){
        findViewById(R.id.personinfor_goback).setOnClickListener(new View.OnClickListener() {//点击取消按钮返回到课程主页
            public void onClick (View v){
                finish();
            }
        });
    }
}
