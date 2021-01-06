package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
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


    private List<MultipleItem> postList= new ArrayList<>();
    private List<MultipleItem> multItemList= new ArrayList<>();

    private List<List> shareSet= new ArrayList<>();

    private int TOTAL_PAGES;
    private int currentPage; //要分页查询的页面
    private int httpcode;
    private Boolean hasNext;

    String searchContent;//传入用户在搜索界面输入的内容

    RecyclerView postRecyclerView;
    RecyclerView courseRecyclerView;

    MultipleItemQuickAdapter myAdapter;
    MultipleItemQuickAdapter miniCourseAdapter;

    private String token;//后期本地获取

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_all, container, false);

        Bundle bundle = getArguments();
        searchContent = bundle.getString("searchContent");
        currentPage = 1;

        shareSet.add(postList);

        initView(view);
        initData();

        return view;
    }

    private void initView(View view){
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_course_mini,null);

        postRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_all_recyclerView);
        courseRecyclerView = (RecyclerView) headerView.findViewById(R.id.fragment_course_recyclerView);

        myAdapter = new MultipleItemQuickAdapter(shareSet.get(0));

        miniCourseAdapter = new MultipleItemQuickAdapter(multItemList);

        miniCourseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int position) {
                Toast.makeText(getActivity(),"OnClick Position: " + position,Toast.LENGTH_SHORT).show();
            }
        });

        myAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            //int mCurrentCunter = 0;

            @Override
            public void onLoadMore() {
                if (currentPage > TOTAL_PAGES) {
                    myAdapter.getLoadMoreModule().loadMoreEnd();
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

        //设置recyclerView的样式
        postRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        myAdapter.addHeaderView(headerView);//待增添逻辑：如果无课程则不添加“课程”TextView和加载更多按钮（小于等于三条也不显示）
        postRecyclerView.setAdapter(myAdapter);

        courseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseRecyclerView.setAdapter(miniCourseAdapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        postRecyclerView.addItemDecoration(decoration);
    }

    private void initData(){

        Course course1 = new Course();
        course1.setCourseName("腹肌");
        course1.setCourseIntro("K1零基础  . 3002.5万人参加");

        multItemList.add(new MultipleItem(MultipleItem.TEXTONLY,"课程"));

        multItemList.add(new MultipleItem(MultipleItem.MINICOURSE,course1));
        multItemList.add(new MultipleItem(MultipleItem.MINICOURSE,course1));
        multItemList.add(new MultipleItem(MultipleItem.MINICOURSE,course1));

        multItemList.add(new MultipleItem(MultipleItem.BUTTON,"加载更多"));

        multItemList.add(new MultipleItem(MultipleItem.TEXTONLY,"动态"));

        configLoadMoreData();
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

                            postList.add(new MultipleItem(MultipleItem.MASONRYPOST,share));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (IOException e) {
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

    private void configLoadMoreData() {
        String url;//http请求的url
        url = "http://159.75.2.94:8080/api/community/getHotShare?token=" + token + "&currentPage=" + currentPage;//后期改为搜索动态接口

        getHttpSearch(url);

        shareSet.add(postList);
        //myAdapter.addData(sharePages.get(currentPage-1));
        currentPage++;
        myAdapter.getLoadMoreModule().loadMoreEnd();
    }
}
