package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class exercise_main extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibSearch;
    private Button btBodypart[] = new Button[8];
    private Button btHotcourse[] = new Button[10];
    private TextView tvCountTime;
    private List<String> bodyPart;
    private List<String> degree;
    private List hotCourse = new ArrayList();

    private int httpcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_main);

        initView();
        initCountTime();
        initBodypart();
        initHotCourse();
    }

    private void initHotCourse(){
        String url = "http://192.168.16.1:8080/api/course/getHotCourse10";
        String responseData = null;
        try {
            responseData = HttpUtils.connectHttpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = null;
        try {
            jsonObject1 = new JSONObject(responseData);
            httpcode = jsonObject1.getInt("code");
            if(httpcode == 200){
                JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                //待写：得到hotCourse的list



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++)btHotcourse[i].setText(hotCourse.indexOf(1));//待写：得到热门课程的名称

    }

    private void initBodypart(){
        String url = "http://192.168.16.1:8080/api/course/getFilter";
        String responseData = null;
        try {
            responseData = HttpUtils.connectHttpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = null;
        try {
            jsonObject1 = new JSONObject(responseData);
            httpcode = jsonObject1.getInt("code");
            if(httpcode == 200){
                JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                //bodyPart = jsonObject2.getJSONArray("bodyPart");
                //待写：得到bodypart、degree 的list




            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 8; i++)btBodypart[i].setText(bodyPart.get(0));
    }

    private void initCountTime()  {
        String countTime = "0";
        String url = "http://192.168.16.1:8080/api/course/getCountTime";
        String responseData = null;
        try {
            responseData = HttpUtils.connectHttpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = null;
        try {
            jsonObject1 = new JSONObject(responseData);
            httpcode = jsonObject1.getInt("code");
            if(httpcode == 200)countTime = String.valueOf(jsonObject1.getInt("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvCountTime.setText("已累计运动" + countTime + "分钟");
    }

    private void initView() {
        tvCountTime = findViewById(R.id.accumulate_time);
        ibSearch = findViewById(R.id.search);
        btBodypart[0] = findViewById(R.id.bodypart1);
        btBodypart[1] = findViewById(R.id.bodypart2);
        btBodypart[2] = findViewById(R.id.bodypart3);
        btBodypart[3] = findViewById(R.id.bodypart4);
        btBodypart[4] = findViewById(R.id.bodypart5);
        btBodypart[5] = findViewById(R.id.bodypart6);
        btBodypart[6] = findViewById(R.id.bodypart7);
        btBodypart[7] = findViewById(R.id.bodypart8);

        btHotcourse[0] = findViewById(R.id.hotcourse1);
        btHotcourse[1] = findViewById(R.id.hotcourse2);
        btHotcourse[2] = findViewById(R.id.hotcourse3);
        btHotcourse[3] = findViewById(R.id.hotcourse4);
        btHotcourse[4] = findViewById(R.id.hotcourse5);
        btHotcourse[5] = findViewById(R.id.hotcourse6);
        btHotcourse[6] = findViewById(R.id.hotcourse7);
        btHotcourse[7] = findViewById(R.id.hotcourse8);
        btHotcourse[8] = findViewById(R.id.hotcourse9);
        btHotcourse[9] = findViewById(R.id.hotcourse10);



        ibSearch.setOnClickListener(this);//监听获取验证码按钮
        int i;
        for (i = 0; i < 8; i++) btBodypart[i].setOnClickListener(this);
        for (i = 0; i < 10; i++) btHotcourse[i].setOnClickListener(this);


    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                break;
            case R.id.bodypart1:
                break;
            case R.id.bodypart2:
                break;
            case R.id.bodypart3:
                break;
            case R.id.bodypart4:
                break;
            case R.id.bodypart5:
                break;
            case R.id.bodypart6:
                break;
            case R.id.bodypart7:
                break;
            case R.id.bodypart8:
                break;
            case R.id.hotcourse1:
                break;
            case R.id.hotcourse2:
                break;
            case R.id.hotcourse3:
                break;
            case R.id.hotcourse4:
                break;
            case R.id.hotcourse5:
                break;
            case R.id.hotcourse6:
                break;
            case R.id.hotcourse7:
                break;
            case R.id.hotcourse8:
                break;
            case R.id.hotcourse9:
                break;
            case R.id.hotcourse10:
                break;
        }
    }
}