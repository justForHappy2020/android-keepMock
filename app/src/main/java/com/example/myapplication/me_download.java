package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class me_download extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_download);
        initView();
    }
    public void initView(){
        TextView tv_videodata = findViewById(R.id.text_video_data);
        ImageButton bt_clean = findViewById(R.id.btn_clean_useless_video);
    }
}
