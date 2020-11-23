package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class search_course extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        findViewById(R.id.searching_button).setOnClickListener(new View.OnClickListener() {//点击取消按钮返回到课程主页
            public void onClick (View v){
                Intent i = new Intent(search_course.this , search_result.class);//点击搜索按钮跳转到搜索结果
                //启动
                startActivity(i);
            }
        });
        findViewById(R.id.search_back).setOnClickListener(new View.OnClickListener() {//点击取消按钮返回到课程主页
            public void onClick (View v){
                Intent i = new Intent(search_course.this , course_filter.class);//点击返回按钮跳转到课程筛选
                //启动
                startActivity(i);
            }
        });
    }
}