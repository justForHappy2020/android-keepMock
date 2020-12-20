package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class community2 extends AppCompatActivity {

    private View.OnClickListener onClickListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community2);
        initView();
    }
    public void initView(){
        ImageButton c2_goback = findViewById(R.id.community2_LeftArrow_btn);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommunity2Click(v);
            }
        };
        c2_goback.setOnClickListener(onClickListener);
    }
    public void onCommunity2Click(View v){
        switch (v.getId()){
            case R.id.community2_LeftArrow_btn:
                finish();
                break;
        }
    }
}
