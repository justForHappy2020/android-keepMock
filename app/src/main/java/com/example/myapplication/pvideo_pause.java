package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

public class pvideo_pause extends Activity implements View.OnClickListener{
    private ImageButton ibQuit;
    private ImageButton ibKeepTrain;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamepage_specificcourses_quitex);

        initView();
    }

    private void initView() {
        ibQuit = findViewById(R.id.quit);
        ibKeepTrain = findViewById(R.id.keepTrain);

        ibQuit.setOnClickListener(this);
        ibKeepTrain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.quit:
                intent = new Intent(this,course_main.class);
                startActivity(intent);
                break;
            case R.id.keepTrain:
                finish();
                break;
        }
    }
}
