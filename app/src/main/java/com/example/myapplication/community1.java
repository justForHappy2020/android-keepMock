package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class community1 extends AppCompatActivity {

    private View.OnClickListener onClickListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community1);
        initView();
    }
    public void initView(){
        ImageButton c1_goback = findViewById(R.id.community1_leftarrow);
        c1_goback.setOnClickListener(onClickListener);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommunityoneClick(view);
            }
        };

    }
    public void onCommunityoneClick(View v){
        switch(v.getId()){
            case R.id.community1_leftarrow:
                finish();
                break;
        }
    }
}
