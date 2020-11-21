package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.entity.Course;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class course_main extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibVideoPlay;
    private Button  btRelatedCourse[] = new Button[6];
    private List<Course> relatedCourse = new ArrayList();
    private TextView tvCalorie;
    private TextView tvDuration;
    private TextView tvDegree;
    private TextView tvIntro;
    private Course course;
    private Long courseID;

    private int httpcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);
        initView();
        new Thread(new Runnable(){
            Drawable drawable = loadImageFromNetwork(course.getBackgroundUrl());
            @Override
            public void run() {

                // post() 特别关键，就是到UI主线程去更新图片
                ibVideoPlay.post(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        ibVideoPlay.setImageDrawable(drawable) ;
                    }}) ;
            }

        }).start()  ;
    }

    //通过课程ID获得课程类
    private void courseId2Course(Long courseId){
        String url = "http://192.168.16.1:8080/api/course/courseId2Course?courseId=" + courseId.toString().trim();
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
                //得到course
                course.setCourseId(jsonObject2.getLong("courseId"));
                course.setCourseName(jsonObject2.getString("courseName"));
                course.setBackgroundUrl(jsonObject2.getString("backgroundUrl"));
                course.setCourseUrl(jsonObject2.getString("courseUrl"));
                course.setBodyPart(jsonObject2.getString("bodyPart"));
                course.setDegree(jsonObject2.getString("degree"));
                course.setDuration(jsonObject2.getString("duration"));
                course.setHits(jsonObject2.getInt("hits"));
                course.setCreateTime(jsonObject2.getString("createTime"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化相关课程
    private void initRelativeCourse(Long courseId){
        String url = "http://192.168.16.1:8080/api/course/getRelativeCourse?courseId=" + courseId.toString().trim();
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
                    relatedCourse.get(i).setCourseId(jsonObject.getLong("courseId"));
                    relatedCourse.get(i).setCourseName(jsonObject.getString("courseName"));
                    relatedCourse.get(i).setBackgroundUrl(jsonObject.getString("backgroundUrl"));
                    relatedCourse.get(i).setCourseUrl(jsonObject.getString("courseUrl"));
                    relatedCourse.get(i).setBodyPart(jsonObject.getString("bodyPart"));
                    relatedCourse.get(i).setDegree(jsonObject.getString("degree"));
                    relatedCourse.get(i).setDuration(jsonObject.getString("duration"));
                    relatedCourse.get(i).setHits(jsonObject.getInt("hits"));
                    relatedCourse.get(i).setCreateTime(jsonObject.getString("createTime"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++)btRelatedCourse[i].setText(relatedCourse.get(i).getCourseName() + "\n" + relatedCourse.get(i).getDuration() + "  " + relatedCourse.get(i).getDegree() );//10个BUTTON中录入热门课程
    }

    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "course_background.jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }

        return drawable ;
    }


    private void initView(){
        ibVideoPlay.findViewById(R.id.video_play);
        btRelatedCourse[0].findViewById(R.id.related_course1);
        btRelatedCourse[1].findViewById(R.id.related_course2);
        btRelatedCourse[2].findViewById(R.id.related_course3);
        btRelatedCourse[3].findViewById(R.id.related_course4);
        btRelatedCourse[4].findViewById(R.id.related_course5);
        btRelatedCourse[5].findViewById(R.id.related_course6);
        tvCalorie.findViewById(R.id.calorie);
        tvDuration.findViewById(R.id.duration);
        tvDegree.findViewById(R.id.degree);
        tvIntro.findViewById(R.id.course_intro);

        Intent intentAccept = null;
        intentAccept = getIntent();
        courseID=intentAccept.getLongExtra("course",0);

        courseId2Course(courseID);//根据ID获取课程类./courseId2course

        tvCalorie.setText(course.getCalorie() + "千卡");
        tvDuration.setText(course.getDuration());
        tvDegree.setText(course.getDegree());
        tvIntro.setText(course.getCourseIntro());

        initRelativeCourse(courseID);//获取相关课程./getRelativeCourse

        ibVideoPlay.setOnClickListener(this);//监听获取验证码按钮
        int i;
        for(i = 0;i<6;i++) btRelatedCourse[i].setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.video_play:
                intent = new Intent(this, play.class);
                intent.putExtra("courseUrl",course.getCourseUrl());
                intent.putExtra("courseID",course.getCourseId());
                startActivity(intent);
                break;
            case R.id.related_course1:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(3).getCourseId());
                startActivity(intent);
                break;
            case R.id.related_course2:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(3).getCourseId());
                startActivity(intent);
                break;
            case R.id.related_course3:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(3).getCourseId());
                startActivity(intent);
                break;
            case R.id.related_course4:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(3).getCourseId());
                startActivity(intent);
                break;
            case R.id.related_course5:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(3).getCourseId());
                startActivity(intent);
                break;
            case R.id.related_course6:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(3).getCourseId());
                startActivity(intent);
                break;
        }
    }
}
