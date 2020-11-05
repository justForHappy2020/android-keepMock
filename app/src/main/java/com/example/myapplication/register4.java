package com.example.myapplication;

import android.content.Intent;
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
    }

    public void onClick(View view) {
        String gender = "m".trim();
        switch (view.getId()) {
            case R.id.is_female:
                gender = "w".trim();
                break;
            case R.id.is_male:
                gender = "m".trim();
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
