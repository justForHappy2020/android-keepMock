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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Share;
import com.example.myapplication.entity.User;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fragment_search_all extends Fragment {


    private List<MultipleItem> shareList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();
    private List<MultipleItem> miniCourseList = new ArrayList<>();

    private int TOTAL_PAGES;
    private int currentPage; //要分页查询的页面
    private int httpcode;
    private Boolean hasNext;

    String keyWord;//传入用户在搜索界面输入的内容

    RecyclerView rootRecyclerView;
    RecyclerView courseRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    MultipleItemQuickAdapter rootAdapter;
    MultipleItemQuickAdapter miniCourseAdapter;

    private String token;//后期本地获取

    private String url;//http请求动态的url

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
        View view = inflater.inflate(R.layout.fragment_search_all, container, false);

        Bundle bundle = getArguments();
        keyWord = bundle.getString("keyWord");
        currentPage = 1;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view){
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_course_mini,null);

        rootRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_all_recyclerView);
        courseRecyclerView = (RecyclerView) headerView.findViewById(R.id.fragment_course_recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.fragment_all_swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//下拉刷新监听
                rootRecyclerView.post(new Runnable() {//后续需要增加courseRecyclerview.post
                    @Override
                    public void run() {
                        refreshData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        rootAdapter = new MultipleItemQuickAdapter(shareList);//shareSet.get(0)

        miniCourseAdapter = new MultipleItemQuickAdapter(miniCourseList);

        miniCourseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), course_main.class);
                intent.putExtra("course", courseList.get(position).getCourseId());
                startActivity(intent);
            }
        });

        rootAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            //int mCurrentCunter = 0;

            @Override
            public void onLoadMore() {
                if (currentPage > TOTAL_PAGES) {
                    rootAdapter.getLoadMoreModule().loadMoreEnd();
                } else {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            configLoadMoreShareData();
                        }
                    }, 1000);

                }

            }
        });

        //设置recyclerView的样式
        rootRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        rootAdapter.addHeaderView(headerView);//待增添逻辑：如果无课程则不添加“课程”TextView和加载更多按钮（小于等于三条也不显示）
        rootRecyclerView.setAdapter(rootAdapter);

        courseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseRecyclerView.setAdapter(miniCourseAdapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        rootRecyclerView.addItemDecoration(decoration);
    }

    private void initData(){

        rootRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                getHttpSearchCourse("http://159.75.2.94:8080/api/course/searchCourse?keyword=" + keyWord + "&currentPage=" + 1);

                if(!courseList.isEmpty()){
                    miniCourseAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.titlebar_search_all_course,null));
                    //miniCourseList.add(new MultipleItem(MultipleItem.TEXTONLY,"课程"));

                    if(courseList.size()>3){
                        for(int n=0;n<3;n++){
                            miniCourseList.add(new MultipleItem(MultipleItem.MINICOURSE,courseList.get(n)));
                        }
                        View checkMoreButton=LayoutInflater.from(getActivity()).inflate(R.layout.item_search_all_checkmore_button,null);
                        checkMoreButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getContext(), search_result.class);
                                intent.putExtra("from", 1);
                                intent.putExtra("keyWord",keyWord);
                                startActivity(intent);
                            }
                        });
                        miniCourseAdapter.addFooterView(checkMoreButton);
                        //miniCourseList.add(new MultipleItem(MultipleItem.BUTTON,"加载更多"));
                    }else{
                        for(int n=0;n<courseList.size();n++){
                            miniCourseList.add(new MultipleItem(MultipleItem.MINICOURSE,courseList.get(n)));
                        }
                    }
                }
                miniCourseAdapter.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.titlebar_search_all_share,null));
                //miniCourseList.add(new MultipleItem(MultipleItem.TEXTONLY,"动态"));
                miniCourseAdapter.notifyDataSetChanged();

            }
        });


        configLoadMoreShareData();
    }

    private void getHttpSearchShare(final String url) {

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
                        JSONArray JSONArrayShare = jsonObject2.getJSONArray("shareList");
                        for (int i = 0; i < JSONArrayShare.length(); i++) {
                            JSONObject jsonObject = JSONArrayShare.getJSONObject(i);
                            //相应的内容
                            Share share = new Share();
                            User user = new User();

                            user.setNickname(jsonObject.getString("nickname"));
                            user.setHeadPortraitUrl(jsonObject.getString("headPortraitUrl"));

                            share.setContents(jsonObject.getString("content"));
                            share.setImgUrls(jsonObject.getString("imgUrls"));
                            share.setLikeNumbers(jsonObject.getString("likeNumbers"));
                            share.setCommentsNumbers(jsonObject.getString("commentNumbers"));
                            share.setCreateTime(jsonObject.getString("createTime"));

                            share.setUser(user);

                            shareList.add(new MultipleItem(MultipleItem.MASONRYPOST,share));

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
            thread.join(8000);
        } catch (InterruptedException e) {
            errorMsg=e.getMessage();
            e.printStackTrace();
        }
        if (httpcode != 200) {
            rootAdapter.getLoadMoreModule().loadMoreFail();
            showErrorAlert("fragment_search_all: "+errorMsg);
        }
    }

    private void getHttpSearchCourse(final String url) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String responseData = null;
                JSONObject jsonObject1 = null;
                try {
                    responseData = HttpUtils.connectHttpGet(url);
                    jsonObject1 = new JSONObject(responseData);
                    httpcode = jsonObject1.getInt("code");
                    if (httpcode == 200) {
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
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

    private void configLoadMoreShareData() {
        url = "http://159.75.2.94:8080/api/community/getHotShare?token=" + token + "&currentPage=" + currentPage;//后期改为搜索动态接口
        getHttpSearchShare(url);
        rootAdapter.notifyDataSetChanged();
        currentPage++;
        rootAdapter.getLoadMoreModule().loadMoreComplete();
    }
    private void refreshData() {
        currentPage = 1;
        url = "http://159.75.2.94:8080/api/community/getHotShare?token=" + token + "&currentPage=" + currentPage++;
        shareList=new ArrayList<>();
        getHttpSearchShare(url);
        rootAdapter.setNewData(shareList);
    }

}
