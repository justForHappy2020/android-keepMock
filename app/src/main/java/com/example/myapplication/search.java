package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class search extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final TextView tv = findViewById(R.id.searching_history);
        final EditText et = findViewById(R.id.text_inout_search);

        findViewById(R.id.searching_button).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){//点击查找进行课程搜索以及存储搜索记录，目前只能存一个
                String inputSrt = et.getText().toString().trim();
                SharedPreferences shp = getSharedPreferences("history", MODE_PRIVATE);
                SharedPreferences.Editor editor = shp.edit();
                editor.putString("name",inputSrt);
                editor.commit();

                Intent i = new Intent(search.this , search_result.class);//启动课程结果activity
                i.putExtra("searchContent",inputSrt);//传递参数：搜索内容
                startActivity(i);//启动
            }
        });

        final SharedPreferences shp = getSharedPreferences("history",MODE_PRIVATE);
        String s = shp.getString("name"," ");
        tv.setText(s);
        findViewById(R.id.clean_history).setOnClickListener(new View.OnClickListener() {//点击清除历史记录清除历史记录
            public void onClick(View v) {
                SharedPreferences.Editor editor = shp.edit();
                editor.clear();
                editor.commit();
            }
        });
        findViewById(R.id.quit_button).setOnClickListener(new View.OnClickListener() {//点击取消按钮返回到课程主页
            public void onClick (View v){
                finish();

                //Intent i = new Intent(search.this , exercise_main.class);
                //startActivity(i);//启动
            }
        });
    }

}