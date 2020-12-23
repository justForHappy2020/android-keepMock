package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
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

public class fragment_search_daily extends Fragment {


    private List<MultipleItem> shareList = new ArrayList<>();
    private List<List> sharePages = new ArrayList<>();
    
    private int currentPage; //要分页查询的页面

    private int TOTAL_PAGES;
    private int httpcode;
    private Boolean hasNext;
    private String keyWord;//搜索的关键词

    MultipleItemQuickAdapter quickAdapter;
    RecyclerView recyclerView;


    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_daily, container, false);

        currentPage = 1;
        Bundle bundle = getArguments();
        keyWord = bundle.getString("searchContent");

        initView(view);

        return view;
    }

    private void initView(View view){

        recyclerView= (RecyclerView) view.findViewById(R.id.fragment_daily_recyclerView);

        //设置recyclerView的样式
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //设置adapter
        quickAdapter = new MultipleItemQuickAdapter(shareList);

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
                    }, 1000);

                }

            }
        });
        //具体课程的监听
        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent;
                intent = new Intent(getActivity(), activity_sharedetail.class);
                startActivity(intent);
            }
        });
        quickAdapter.addChildClickViewIds(R.id.masonry_item_portrait_img);
        //具体课程的监听
        quickAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.masonry_item_portrait_img:
                        clickHead(position);
                        break;
                }

            }
        });

        recyclerView.setAdapter(quickAdapter);

        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);


    }

    public void clickHead(int position){
        Intent intent;
        intent = new Intent(getActivity(), community2.class);
        startActivity(intent);
    }


    private void getHttpSearch(final String url) {
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
                            share.setCommentsNumbers(jsonObject.getString("commentNumbers"));
                            share.setUser(user);
                            shareList.add(new MultipleItem(MultipleItem.MASONRYPOST,share));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join(3000);
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
        url = "http://159.75.2.94:8080/api/community/getHotShare?token=" + "123" + "&currentPage=" + currentPage;//后期改为搜索动态接口
        getHttpSearch(url);

        //sharePages.add(shareList);

        //dataSet.add(shareList);
        //quickAdapter.addData(dataSet.get(currentPage-1));
        currentPage++;
        quickAdapter.getLoadMoreModule().loadMoreEnd();
    }


}
