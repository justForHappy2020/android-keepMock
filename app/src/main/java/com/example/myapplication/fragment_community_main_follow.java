package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
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


public class fragment_community_main_follow extends Fragment implements LoadMoreModule {
    private View.OnClickListener onClickListener;
    private List<List> dataSet = new  ArrayList<>();
    private int TOTAL_PAGES;
    private int currentPage; //要分页查询的页面
    private List<MultipleItem> shareList = new ArrayList();
    private List<MultipleItem> data01 = new ArrayList();
    private int httpcode;
    private Boolean hasNext;
    MultipleItemQuickAdapter quickAdapter;
    RecyclerView recyclerView;

    private String keyUrl;//搜索的关键词
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_main_content, container, false);
        currentPage = 1;
        Bundle bundle = getArguments();
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.community_main_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent;
                intent = new Intent(getActivity(), activity_sharedetail.class);
                startActivity(intent);
            }
        });
        quickAdapter.addChildClickViewIds(R.id.share_follow,R.id.share_users_head,R.id.users_id,R.id.postlike,R.id.praises,R.id.postcomment,R.id.comments);
        //具体课程的监听
        quickAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                int count_like = 0;
                int count_follow = 0;
                switch (view.getId()){
                    case R.id.share_users_head:
                        //position.like.click;
                        clickHead(position);
                        break;
                    case R.id.share_follow:
                        count_follow = count_follow + 1;
                        clickFollow(position,count_follow);
                        break;
                    case R.id.postlike:
                        count_like = count_like + 1;
                        clickLike(view,position,count_like);
                        break;
                    case R.id.postcomment:
                      //  clickComment(position);
                        Intent intent;
                        intent = new Intent(getActivity(), activity_sharedetail.class);
                        startActivity(intent);
                        break;
                }

            }
        });
        recyclerView.setAdapter(quickAdapter);
    }
            public void clickHead(int position){
                Intent intent;
                intent = new Intent(getActivity(), community2.class);
                startActivity(intent);
            }
            public void clickFollow(int position,int i){
                Boolean isClicked;
                if (i%2 == 1) isClicked = true;
                else isClicked = false;
                if(isClicked == false){
                    isClicked=true;
                    Button item_follow=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.share_follow);
                    item_follow.setText("关注");
                    item_follow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                else {
                    isClicked=false;
                    Button item_follow=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.share_follow);
                    item_follow.setText("已关注");
                    item_follow.setBackgroundColor(Color.parseColor("#25C689"));
                }
            }
            public void clickLike(View view,int position,int i){
                Boolean isClicked;
                if (i%2 == 1) isClicked = true;
                else isClicked = false;//从接口获取，List.get(i).getLiked();
                if(isClicked == false){
                    isClicked=true;
                    ImageView item_like=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.postlike);
                    TextView item_praises=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.praises);
                    item_praises.setText(String.valueOf(Integer.parseInt(item_praises.getText().toString())+1));
                    item_like.setImageResource(R.drawable.like_click);
                }
                else {
                    isClicked=false;
                    ImageView item_like=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.postlike);
                    TextView item_praises=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.praises);
                    item_praises.setText(String.valueOf(Integer.parseInt(item_praises.getText().toString())-1));
                    item_like.setImageResource(R.drawable.like);
                }

            }
            public void clickComment(int position){
                Intent intent;
                intent = new Intent(getActivity(), community3.class);
                startActivity(intent);
            }

    public void onCommunityClick(View view){
        Intent intent = null;
        switch (view.getId()) {
            case R.id.community_main_follow:
                intent = new Intent(getActivity(),search_user.class);//点击搜索用户按钮跳转到搜索用户界面
                startActivity(intent);
                break;
            case R.id.community_main_search:
                intent = new Intent(getActivity(),search_share.class);//点击搜索动态按钮跳转到搜索动态界面
                startActivity(intent);
                break;
            case R.id.float_button:
                intent = new Intent(getActivity(),community4.class);//点击浮动按钮跳转到发表动态界面
                startActivity(intent);
                break;
        }}
    private void initData() {
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
                            share.setLikeNumbers(jsonObject.getString("likeNumbers"));
                            share.setCommentsNumbers(jsonObject.getString("commentNumbers"));
                            share.setCreateTime(jsonObject.getString("createTime"));

                            share.setUser(user);
                            shareList.add(new MultipleItem(MultipleItem.SHARE,share));
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

    private void configLoadMoreData() {
        String url;//http请求的url
        url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/community/getFriendShare";
        getHttpSearch(url);
        dataSet.add(shareList);
        quickAdapter.addData(dataSet.get(currentPage-1));
        currentPage++;
        quickAdapter.getLoadMoreModule().loadMoreEnd();
    }
}
