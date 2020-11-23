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
import android.widget.Toast;

import com.example.myapplication.entity.Course;
import com.example.myapplication.utils.HttpUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class course_main extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibVideoPlay;
    private Button btVideoPlay;
    private Button  btRelatedCourse[] = new Button[6];
    private List<Course> relatedCourse = new ArrayList();
    private TextView tvCalorie;
    private TextView tvDuration;
    private TextView tvDegree;
    private TextView tvIntro;
    private Course course = new Course();
    private Long courseID;

    private int httpcode;
    //private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_course_main);
        initView();
        startThread();
    }

    private void startThread() {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                final Drawable drawable = loadImageFromNetwork(course.getBackgroundUrl());
                // post() 特别关键，就是到UI主线程去更新图片
                ibVideoPlay.post(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        ibVideoPlay.setImageDrawable(drawable) ;
                    }}) ;
            }

        });
        thread.start();
    }

    //通过课程ID获得课程类
    private Boolean courseId2Course(Long courseId){
        final Long courseID = courseId;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
        String url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/course/courseId2Course?courseId=" + courseID.toString().trim();
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
                course.setCalorie(jsonObject2.getInt("calorie"));
                course.setCourseIntro(jsonObject2.getString("courseIntro"));
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
        if(httpcode!=200) {
            Toast.makeText(course_main.this,"ERROR", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //初始化相关课程
    private void initRelativeCourse(Long courseId){
        final Long courseID = courseId;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
        String url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/course/getRelativeCourse?courseId=" + courseID.toString().trim();
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
                //得到hotCourse的list
                JSONArray JSONArrayCourse = jsonObject1.getJSONArray("data");
                for (int i = 0; i < JSONArrayCourse.length(); i++) {
                    JSONObject jsonObject = JSONArrayCourse.getJSONObject(i);
                    //相应的内容
                    Course course = new Course();
                    course.setCourseId(jsonObject.getLong("courseId"));
                    course.setCourseName(jsonObject.getString("courseName"));
                    course.setBackgroundUrl(jsonObject.getString("backgroundUrl"));
                    course.setCourseUrl(jsonObject.getString("courseUrl"));
                    course.setBodyPart(jsonObject.getString("bodyPart"));
                    course.setDegree(jsonObject.getString("degree"));
                    course.setDuration(jsonObject.getString("duration"));
                    course.setHits(jsonObject.getInt("hits"));
                    course.setCreateTime(jsonObject.getString("createTime"));
                    course.setCalorie(jsonObject.getInt("calorie"));
                    course.setCourseIntro(jsonObject.getString("courseIntro"));
                    relatedCourse.add(i,course);
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
        if(httpcode!=200) Toast.makeText(course_main.this,"ERROR", Toast.LENGTH_SHORT).show();
        else for (int i = 0; i <relatedCourse.size(); i++)btRelatedCourse[i].setText(relatedCourse.get(i).getCourseName() + "\n" + relatedCourse.get(i).getDuration() + "  " + relatedCourse.get(i).getDegree() );//录入相关课程
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
        ibVideoPlay = findViewById(R.id.video_play);
        btVideoPlay = findViewById(R.id.play_video);
        btRelatedCourse[0] = findViewById(R.id.related_course1);
        btRelatedCourse[1] = findViewById(R.id.related_course2);
        btRelatedCourse[2] = findViewById(R.id.related_course3);
        btRelatedCourse[3] = findViewById(R.id.related_course4);
        btRelatedCourse[4] = findViewById(R.id.related_course5);
        btRelatedCourse[5] = findViewById(R.id.related_course6);
        tvCalorie = findViewById(R.id.calorie);
        tvDuration = findViewById(R.id.duration);
        tvDegree = findViewById(R.id.degree);
        tvIntro = findViewById(R.id.course_intro);

        Intent intentAccept = null;
        intentAccept = getIntent();
        courseID=intentAccept.getLongExtra("course",0);

        if(courseId2Course(courseID) == true) {//根据ID获取课程类./courseId2course
            tvCalorie.setText(course.getCalorie() + "千卡");
            tvDuration.setText(course.getDuration());
            tvDegree.setText(course.getDegree());
            tvIntro.setText(course.getCourseIntro());
        }

        initRelativeCourse(courseID);//获取相关课程./getRelativeCourse

        ibVideoPlay.setOnClickListener(this);//监听获取验证码按钮
        btVideoPlay.setOnClickListener(this);//监听获取验证码按钮
        for(int i = 0;i<btRelatedCourse.length;i++) btRelatedCourse[i].setOnClickListener(this);
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
            case R.id.play_video:
                intent = new Intent(this, play.class);
                intent.putExtra("courseUrl",course.getCourseUrl());
                intent.putExtra("courseID",course.getCourseId());
                startActivity(intent);
                break;
            case R.id.related_course1:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(0).getCourseId());
                startActivity(intent);
                finish();
                break;
            case R.id.related_course2:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(1).getCourseId());
                startActivity(intent);
                finish();
                break;
            case R.id.related_course3:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(2).getCourseId());
                startActivity(intent);
                finish();
                break;
            case R.id.related_course4:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(3).getCourseId());
                startActivity(intent);
                finish();
                break;
            case R.id.related_course5:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(4).getCourseId());
                startActivity(intent);
                finish();
                break;
            case R.id.related_course6:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course",relatedCourse.get(5).getCourseId());
                startActivity(intent);
                finish();
                break;
        }
    }
}
