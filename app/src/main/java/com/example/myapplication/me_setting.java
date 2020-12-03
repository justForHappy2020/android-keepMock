package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class me_setting extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_setting);
        initView();
    }
    public void initView(){
        ImageButton btn_goback = findViewById(R.id.setting_goback);
        ImageButton btn_personalinfor = findViewById(R.id.personal_information);
        ImageButton btn_apply = findViewById(R.id.apply);
        ImageButton btn_invite = findViewById(R.id.invite);
        ImageButton btn_about = findViewById(R.id.about);
        onClick();
    }
    public void onClick(){
        findViewById(R.id.setting_goback).setOnClickListener(new View.OnClickListener() {//点击取消按钮返回到课程主页
            public void onClick (View v){
                finish();
            }
        });
        findViewById(R.id.personal_information).setOnClickListener(new View.OnClickListener() {//点击取消按钮返回到课程主页
            public void onClick (View v){
                Intent i = new Intent(me_setting.this, me_personal_information.class);//点击搜索按钮跳转到搜索结果
                //启动
                startActivity(i);
            }
        });
        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {//点击取消按钮返回到课程主页
            public void onClick (View v){
                Intent i = new Intent(me_setting.this, me_apply.class);//点击搜索按钮跳转到搜索结果
                //启动
                startActivity(i);
            }
        });

    }
}
