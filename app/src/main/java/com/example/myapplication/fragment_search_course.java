package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.myapplication.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class fragment_search_course extends Fragment {

    private List<Course> data;
    private List<List> dataSet;
    private int TOTAL_PAGES = 1;

    QuickAdapter quickAdapter;
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

        quickAdapter = new QuickAdapter(R.layout.item_course, data);

        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                Log.d("Adapter","Click"+position);
            }
        });
        quickAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            int mCurrentCunter = 0;

            @Override
            public void onLoadMore() {
                if(mCurrentCunter>=TOTAL_PAGES){
                    quickAdapter.getLoadMoreModule().loadMoreEnd();
                }else{
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            mCurrentCunter++;
                            configLoadMoreData();
                        }
                    },3000);

                }

            }
        });

        recyclerView.setAdapter(quickAdapter);

    }

    private void initData(){

        /**
         * TestData
         */
        data = new ArrayList<>();
        dataSet = new ArrayList<>();

        Course course;
        for (int i = 0; i < 5; i++) {
            course = new Course();
            course.setCourseName("课程名称");
            course.setCourseIntro("课程介绍");
            data.add(course);
        }
        dataSet.add(data);
        dataSet.add(data);

    }

    private void configLoadMoreData() {
        dataSet.add(data);
        quickAdapter.addData(dataSet.get(1));
        quickAdapter.getLoadMoreModule().loadMoreEnd();
    }

}
