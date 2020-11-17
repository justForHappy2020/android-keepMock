package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class course_main extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibVideoPlay;
    private Button  btRelatedCourse[] = new Button[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);
        initView();
    }
    private void initView(){
        ibVideoPlay.findViewById(R.id.video_play);
        btRelatedCourse[0].findViewById(R.id.related_course1);
        btRelatedCourse[1].findViewById(R.id.related_course2);
        btRelatedCourse[2].findViewById(R.id.related_course3);
        btRelatedCourse[3].findViewById(R.id.related_course4);
        btRelatedCourse[4].findViewById(R.id.related_course5);
        btRelatedCourse[5].findViewById(R.id.related_course6);

        ibVideoPlay.setOnClickListener(this);//监听获取验证码按钮
        int i;
        for(i = 0;i<6;i++) btRelatedCourse[i].setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_play:
                break;
            case R.id.related_course1:
                break;
            case R.id.related_course2:
                break;
            case R.id.related_course3:
                break;
            case R.id.related_course4:
                break;
            case R.id.related_course5:
                break;
            case R.id.related_course6:
                break;
        }
    }
}
