package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.CourseClass;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.CourseClass;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class exercise_main extends Fragment{

    private ImageView ivSearch;
    private Button btBodypart[] = new Button[8];
    private Button btHotcourse[] = new Button[10];
    private TextView tvCountTime;
    private List<CourseClass> bodyPart = new ArrayList();
    private List<CourseClass> degree = new ArrayList();
    private List<Course> hotCourse = new ArrayList();
    private List<Course> courseList = new ArrayList();
    //private CourseClass allCourse = new CourseClass();

    private int httpcode;
    private String countTime;//累计分钟数

    private View.OnClickListener onClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_main, container, false);


        initView(view);
        //initCountTime();
        initBodypart();
        initHotCourse();
        return view;
    }


    //获取热门课程
    private void initHotCourse() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //String url = "http://192.168.16.1:8080/api/course/getHotCourse10";
                String url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/course/getHotCourse10";
                String responseData = null;
                try {
                    responseData = HttpUtils.connectHttpGet(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject1 = null;
                try {
                    jsonObject1 = new JSONObject(responseData);
                    int httpcode = jsonObject1.getInt("code");
                    if (httpcode == 200) {
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                        //得到hotCourse的list
                        JSONArray JSONArrayCourse = jsonObject2.getJSONArray("courseList");
                        for (int i = 0; i < JSONArrayCourse.length(); i++) {
                            JSONObject jsonObject = JSONArrayCourse.getJSONObject(i);
                            //相应的内容
                            Course course = new Course();
                            course.setCourseId(jsonObject.getLong("courseId"));
                            course.setCourseName(jsonObject.getString("courseName"));
                            course.setBackgroundUrl(jsonObject.getString("backgroundUrl"));
                            //course.setCourseUrl(jsonObject.getString("courseUrl"));
                            course.setBodyPart(jsonObject.getString("bodyPart"));
                            course.setDegree(jsonObject.getString("degree"));
                            course.setDuration(jsonObject.getString("duration"));
                            course.setHits(jsonObject.getInt("hits"));
                            course.setCreateTime(jsonObject.getString("createTime"));
                            hotCourse.add(i,course);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(httpcode!=200) Toast.makeText(getActivity(),"ERROR", Toast.LENGTH_SHORT).show();
        else for (int i = 0; i < 10; i++)
            btHotcourse[i].setText(hotCourse.get(i).getCourseName() + "\n" + hotCourse.get(i).getDuration() + "  " + hotCourse.get(i).getDegree());//10个BUTTON中录入热门课程
    }


    //获取部位标签
    private void initBodypart(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
        //String url = "http://192.168.16.1:8080/api/course/getFilter";
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
            if(httpcode == 200){
                JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                JSONArray JSONArrayBodyPart = jsonObject2.getJSONArray("bodyPart");
                JSONArray JSONArrayDegree = jsonObject2.getJSONArray("degree");
                for (int i = 0; i < JSONArrayBodyPart.length(); i++) {
                    JSONObject jsonObject3 = JSONArrayBodyPart.getJSONObject(i);
                    //相应的内容
                    CourseClass courseClass = new CourseClass();
                    courseClass.setCourseClassId(jsonObject3.getLong("courseClassId"));
                    courseClass.setClassName(jsonObject3.getString("className"));
                    courseClass.setClassValue(jsonObject3.getString("classValue"));
                    bodyPart.add(i,courseClass);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            }
        });
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(httpcode!=200) Toast.makeText(getActivity(),"ERROR", Toast.LENGTH_SHORT).show();
        else {
            for (int i = 0; i < 8; i++) btBodypart[i].setText(bodyPart.get(i).getClassValue());
            bodyPart.get(7).setClassValue("all");
            btBodypart[7].setText(bodyPart.get(7).getClassValue());
        }
    }


    //设置用户播放时长
    private void initCountTime()  {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
        String url = "http://192.168.16.1:8080/api/course/getCountTime";
        String responseData = null;
        try {
            responseData = HttpUtils.connectHttpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = null;
    }
        });
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(httpcode!=200) Toast.makeText(getActivity(),"ERROR", Toast.LENGTH_SHORT).show();
        else tvCountTime.setText("已累计运动" + countTime + "分钟");
    }

    private void initView(View view) {
        tvCountTime = view.findViewById(R.id.accumulate_time);
        ivSearch = view.findViewById(R.id.search);
        btBodypart[0] = view.findViewById(R.id.bodypart1);
        btBodypart[1] = view.findViewById(R.id.bodypart2);
        btBodypart[2] = view.findViewById(R.id.bodypart3);
        btBodypart[3] = view.findViewById(R.id.bodypart4);
        btBodypart[4] = view.findViewById(R.id.bodypart5);
        btBodypart[5] = view.findViewById(R.id.bodypart6);
        btBodypart[6] = view.findViewById(R.id.bodypart7);
        btBodypart[7] = view.findViewById(R.id.bodypart8);

        btHotcourse[0] = view.findViewById(R.id.hotcourse1);
        btHotcourse[1] = view.findViewById(R.id.hotcourse2);
        btHotcourse[2] = view.findViewById(R.id.hotcourse3);
        btHotcourse[3] = view.findViewById(R.id.hotcourse4);
        btHotcourse[4] = view.findViewById(R.id.hotcourse5);
        btHotcourse[5] = view.findViewById(R.id.hotcourse6);
        btHotcourse[6] = view.findViewById(R.id.hotcourse7);
        btHotcourse[7] = view.findViewById(R.id.hotcourse8);
        btHotcourse[8] = view.findViewById(R.id.hotcourse9);
        btHotcourse[9] = view.findViewById(R.id.hotcourse10);


        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onExerciseClick(view);
            }
        };

        ivSearch.setOnClickListener(onClickListener);//监听获取验证码按钮
        int i;
        for (i = 0; i < 8; i++) btBodypart[i].setOnClickListener(onClickListener);
        for (i = 0; i < 10; i++) btHotcourse[i].setOnClickListener(onClickListener);

    }

    public void onExerciseClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.search:
                intent = new Intent(getActivity(), search.class);
                startActivity(intent);
                break;
            //筛选课程http、跳转对应的筛选主页
            case R.id.bodypart1:
                intent = new Intent(getActivity(), course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(0));
                startActivity(intent);
                break;
                //text

            case R.id.bodypart2:
                intent = new Intent(getActivity(), course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(1));
                startActivity(intent);
                break;
            case R.id.bodypart3:
                intent = new Intent(getActivity(), course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(2));
                startActivity(intent);
                break;
            case R.id.bodypart4:
                intent = new Intent(getActivity(), course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(3));
                startActivity(intent);
                break;
            case R.id.bodypart5:
                intent = new Intent(getActivity(), course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(4));
                startActivity(intent);
                break;
            case R.id.bodypart6:
                intent = new Intent(getActivity(), course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(5));
                startActivity(intent);
                break;
            case R.id.bodypart7:
                intent = new Intent(getActivity(), course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(6));
                startActivity(intent);
                break;
            //待做：筛选全部
            case R.id.bodypart8:
                intent = new Intent(getActivity(), course_filter.class);
                intent.putExtra("bodypart" , bodyPart.get(7));
                startActivity(intent);
                break;
            case R.id.hotcourse1:
                intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course",hotCourse.get(0).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse2:
                intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course",hotCourse.get(1).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse3:
                intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course",hotCourse.get(2).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse4:
                intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course",hotCourse.get(3).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse5:
                intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course",hotCourse.get(4).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse6:
                intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course",hotCourse.get(5).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse7:
                intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course",hotCourse.get(6).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse8:
                intent = new Intent(getActivity(),course_main.class);
                intent.putExtra("course",hotCourse.get(7).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse9:
                intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course",hotCourse.get(8).getCourseId());
                startActivity(intent);
                break;
            case R.id.hotcourse10:
                intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course",hotCourse.get(9).getCourseId());
                startActivity(intent);
                break;
        }
    }
}