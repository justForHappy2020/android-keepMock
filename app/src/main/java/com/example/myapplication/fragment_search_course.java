package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragment_search_course extends Fragment {

    private List<Map<String,Object>> lists;
    private List<Course> datas;

    ListView listView;
    RecyclerView recyclerView;

    String searchContent;//传入用户在搜索界面输入的内容

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

        //testData
        String[] courseNames = {"腹肌撕裂者初级","哑铃手臂塑形","腹肌撕裂者进阶","腹肌撕裂者进阶","腹肌撕裂者进阶"};
        String[] courseHints = {"“明星也在练的腹肌课！”","“虐腹就是它了”","“虐腹就是它了”","“虐腹就是它了”","“虐腹就是它了”"};
        String[] courseTexts ={"K1零基础 . 13分钟 . 3002万人已参加","K1零基础 . 13分钟 . 3002万人已参加","K1零基础 . 13分钟 . 3002万人已参加","K1零基础 . 13分钟 . 3002万人已参加","K1零基础 . 13分钟 . 3002万人已参加"};
        int[] courseImgs = {R.drawable.course_background,R.drawable.course_background,R.drawable.course_background,R.drawable.course_background,R.drawable.course_background};

    }
}
