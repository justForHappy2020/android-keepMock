package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class me_body_data extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_body_data);
        initView();
    }
    public void initView(){
        ImageButton btn_bdgoback = findViewById(R.id.body_datagoback);
        onClick();
    }
    public void onClick(){
        findViewById(R.id.body_datagoback).setOnClickListener(new View.OnClickListener() {//点击取消按钮返回到课程主页
            public void onClick (View v){
                finish();
            }
        });
    }
}
