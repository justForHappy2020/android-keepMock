package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.CourseClass;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class course_filter extends Activity implements View.OnClickListener {

    private ImageButton ibSearch;
    private ImageButton ibback;
    private Button btReset;
    private Button btSure;
    private Button btBodypart[] = new Button[9];
    private Button btDegree[] = new Button[5];
    private Button btCourse[] = new Button[9];


    private List<CourseClass> bodyPart = new ArrayList<>();
    private List<CourseClass> degree = new ArrayList<>();
    private List<Course> courseList = new ArrayList();
    private CourseClass courseClass;//记录传入的标签

    private int httpcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_filter);
        initView();
        initData();
        initCourse();
    }

    private void initCourse() {
        Intent intentAccept = getIntent();
        courseClass = (CourseClass) intentAccept.getSerializableExtra("bodyPart");
        if (courseClass.getClassValue() == "all") {
            //选中所有BUTTON
            //筛选课程
            //展示课程
        } //else //筛选对应部位的课程，展示课程
    }

    private void initData() {
        //展示标签
        String url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/course/getFilter";
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
            if (httpcode == 200) {
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
                for (int i = 0; i < JSONArrayDegree.length(); i++) {
                    JSONObject jsonObject4 = JSONArrayDegree.getJSONObject(i);
                    degree.get(i).setCourseClassId(jsonObject4.getLong("courseClassId"));
                    degree.get(i).setClassName(jsonObject4.getString("className"));
                    degree.get(i).setClassValue(jsonObject4.getString("classValue"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 9; i++) btBodypart[i].setText(bodyPart.get(i).getClassValue());
        for (int i = 0; i < 5; i++) btDegree[i].setText(degree.get(i).getClassValue());
    }

    private void initView() {
        ibback = findViewById(R.id.go_back_button);
        ibSearch = findViewById(R.id.search_button1);
        btReset = findViewById(R.id.reset);
        btSure = findViewById(R.id.sure);
        btBodypart[0] = findViewById(R.id.button1);
        btBodypart[1] = findViewById(R.id.button2);
        btBodypart[2] = findViewById(R.id.button3);
        btBodypart[3] = findViewById(R.id.button4);
        btBodypart[4] = findViewById(R.id.bt1);
        btBodypart[5] = findViewById(R.id.bt2);
        btBodypart[6] = findViewById(R.id.bt3);
        btBodypart[7] = findViewById(R.id.bt4);
        btBodypart[8] = findViewById(R.id.bt5);

        btDegree[0] = findViewById(R.id.btdifficult1);
        btDegree[1] = findViewById(R.id.btdifficult2);
        btDegree[2] = findViewById(R.id.btdifficult3);
        btDegree[3] = findViewById(R.id.btdifficult4);
        btDegree[4] = findViewById(R.id.btdifficult5);

        btCourse[0] = findViewById(R.id.famous1);
        btCourse[1] = findViewById(R.id.famous2);
        btCourse[2] = findViewById(R.id.famous3);
        btCourse[3] = findViewById(R.id.famous4);
        btCourse[4] = findViewById(R.id.famous5);
        btCourse[5] = findViewById(R.id.famous6);
        btCourse[6] = findViewById(R.id.famous7);
        btCourse[7] = findViewById(R.id.famous8);
        btCourse[8] = findViewById(R.id.famous9);

        ibSearch.setOnClickListener(this);
        btReset.setOnClickListener(this);
        btSure.setOnClickListener(this);
        for (int i = 0; i < 9; i++) btCourse[i].setOnClickListener(this);
        for (int i = 0; i < 5; i++) btDegree[i].setOnClickListener(this);
        for (int i = 0; i < 9; i++) btBodypart[i].setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.search_button1:
                intent = new Intent(this, search_course.class);
                startActivity(intent);
                break;
            case R.id.go_back_button:
                finish();
                break;
            case R.id.sure://待做：筛选选中的标签的课程，展示课程
                break;
            case R.id.reset://待做：标签全部置灰，取消选中
                break;

            //部位标签
            case R.id.button1:
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                break;
            case R.id.button4:
                break;
            case R.id.bt1:
                break;
            case R.id.bt2:
                break;
            case R.id.bt3:
                break;
            case R.id.bt4:
                break;
            case R.id.bt5:
                break;

            //难度标签
            case R.id.btdifficult1:
                break;
            case R.id.btdifficult2:
                break;
            case R.id.btdifficult3:
                break;
            case R.id.btdifficult4:
                break;
            case R.id.btdifficult5:
                break;

            case R.id.famous1:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course", courseList.get(0).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous2:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course", courseList.get(1).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous3:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course", courseList.get(2).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous4:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course", courseList.get(3).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous5:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course", courseList.get(4).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous6:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("course", courseList.get(5).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous7:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("course", courseList.get(6).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous8:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("course", courseList.get(7).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous9:
                intent = new Intent(this, course_filter.class);
                intent.putExtra("course", courseList.get(8).getCourseId());
                startActivity(intent);
                break;
        }
    }
}


/*
    Boolean hasNext = false;
    int totalPages = 1;
    Long bodyClassId = Long.valueOf(0);//部位id值
    Long degreeClassId = Long.valueOf(0);//难度id值
    String url;//http请求的url
                bodyClassId = bodyPart.get(0).getCourseClassId();
                        url = "http://192.168.16.1:8080/api/course/filterCourse?bodyPart=" + bodyClassId + "&&degree=&&currentPage=1";
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
                        hasNext = jsonObject2.getBoolean("hasNext");
                        totalPages = jsonObject2.getInt("totalPages");
                        //得到hotCourse的list
                        JSONArray JSONArrayCourse = jsonObject2.getJSONArray("courseList");
                        for (int i = 0; i < JSONArrayCourse.length(); i++) {
        JSONObject jsonObject = JSONArrayCourse.getJSONObject(i);
        //相应的内容
        courseList.get(i).setCourseId(jsonObject.getLong("courseId"));
        courseList.get(i).setCourseName(jsonObject.getString("courseName"));
        courseList.get(i).setBackgroundUrl(jsonObject.getString("backgroundUrl"));
        courseList.get(i).setCourseUrl(jsonObject.getString("courseUrl"));
        courseList.get(i).setBodyPart(jsonObject.getString("bodyPart"));
        courseList.get(i).setDegree(jsonObject.getString("degree"));
        courseList.get(i).setDuration(jsonObject.getString("duration"));
        courseList.get(i).setHits(jsonObject.getInt("hits"));
        courseList.get(i).setCreateTime(jsonObject.getString("createTime"));
        }
        }
        } catch (JSONException e) {
        e.printStackTrace();
        }*/
