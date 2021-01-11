package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.listener.OnUpFetchListener;
import com.example.myapplication.entity.Course;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fragment_search_course extends Fragment {

    //private List<List> courseSet = new  ArrayList<>();
    private int TOTAL_PAGES;

    QuickAdapter quickAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    private int httpcode;
    private String keyWord;//搜索的关键词
    private Boolean hasNext;
    private int currentPage; //要分页查询的页面
    private List<Course> courseList = new ArrayList();

    private String url;//http请求的url

    private AlertDialog.Builder builder;
    private String errorMsg;

    private void showErrorAlert(String Msg){
        builder = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.cancel).setTitle("Error")
                .setMessage(Msg).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_course, container, false);
        currentPage = 1;
        Bundle bundle = getArguments();
        keyWord = bundle.getString("keyWord");
        //courseSet.add(courseList);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_course_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = view.findViewById(R.id.fragment_course_swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//下拉刷新监听
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });


        quickAdapter = new QuickAdapter(R.layout.item_course, courseList);

        configLoadMoreData();

        quickAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            //int mCurrentCunter = 0;

            @Override
            public void onLoadMore() {
                if (currentPage > TOTAL_PAGES) {
                    quickAdapter.getLoadMoreModule().loadMoreEnd();
                } else {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            configLoadMoreData();
                        }
                    }, 2000);

                }

            }
        });

        //具体课程的监听
        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                //Log.d("Adapter","Click");
                Intent intent;
                intent = new Intent(getActivity(), course_main.class);
/*                for(int i = 0 ; i < dataSet.size() ; i++){
                    courseList = dataSet.get(i);
                    if(courseList.size()<=position)position = position - courseList.size();
                    else break;
                }*/
                intent.putExtra("course", courseList.get(position).getCourseId());
                startActivity(intent);
            }
        });

        quickAdapter.setEmptyView(R.layout.empty_view);//设置空布局，没有数据时显示
        quickAdapter.setAnimationEnable(true);
        recyclerView.setAdapter(quickAdapter);



    }

    private void getHttpSearch(final String url) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String responseData = null;
                /*
                try {
                    responseData = HttpUtils.connectHttpGet(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                 */
                JSONObject jsonObject1 = null;
                try {
                    responseData = HttpUtils.connectHttpGet(url);
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
                            //course.setCourseUrl(jsonObject.getString("courseUrl"));
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
                    errorMsg = e.getMessage();
                    e.printStackTrace();
                }catch (IOException e) {
                    errorMsg = e.getMessage();
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            errorMsg = e.getMessage();
            e.printStackTrace();
        }
        if (httpcode != 200) {
            showErrorAlert("fragment_search_course: "+errorMsg);
        }

    }

    private void configLoadMoreData() {
        url = "http://159.75.2.94:8080/api/course/searchCourse?keyword=" + keyWord + "&currentPage=" + currentPage;//(e.g.搜索"腹肌")
        getHttpSearch(url);
        quickAdapter.notifyDataSetChanged();
        currentPage++;
        quickAdapter.getLoadMoreModule().loadMoreComplete();
    }

    private void refreshData() {
        currentPage=1;
        url = "http://159.75.2.94:8080/api/course/searchCourse?keyword=" + keyWord + "&currentPage=" + currentPage++;
        courseList = new ArrayList<>();
        getHttpSearch(url);
        quickAdapter.setNewData(courseList);
    }

}
