package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class community3 extends AppCompatActivity {
    private View.OnClickListener onClickListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community3);
    }
    public void initView(){
        ImageButton c3_goback = findViewById(R.id.community3_LeftArrow_btn);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommunity3Click(v);
            }
        };
        c3_goback.setOnClickListener(onClickListener);
    }
    public void onCommunity3Click(View v){
        switch (v.getId()){
            case R.id.community3_LeftArrow_btn:
                finish();
                break;
        }
    }
}
