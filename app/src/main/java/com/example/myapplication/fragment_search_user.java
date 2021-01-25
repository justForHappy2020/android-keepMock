package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.User;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fragment_search_user extends Fragment implements LoadMoreModule {

    private int TOTAL_PAGES;
    private int httpcode;
    private Boolean hasNext;
    private int currentPage; //要分页查询的页面
    private String keyWord;//搜索的关键词
    private String token = "123";//后期本地获取

    private List<List> dataSet = new  ArrayList<>();
    private List<MultipleItem> userList = new ArrayList();

    MultipleItemQuickAdapter quickAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

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
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        currentPage = 1;
        Bundle bundle = getArguments();
        keyWord = bundle.getString("keyWord");

        dataSet.add(userList);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        recyclerView= (RecyclerView) view.findViewById(R.id.fragment_user_recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.fragment_user_swipe_refresh);

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

        //设置recyclerView的样式
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        quickAdapter = new MultipleItemQuickAdapter(dataSet.get(0));


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
        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent;
                intent = new Intent(getActivity(), activity_community_homepage.class);
                startActivity(intent);
            }
        });
        quickAdapter.addChildClickViewIds(R.id.user_follow,R.id.user_head);
        quickAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                int count_like = 0;
                int count_follow = 0;
                switch (view.getId()){
                    case R.id.user_follow:
                        //position.like.click;
                        count_follow = count_follow + 1;
                        clickFollow(position,count_follow);
                        break;
                    case R.id.user_head:
                        Intent intent;
                        intent = new Intent(getActivity(), activity_community_homepage.class);
                        startActivity(intent);
                        break;
                }

            }
        });

        quickAdapter.setEmptyView(R.layout.empty_view);//设置空布局，没有数据时显示
        quickAdapter.setAnimationEnable(true);

        recyclerView.setAdapter(quickAdapter);

        //设置item之间的间隔
        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    public void clickFollow(int position,int i){
        Boolean isClicked;
        if (i%2 == 1) isClicked = true;
        else isClicked = false;
        if(isClicked == false){
            isClicked=true;
            Button item_follow=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.user_follow);
            item_follow.setText("关注");
            item_follow.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
            isClicked=false;
            Button item_follow=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.user_follow);
            item_follow.setText("已关注");
            item_follow.setBackgroundColor(Color.parseColor("#25C689"));
        }
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

                    Log.d("USER_responseData ",responseData);

                    jsonObject1 = new JSONObject(responseData);
                    httpcode = jsonObject1.getInt("code");
                    if (httpcode == 200) {
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                        hasNext = jsonObject2.getBoolean("hasNext");
                        TOTAL_PAGES = jsonObject2.getInt("totalPages");
                        //得到筛选的课程list
                        JSONArray JSONArrayUser = jsonObject2.getJSONArray("userList");
                        for (int i = 0; i < JSONArrayUser.length(); i++) {
                            JSONObject jsonObject = JSONArrayUser.getJSONObject(i);
                            //相应的内容
                            User user = new User();
                            user.setUserId(jsonObject.getLong("userId"));
                            user.setNickname(jsonObject.getString("nickname"));
                            user.setHeadPortraitUrl(jsonObject.getString("headPortraitUrl"));
                            user.setGender(jsonObject.getString("gender"));
                            user.setIntro(jsonObject.getString("intro"));
                            user.setCreateTime(jsonObject.getString("createTime"));
                            userList.add(new MultipleItem(MultipleItem.USER,user));
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
            thread.join(3000);
        } catch (InterruptedException e) {
            errorMsg = e.getMessage();
            e.printStackTrace();
        }
        if (httpcode != 200) {
            showErrorAlert("fragment_search_user: "+errorMsg);
        }

/*        else for (int i = 0; i <courseList.size(); i++)btCourse[i].setText(courseList.get(i).getCourseName() + "\n" + courseList.get(i).getDegree() + " . " +
                courseList.get(i).getDuration() + " . " +courseList.get(i).getHits() + "万人已参加");//展示课程*/
    }

    private void configLoadMoreData() {
        url = "http://159.75.2.94:8080/api/community/searchFriend?keyword=" + keyWord + "&token=" + token + "&currentPage=" + currentPage;

        getHttpSearch(url);
        dataSet.add(userList);
        //quickAdapter.addData(dataSet.get(currentPage-1));
        currentPage++;
        quickAdapter.getLoadMoreModule().loadMoreComplete();
    }

    private void refreshData() {
        currentPage = 1;
        url = "http://159.75.2.94:8080/api/community/searchFriend?keyword=" + keyWord + "&token=" + token + "&currentPage=" + currentPage++;
        userList=new ArrayList<>();
        getHttpSearch(url);
        quickAdapter.setNewData(userList);
    }
}


