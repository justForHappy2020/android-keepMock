package com.example.myapplication;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.myapplication.Beans.HttpRequest;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.ShareAbb;
import com.example.myapplication.entity.User;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class fragment_search_daily extends Fragment {


    private List<MultipleItem> shareList = new ArrayList<>();
    
    private int currentPage; //要分页查询的页面

    private Long TOTAL_PAGES;
    private int httpcode;
    private Boolean hasNext;
    private String keyWord;//搜索的关键词

    MultipleItemQuickAdapter quickAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    private String url;//http请求的url
    private String token;//后期本地获取

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
        View view = inflater.inflate(R.layout.fragment_search_daily, container, false);

        currentPage = 1;
        Bundle bundle = getArguments();
        keyWord = bundle.getString("keyWord");
        initView(view);
        return view;
    }

     @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initView(View view){

        recyclerView= (RecyclerView) view.findViewById(R.id.fragment_daily_recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.fragment_daily_swipe_refresh);

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

        //设置adapter
        quickAdapter = new MultipleItemQuickAdapter(shareList);

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
        //quickAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        //quickAdapter.getLoadMoreModule().setAutoLoadMore(false);

        //具体课程的监听
        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent;
                intent = new Intent(getActivity(), activity_sharedetail.class);
                intent.putExtra("shareAbb",(Serializable) shareList.get(position).getShareAbb());

                intent.putExtra("ShareId",shareList.get(position).getShareAbb().getShareId());
                startActivity(intent);
            }
        });
        quickAdapter.addChildClickViewIds(R.id.masonry_item_portrait_img,R.id.masonry_item_like);
        //具体课程的监听
        quickAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.masonry_item_portrait_img:
                        clickHead(position);
                        break;
                    case R.id.masonry_item_like:
                        //点赞
                        break;
                }
            }
        });

        quickAdapter.setEmptyView(R.layout.empty_view);//设置空布局，没有数据时显示
        quickAdapter.setAnimationEnable(true);

        recyclerView.setAdapter(quickAdapter);

        //设置recyclerView的样式
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

    }

    private void initData(){

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                refreshData();
                //configLoadMoreData();
            }
        });

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
                JSONObject jsonObject1 = null;
                try {
                    responseData = HttpUtils.connectHttpGet(url);
                    HttpRequest httpRequest = JSON.parseObject(responseData, HttpRequest.class);
                    httpcode=httpRequest.getCode();
                    TOTAL_PAGES=httpRequest.getData().getTotalPages();
                    for(int i = 0;i<httpRequest.getData().getShareList().size();i++){
                        //文本数据在传输时会在转义符前多加一个\，需要去除;
                        httpRequest.getData().getShareList().get(i).setContent(
                                httpRequest.getData().getShareList().get(i).getContent()
                                        .replace("\\n", "\t")
                                        .replace("\\r","\r")
                                        .replace("\\t","\t"));
                        shareList.add(new MultipleItem(MultipleItem.MASONRYPOST,httpRequest.getData().getShareList().get(i)));
                    }

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

    /*
    private void getHttpSearch(final String url) {
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
                        hasNext = jsonObject2.getBoolean("hasNext");
                        TOTAL_PAGES = jsonObject2.getInt("totalPages");
                        //得到筛选的课程list
                        JSONArray JSONArrayShare = jsonObject2.getJSONArray("shareList");
                        for (int i = 0; i < JSONArrayShare.length(); i++) {
                            JSONObject jsonObject = JSONArrayShare.getJSONObject(i);
                            //相应的内容
                            ShareAbb shareAbb = new ShareAbb();
                            User user = new User();
                            user.setNickname(jsonObject.getString("nickname"));
                            user.setHeadPortraitUrl(jsonObject.getString("headPortraitUrl"));

                            shareAbb.setContent(jsonObject.getString("content"));
                            shareAbb.setImgUrls(jsonObject.getString("imgUrls"));
                            shareAbb.setCommentNumbers(jsonObject.getString("commentNumbers"));
                            //shareAbb.setUser(user);
                            shareList.add(new MultipleItem(MultipleItem.MASONRYPOST, shareAbb));
                        }
                    }
                }  catch (JSONException e) {
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
            thread.join(5000);
        } catch (InterruptedException e) {
            errorMsg=e.getMessage();
            e.printStackTrace();
        }
        if (httpcode != 200) {
            showErrorAlert("fragment_search_daily: "+errorMsg);
        }
    }

     */

    private void configLoadMoreData() {
        url = "http://159.75.2.94:8080/api/community/getHotShare?token=" + token + "&currentPage=" + currentPage;//后期改为搜索动态接口
        getHttpSearch(url);
        //quickAdapter.notifyDataSetChanged();
        currentPage++;
        quickAdapter.getLoadMoreModule().loadMoreComplete();
    }

    private void refreshData() {
        currentPage = 1;
        url = "http://159.75.2.94:8080/api/community/getHotShare?token=" + token + "&currentPage=" + currentPage++;
        shareList=new ArrayList<>();
        getHttpSearch(url);
        quickAdapter.setNewData(shareList);
    }

}
