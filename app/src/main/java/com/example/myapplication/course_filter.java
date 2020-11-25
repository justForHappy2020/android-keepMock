package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.CourseClass;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.R.color.btn_boder_colorgreen;

public class course_filter extends AppCompatActivity implements View.OnClickListener {

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
    private Drawable background;
    private Boolean hasNext = false;
    private Long currentPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_filter);
        currentPage = Long.valueOf(1);
        initView();
        initData();
        initCourse();
    }



    public void getHttpFilter(final String url) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int totalPages = 1;
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
                        hasNext = jsonObject2.getBoolean("hasNext");
                        totalPages = jsonObject2.getInt("totalPages");
                        //得到筛选的课程list
                        JSONArray JSONArrayCourse = jsonObject2.getJSONArray("courseList");
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
                            courseList.add(i, course);
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
        if (httpcode != 200) {
            Toast.makeText(course_filter.this, "ERROR", Toast.LENGTH_SHORT).show();
        }
        else for (int i = 0; i <courseList.size(); i++)btCourse[i].setText(courseList.get(i).getCourseName() + "\n" + courseList.get(i).getDegree() + " . " +
                courseList.get(i).getDuration() + " . " +courseList.get(i).getHits() + "万人已参加");//展示课程
    }

    //初始化标签状态
    private void initCourse() {
        Intent intentAccept = getIntent();
        courseClass = (CourseClass) intentAccept.getSerializableExtra("bodypart");
        if (courseClass.getClassValue().equals("all")) {//"all"
            Long bodyClassId = null;//部位id值
            Long degreeClassId = null;//难度id值
            String url;//http请求的url
            url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/course/filterCourse?bodyPart=" + bodyClassId + "&&degree=" + degreeClassId + "&&currentPage=" + currentPage;
            getHttpFilter(url);//筛选课程并展示
        }
        else {//某个部位
            int i;
            for(i = 0 ; i < btBodypart.length ; i++) {
                if (btBodypart[i].getText().toString().equals(courseClass.getClassValue())) {
                    btBodypart[i].setBackgroundColor(Color.parseColor("#25C689"));
                    break;
                }
            }
            for(i = 0 ; i < btDegree.length ; i++){
                btDegree[i].setBackgroundColor(Color.parseColor("#25C689"));
            }
            String totalBodyClassId = null;//汇总的部位Id
            //String totalDegreeClassId = null;//汇总的难度id
            Long bodyClassId = null;//部位id值
            Long degreeClassId = null;//难度id值
            String url;//http请求的url
            totalBodyClassId = getBodyClassId(bodyClassId);//得到身体部位标签的id
            //totalDegreeClassId = getDegreeId(degreeClassId);//得到难度标签的Id
            url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/course/filterCourse?bodyPart=" + totalBodyClassId + "&&degree=" + degreeClassId + "&&currentPage=1";
            getHttpFilter(url);//筛选课程并展示
        }
    }

    private String getDegreeId(Long degreeClassId){
        ColorDrawable colorDrawable;
        int color;
        String totalDegreeClassId = null;
        int i;
        for(i = 0 ; i < btDegree.length ; i++) {
            background=btDegree[i].getBackground();
            colorDrawable = (ColorDrawable) background;
            color=colorDrawable.getColor();
            if(color == -14301559){
                degreeClassId = degree.get(i).getCourseClassId();
                if(totalDegreeClassId != null) totalDegreeClassId = totalDegreeClassId + ";" + degreeClassId.toString().trim();
                else totalDegreeClassId = degreeClassId.toString().trim();
            }
        }
        return totalDegreeClassId;
    }

    private String getBodyClassId(Long bodyClassId){
        ColorDrawable colorDrawable;
        int color;
        String totalBodyClassId = null;
        int i;
        for(i = 0 ; i < btBodypart.length ; i++) {
            background=btBodypart[i].getBackground();
            colorDrawable = (ColorDrawable) background;
            color=colorDrawable.getColor();
            if(color == -14301559){
                bodyClassId = bodyPart.get(i).getCourseClassId();
                if(totalBodyClassId != null) totalBodyClassId = totalBodyClassId + ";" + bodyClassId.toString().trim();
                else totalBodyClassId = bodyClassId.toString().trim();
            }
        }
        return totalBodyClassId;
    }

    private void initData() {
        //展示标签
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
        String url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/course/getFilter";
        String responseData = null;
        try {
            responseData = HttpUtils.connectHttpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = null;
        try {
            if(responseData != null)jsonObject1 = new JSONObject(responseData);
            httpcode = jsonObject1.getInt("code");
            if (httpcode == 200) {
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
                for (int i = 0; i < JSONArrayDegree.length(); i++) {
                    JSONObject jsonObject4 = JSONArrayDegree.getJSONObject(i);
                    CourseClass courseClass = new CourseClass();
                    courseClass.setCourseClassId(jsonObject4.getLong("courseClassId"));
                    courseClass.setClassName(jsonObject4.getString("className"));
                    courseClass.setClassValue(jsonObject4.getString("classValue"));
                    degree.add(i,courseClass);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bodyPart.size(); i++) btBodypart[i].setText(bodyPart.get(i).getClassValue());
        for (int i = 0; i < degree.size(); i++) btDegree[i].setText(degree.get(i).getClassValue());
    }
        });
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(httpcode!=200)Toast.makeText(course_filter.this,"ERROR", Toast.LENGTH_SHORT).show();
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
        ibback.setOnClickListener(this);
        btReset.setOnClickListener(this);
        btSure.setOnClickListener(this);
        for (int i = 0; i < btCourse.length; i++) btCourse[i].setOnClickListener(this);
        for (int i = 0; i < btDegree.length; i++) {
            //btDegree[i].setBackgroundColor(Color.parseColor("#F5F5F5"));
            btDegree[i].setOnClickListener(this);
        }
        for (int i = 0; i < btBodypart.length; i++) {
            //btBodypart[i].setBackgroundColor(Color.parseColor("#F5F5F5"));
            btBodypart[i].setOnClickListener(this);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        Intent intent = null;
        ColorDrawable colorDrawable;
        int color;
        switch (view.getId()) {
            case R.id.go_back_button:
                finish();
                break;
            case R.id.search_button1:
                intent = new Intent(this, search_course.class);
                startActivity(intent);
                break;
            case R.id.sure://清空课程列表，如果BUTTON选中，传值，筛选选中的标签的课程，展示课程
                courseList.clear();
                currentPage = Long.valueOf(1);
                String totalBodyClassId = null;//汇总的部位Id
                String totalDegreeClassId = null;//汇总的难度id
                Long bodyClassId = null;//部位id值
                Long degreeClassId = null;//难度id值
                String url;//http请求的url
                totalBodyClassId = getBodyClassId(bodyClassId);//得到身体部位标签的id
                totalDegreeClassId = getDegreeId(degreeClassId);//得到难度标签的Id
                url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/course/filterCourse?bodyPart=" + totalBodyClassId + "&&degree=" + totalDegreeClassId + "&&currentPage=1";
                getHttpFilter(url);//筛选课程并展示
                break;
            case R.id.reset://标签全部置灰，取消选中
                int i;
                for(i = 0 ; i < btBodypart.length ; i++) btBodypart[i].setBackgroundColor(Color.parseColor("#F5F5F5"));//.performClick();
                for(i = 0 ; i < btDegree.length ; i++)btDegree[i].setBackgroundColor(Color.parseColor("#F5F5F5"));//.performClick();
                break;
            //部位标签
            case R.id.button1:
                colorDrawable=(ColorDrawable)btBodypart[0].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btBodypart[0].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btBodypart[0].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.button2:
                colorDrawable=(ColorDrawable)btBodypart[1].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btBodypart[1].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btBodypart[1].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.button3:
                colorDrawable=(ColorDrawable)btBodypart[2].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btBodypart[2].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btBodypart[2].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.button4:
                colorDrawable=(ColorDrawable)btBodypart[3].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btBodypart[3].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btBodypart[3].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.bt1:
                colorDrawable=(ColorDrawable)btBodypart[4].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btBodypart[4].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btBodypart[4].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.bt2:
                colorDrawable=(ColorDrawable)btBodypart[5].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btBodypart[5].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btBodypart[5].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.bt3:
                colorDrawable=(ColorDrawable)btBodypart[6].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btBodypart[6].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btBodypart[6].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.bt4:
                colorDrawable=(ColorDrawable)btBodypart[7].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btBodypart[7].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btBodypart[7].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.bt5:
                colorDrawable=(ColorDrawable)btBodypart[8].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btBodypart[8].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btBodypart[8].setBackgroundColor(Color.parseColor("#25C689"));
                break;

            //难度标签
            case R.id.btdifficult1:
                colorDrawable=(ColorDrawable)btDegree[0].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btDegree[0].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btDegree[0].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.btdifficult2:
                colorDrawable=(ColorDrawable)btDegree[1].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btDegree[1].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btDegree[1].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.btdifficult3:
                colorDrawable=(ColorDrawable)btDegree[2].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btDegree[2].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btDegree[2].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.btdifficult4:
                colorDrawable=(ColorDrawable)btDegree[3].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btDegree[3].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btDegree[3].setBackgroundColor(Color.parseColor("#25C689"));
                break;
            case R.id.btdifficult5:
                colorDrawable=(ColorDrawable)btDegree[4].getBackground();
                color=colorDrawable.getColor();
                if(color == -14301559)btDegree[4].setBackgroundColor(Color.parseColor("#F5F5F5"));
                else if(color == -657931) btDegree[4].setBackgroundColor(Color.parseColor("#25C689"));
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
                intent = new Intent(this, course_main.class);
                intent.putExtra("course", courseList.get(5).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous7:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course", courseList.get(6).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous8:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course", courseList.get(7).getCourseId());
                startActivity(intent);
                break;
            case R.id.famous9:
                intent = new Intent(this, course_main.class);
                intent.putExtra("course", courseList.get(8).getCourseId());
                startActivity(intent);
                break;
        }
    }
}
