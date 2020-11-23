package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.example.myapplication.entity.CourseClass;
import com.example.myapplication.utils.HttpUtils;
import com.example.myapplication.entity.Course;

import org.json.JSONArray;
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
    private List<CourseClass> bodyPart = new ArrayList<>();
    private List<CourseClass> degree = new ArrayList<>();
    private List<Course> hotCourse = new ArrayList();
    private List<Course> courseList = new ArrayList();
    //private CourseClass allCourse = new CourseClass();

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


    //获取热门课程
    private void initHotCourse(){
        String url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/course/getHotCourse10";
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
                //得到hotCourse的list
                JSONArray JSONArrayCourse = jsonObject2.getJSONArray("courseList");
                for (int i = 0; i < JSONArrayCourse.length(); i++) {
                    JSONObject jsonObject = JSONArrayCourse.getJSONObject(i);
                    //相应的内容
                    hotCourse.get(i).setCourseId(jsonObject.getLong("courseId"));
                    hotCourse.get(i).setCourseName(jsonObject.getString("courseName"));
                    hotCourse.get(i).setBackgroundUrl(jsonObject.getString("backgroundUrl"));
                    hotCourse.get(i).setCourseUrl(jsonObject.getString("courseUrl"));
                    hotCourse.get(i).setBodyPart(jsonObject.getString("bodyPart"));
                    hotCourse.get(i).setDegree(jsonObject.getString("degree"));
                    hotCourse.get(i).setDuration(jsonObject.getString("duration"));
                    hotCourse.get(i).setHits(jsonObject.getInt("hits"));
                    hotCourse.get(i).setCreateTime(jsonObject.getString("createTime"));
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++)btHotcourse[i].setText(hotCourse.get(i).getCourseName() + "\n" + hotCourse.get(i).getDuration() + "  " + hotCourse.get(i).getDegree() );//10个BUTTON中录入热门课程
    }


    //获取部位标签
    private void initBodypart(){
        String url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/course/getFilter";
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
                JSONArray JSONArrayBodyPart = jsonObject2.getJSONArray("bodyPart");
                JSONArray JSONArrayDegree = jsonObject2.getJSONArray("degree");
                for (int i = 0; i < JSONArrayBodyPart.length(); i++) {
                    JSONObject jsonObject3 = JSONArrayBodyPart.getJSONObject(i);
                    //相应的内容
                    bodyPart.get(i).setCourseClassId(jsonObject3.getLong("courseClassId"));
                    bodyPart.get(i).setClassName(jsonObject3.getString("className"));
                    bodyPart.get(i).setClassValue(jsonObject3.getString("classValue"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 8; i++)btBodypart[i].setText(bodyPart.get(i).getClassValue());
        bodyPart.get(7).setClassValue("all");
        btBodypart[7].setText(bodyPart.get(7).getClassValue());
    }


    //设置用户播放时长
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
        Intent intent = null;
        switch (view.getId()) {
            case R.id.search:
                intent = new Intent(this, search_result.class);
                startActivity(intent);
                break;
                //筛选课程http、跳转对应的筛选主页
            case R.id.bodypart1:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(0));
                startActivity(intent);
                break;
            case R.id.bodypart2:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(1));
                startActivity(intent);
                break;
            case R.id.bodypart3:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(2));
                startActivity(intent);
                break;
            case R.id.bodypart4:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(3));
                startActivity(intent);
                break;
            case R.id.bodypart5:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(4));
                startActivity(intent);
                break;
            case R.id.bodypart6:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(5));
                startActivity(intent);
                break;
            case R.id.bodypart7:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(6));
                startActivity(intent);
                break;
                //待做：筛选全部
            case R.id.bodypart8:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(7));
                startActivity(intent);
                break;
            case R.id.hotcourse1:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(0).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse2:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(1).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse3:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(2).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse4:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(3).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse5:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(4).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse6:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(5).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse7:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(6).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse8:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(7).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse9:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(8).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse10:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",hotCourse.get(9).getCourseId());
                startActivity(intent);
                break;
        }
    }
}