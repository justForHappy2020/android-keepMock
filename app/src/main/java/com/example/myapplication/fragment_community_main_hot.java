package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.myapplication.entity.Share;
import com.example.myapplication.entity.User;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class fragment_community_main_hot extends Fragment implements LoadMoreModule {
    private MultipleItemQuickAdapter quickAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    //private List<List> shareSet = new  ArrayList<>();
    private List<MultipleItem> shareList = new ArrayList();

    private int TOTAL_PAGES;
    private int currentPage; //要分页查询的页面
    private int httpcode;
    private Boolean hasNext;
    private String token = "123";//后期本地获取
    String url;//http请求的url

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_main_content, container, false);
        currentPage = 1;
        //shareSet.add(shareList);
        Bundle bundle = getArguments();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initData();
        initView(view);
    }

    private void initView(final View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.community_main_recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.fragment_community_swipe_refresh);

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

                intent.putExtra("share",(Serializable) shareList.get(position).getShare());

                intent.putExtra("ShareId",shareList.get(position).getShare().getShareId());
                startActivity(intent);
            }
        });
        quickAdapter.addChildClickViewIds(R.id.share_follow,R.id.share_users_head,R.id.users_id,R.id.postlike,R.id.praises,R.id.postcomment,R.id.comments);
        quickAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            Boolean isClicked = null;
            Boolean isLiked = null;
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                if(isLiked == null) {
                    if (shareList.get(position).getShare().isLike())
                        isLiked = true;
                    else isLiked = false;
                }
                if(isClicked == null) {
                    if (shareList.get(position).getShare().getRelations() == 0 || shareList.get(position).getShare().getRelations() == 2)
                        isClicked = false;
                    else isClicked = true;
                }
                switch (view.getId()){
                    case R.id.share_users_head:
                        //position.like.click;
                        clickHead(position);
                        break;
                    case R.id.share_follow:
                        if(isClicked == true){
                            isClicked=false;
                            Button item_follow=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.share_follow);
                            item_follow.setText("关注");
                            item_follow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            //加一个关注的接口
                        }
                        else {
                            isClicked=true;
                            Button item_follow=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.share_follow);
                            item_follow.setText("已关注");
                            item_follow.setBackgroundColor(Color.parseColor("#25C689"));
                            //加一个取消关注的接口
                        }
                        break;
                    case R.id.praises:
                    case R.id.postlike:
                        if(isLiked == false){
                            isLiked=true;
                            ImageView item_like=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.postlike);
                            TextView item_praises=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.praises);
                            item_praises.setText(String.valueOf(Integer.parseInt(item_praises.getText().toString())+1));
                            item_like.setImageResource(R.drawable.like_click);
                            //加一个点赞接口
                        }
                        else {
                            isLiked=false;
                            ImageView item_like=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.postlike);
                            TextView item_praises=recyclerView.getLayoutManager().findViewByPosition(position).findViewById(R.id.praises);
                            item_praises.setText(String.valueOf(Integer.parseInt(item_praises.getText().toString())-1));
                            item_like.setImageResource(R.drawable.like);
                            //加一个取消点赞接口
                        }
                        break;
                }

            }
        });
        recyclerView.setAdapter(quickAdapter);
    }
    public void clickHead(int position){
        Intent intent;
        intent = new Intent(getActivity(), community2.class);
        intent.putExtra("token",token);  //后期通过Sp获取
        intent.putExtra("userId",shareList.get(position).getShare().getUser().getUserId());
        startActivity(intent);
    }
    public void clickLike(View view,int position,int i){//这里逻辑比较混乱
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
                intent = new Intent(getActivity(), activity_community_postNewShare.class);//点击浮动按钮跳转到发表动态界面
                startActivity(intent);
                break;
        }}

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
                            share.setLike(false);//share.setLike(jsonObject.getBoolean("like"));
                            share.setRelations(jsonObject.getInt("relations"));
                            share.setUser(user);
                            shareList.add(new MultipleItem(MultipleItem.SHARE,share));
                        }
                        //shareSet.add(shareList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
    }

    private void configLoadMoreData() {
        url = "http://159.75.2.94:8080/api/community/getHotShare?token=" + token + "&&currentPage=" + currentPage;
        getHttpSearch(url);
        //quickAdapter.addData(dataSet.get(currentPage-1));
        currentPage++;
        quickAdapter.notifyDataSetChanged();
        quickAdapter.getLoadMoreModule().loadMoreComplete();
    }

    private void refreshData() {
        currentPage=1;
        url = "http://159.75.2.94:8080/api/community/getHotShare?token=" + token + "&&currentPage=" + currentPage++;
        shareList = new ArrayList<>();
        getHttpSearch(url);
        quickAdapter.setNewData(shareList);
    }

}
