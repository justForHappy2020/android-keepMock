package com.example.myapplication;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
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
    private List<List> courseListSet = new ArrayList<>();
    private CourseClass courseClass;//记录传入的标签

    QuickAdapter quickAdapter;
    RecyclerView recyclerView;
    View headerView;

    private int httpcode;
    private Drawable background;
    private Boolean hasNext = false;
    private Long currentPage;
    private int TOTAL_PAGES = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_filter);
        currentPage = Long.valueOf(1);
        initView();
        initData();
        initCourse();
    }

    private void configLoadMoreData() {
        if(hasNext){
            /**
             * 加载更多函数，需要完善加载下一页的HTTP请求
             */
            String totalBodyClassId = null;//汇总的部位Id
            String totalDegreeClassId = null;//汇总的难度id
            String url;//http请求的url
            totalBodyClassId = getBodyClassId();//得到身体部位标签的id
            totalDegreeClassId = getDegreeId();//得到难度标签的Id
            if(totalBodyClassId == null && totalDegreeClassId == null)url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart=" + "&&degree=" + "&&currentPage=" + currentPage;
            else if(totalBodyClassId == null)url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart="  + "&&degree=" + totalDegreeClassId + "&&currentPage=" + currentPage;
            else if(totalDegreeClassId == null)url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart=" + totalBodyClassId + "&&degree=" + "&&currentPage=" + currentPage;
            else url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart=" + totalBodyClassId + "&&degree=" + totalDegreeClassId + "&&currentPage=" + currentPage;
            getHttpFilter(url);//筛选课程并展示

            //下面这句注释掉的不要加上去。。不然item显示重复
            //quickAdapter.addData(courseListSet.get(currentPage.intValue()));//用不了Long类型数据!!!!!!!!!!这个quickAdapter连数组的下一个元素都检查也没有发生改变！太恐怖了！
        }
        quickAdapter.getLoadMoreModule().loadMoreEnd();
    }

    private void getHttpFilter(final String url) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

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
                        TOTAL_PAGES = jsonObject2.getInt("totalPages");
                        //得到筛选的课程list
                        JSONArray JSONArrayCourse = jsonObject2.getJSONArray("courseList");
                        for (int i = 0; i < JSONArrayCourse.length(); i++) {
                            JSONObject jsonObject = JSONArrayCourse.getJSONObject(i);
                            //相应的内容
                            Course course = new Course();
                            course.setCourseId(jsonObject.getLong("courseId"));
                            course.setCourseName(jsonObject.getString("courseName"));
                            course.setBackgroundUrl(jsonObject.getString("backgroundUrl"));
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
        else {

            courseListSet.add(courseList);
            Log.d("courseListSet.size: ",Integer.toString(courseListSet.size()));

            /**
             * quickAdapter会自动检测到courseList的变化并进行更新!!!!!!!
             */
            //quickAdapter.addData(courseList);
        }
    }

    //初始化标签状态
    private void initCourse() {
        Intent intentAccept = getIntent();
        courseClass = (CourseClass) intentAccept.getSerializableExtra("bodypart");
        if (courseClass.getClassValue().equals("all")) {//"all"
            String url;//http请求的url
            url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart="  + "&&degree="  + "&&currentPage=" + currentPage;
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
            String url;//http请求的url
            totalBodyClassId = getBodyClassId();//得到身体部位标签的id
            //totalDegreeClassId = getDegreeId();//得到难度标签的Id
            if(totalBodyClassId == null)url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart=" + "&&degree="  + "&&currentPage=" + currentPage;
            else url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart=" + totalBodyClassId + "&&degree="  + "&&currentPage=" + currentPage;
            getHttpFilter(url);//筛选课程并展示
        }
    }

    private String getDegreeId(){
        ColorDrawable colorDrawable;
        int color;
        String totalDegreeClassId = null;
        Long degreeClassId = null;
        int i;
        for(i = 0 ; i < degree.size() ; i++) {
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

    private String getBodyClassId(){
        ColorDrawable colorDrawable;
        int color;
        String totalBodyClassId = null;
        Long bodyClassId = null;
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
        String url = "http://159.75.2.94:8080/api/course/getFilter";
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
        recyclerView = findViewById(R.id.course_fliter_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(course_filter.this));

        headerView = LayoutInflater.from(course_filter.this).inflate(R.layout.fragment_course_filter_header,null);

        courseListSet.add(courseList);//空值占位

        quickAdapter = new QuickAdapter(R.layout.item_course, courseListSet.get(0));
        quickAdapter.addHeaderView(headerView);

        //RecyclerView中的物体点击事件监听器
        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                Intent intent = new Intent(course_filter.this, course_main.class);
                intent.putExtra("course", courseList.get(0).getCourseId());
                startActivity(intent);
            }
        });

        //RecyclerView的上拉加载监听器
        quickAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if(currentPage>=TOTAL_PAGES){
                    quickAdapter.getLoadMoreModule().loadMoreEnd();
                }else{
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            currentPage++;
                            configLoadMoreData();
                        }
                    },1000);

                }

            }
        });
        recyclerView.setAdapter(quickAdapter);

        ibback = headerView.findViewById(R.id.go_back_button);
        ibSearch = headerView.findViewById(R.id.search_button1);
        btReset = headerView.findViewById(R.id.reset);
        btSure = headerView.findViewById(R.id.sure);

        btBodypart[0] = headerView.findViewById(R.id.button1);
        btBodypart[1] = headerView.findViewById(R.id.button2);
        btBodypart[2] = headerView.findViewById(R.id.button3);
        btBodypart[3] = headerView.findViewById(R.id.button4);
        btBodypart[4] = headerView.findViewById(R.id.bt1);
        btBodypart[5] = headerView.findViewById(R.id.bt2);
        btBodypart[6] = headerView.findViewById(R.id.bt3);
        btBodypart[7] = headerView.findViewById(R.id.bt4);
        btBodypart[8] = headerView.findViewById(R.id.bt5);

        btDegree[0] = headerView.findViewById(R.id.btdifficult1);
        btDegree[1] = headerView.findViewById(R.id.btdifficult2);
        btDegree[2] = headerView.findViewById(R.id.btdifficult3);
        btDegree[3] = headerView.findViewById(R.id.btdifficult4);
        btDegree[4] = headerView.findViewById(R.id.btdifficult5);

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
                courseListSet.clear();
                currentPage = Long.valueOf(1);
                String totalBodyClassId = null;//汇总的部位Id
                String totalDegreeClassId = null;//汇总的难度id
                String url;//http请求的url
                totalBodyClassId = getBodyClassId();//得到身体部位标签的id
                totalDegreeClassId = getDegreeId();//得到难度标签的Id
                if(totalBodyClassId == null && totalDegreeClassId == null)url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart=" + "&&degree=" + "&&currentPage=" + currentPage;
                else if(totalBodyClassId == null)url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart="  + "&&degree=" + totalDegreeClassId + "&&currentPage=" + currentPage;
                else if(totalDegreeClassId == null)url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart=" + totalBodyClassId + "&&degree=" + "&&currentPage=" + currentPage;
                else url = "http://159.75.2.94:8080/api/course/filterCourse?bodyPart=" + totalBodyClassId + "&&degree=" + totalDegreeClassId + "&&currentPage=" + currentPage;
                getHttpFilter(url);//筛选课程并展示
                quickAdapter.notifyDataSetChanged();//检测数据更新并刷新
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
        }
    }
}
