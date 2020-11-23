package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class register4 extends AppCompatActivity implements View.OnClickListener{

    private ImageButton ibIsFemale;
    private ImageButton ibIsMale;
    private Button btGenderNext;
    private Intent intentAccept;
    private String file_url;
    private String nickName;
    private String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);

        initView();
        initData();
    }

    private void initView() {
        ibIsFemale = findViewById(R.id.is_female);
        ibIsMale = findViewById(R.id.is_male);
        btGenderNext = findViewById(R.id.gender_next);

        ibIsFemale.setOnClickListener(this);
        ibIsMale.setOnClickListener(this);
        btGenderNext.setOnClickListener(this);
    }

    private void initData() {
        intentAccept = getIntent();
        file_url = intentAccept.getStringExtra("file_url");
        nickName = intentAccept.getStringExtra("nickName");
        gender = "m".trim();
    }

    public void onClick(View view) {
        int tint1 = Color.parseColor("#4CAF50");
        int tint2 = Color.WHITE;
        ibIsMale.getBackground().setColorFilter(tint1, PorterDuff.Mode.DARKEN);
        switch (view.getId()) {
            case R.id.is_female:
                gender = "w".trim();
                ibIsFemale.getBackground().setColorFilter(tint1, PorterDuff.Mode.DARKEN);
                ibIsMale.getBackground().setColorFilter(tint2, PorterDuff.Mode.DARKEN);
                break;
            case R.id.is_male:
                gender = "m".trim();
                ibIsMale.getBackground().setColorFilter(tint1, PorterDuff.Mode.DARKEN);
                ibIsFemale.getBackground().setColorFilter(tint2, PorterDuff.Mode.DARKEN);
                break;
            case R.id.gender_next:
                Intent intent2 = new Intent(this, register5.class);
                intent2.putExtra("file_url", file_url);
                intent2.putExtra("nickName", nickName);
                intent2.putExtra("gender", gender);
                startActivity(intent2);
                break;
        }
    }
}
