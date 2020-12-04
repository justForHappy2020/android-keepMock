package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class me_apply extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_apply);
        initView();
    }

    public void initView(){
        ImageButton bt_applygoback = findViewById(R.id.apply_goback);
        EditText et_realname = findViewById(R.id.et_realname);
        EditText et_phone = findViewById(R.id.et_phone);
        EditText et_licence = findViewById(R.id.et_licence);
        EditText et_organization = findViewById(R.id.et_organization);
        EditText et_title = findViewById(R.id.et_title);
        EditText et_discribe = findViewById(R.id.et_discribe);
        onClick();
    }
    public void onClick(){
        findViewById(R.id.apply_goback).setOnClickListener(new View.OnClickListener() {//点击取消按钮返回到课程主页
            public void onClick (View v){
                finish();
            }
        });
    }
}
