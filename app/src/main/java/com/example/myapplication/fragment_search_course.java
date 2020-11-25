package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entity.Course;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class fragment_search_course extends Fragment {

    private List<Map<String,Object>> lists;
    private List<Course> datas;

    ListView listView;
    RecyclerView recyclerView;

    String searchContent;//传入用户在搜索界面输入的内容

    private int httpcode;
    private Boolean hasNext;
    private List<Course> courseList = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_course, container, false);

        initData();
        initView(view);

        return view;
    }

    private void initView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_course_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        QuickAdapter quickAdapter = new QuickAdapter(R.layout.course_item,datas);

        recyclerView.setAdapter(quickAdapter);

    }



    private void initData(){

        /**
         * TestData
         */
        datas = new ArrayList<>();
        Course course;
        for (int i = 0; i < 5; i++) {
            course = new Course();
            course.setCourseName("课程名称");
            course.setCourseIntro("课程介绍");
            datas.add(course);
        }

    }

    private void getHttpSearch(final String url){
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
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
        }
/*        else for (int i = 0; i <courseList.size(); i++)btCourse[i].setText(courseList.get(i).getCourseName() + "\n" + courseList.get(i).getDegree() + " . " +
                courseList.get(i).getDuration() + " . " +courseList.get(i).getHits() + "万人已参加");//展示课程*/
    }
}
