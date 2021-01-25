package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.myapplication.Beans.HttpRequest;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.UserAbb;
import com.example.myapplication.utils.HttpUtils;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.wanglu.photoviewerlibrary.OnLongClickListener;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class fragment_community_homepage_shares extends Fragment {
    private MultipleItemQuickAdapter adapter;
    private RecyclerView recyclerView;
    private NineGridImageViewAdapter<String> nineGridAdapter;

    private List<MultipleItem> shareList = new ArrayList<>();
    private int currentPage; //要分页查询的页面
    private Long TOTAL_PAGES;
    private int httpcode;
    private Boolean hasNext;
    private String url;//http请求的url
    private String token;//后期本地获取
    private UserAbb userAbb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_homepage_shares, container, false);
        currentPage = 1;

        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.homepage_shares_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new MultipleItemQuickAdapter(shareList);
        recyclerView.setAdapter(adapter);

        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            //int mCurrentCunter = 0;

            @Override
            public void onLoadMore() {
                if (currentPage > TOTAL_PAGES) {
                    adapter.getLoadMoreModule().loadMoreEnd();
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
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent;
                intent = new Intent(getActivity(), activity_sharedetail.class);
                intent.putExtra("shareAbb",(Serializable) shareList.get(position).getShareAbb());
                startActivity(intent);
            }
        });
        adapter.addChildClickViewIds(R.id.masonry_item_portrait_img,R.id.masonry_item_like);
        //具体课程的监听
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.masonry_item_portrait_img:
                        break;
                }
            }
        });
        nineGridAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String url) {
                Glide.with(context)
                        .load(url)
                        .into(imageView);
            }
            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List list) {
                super.onItemImageClick(context, index, list);
                /*
                ArrayList<String> urls = new ArrayList<>(list);
                PhotoViewer.setData(urls)
                        .setCurrentPage(index)
                        .setImgContainer(recyclerView)
                        .setShowImageViewInterface(new PhotoViewer.ShowImageViewInterface() {
                            @Override
                            public void show(@NotNull ImageView imageView, @NotNull String url) {
                                Glide.with(getContext()).load(url).into(imageView);
                            }
                        })
                        .setOnLongClickListener(new OnLongClickListener() {
                            @Override
                            public void onLongClick(@NotNull View view) {
                                //Long click
                            }
                        })
                        .start((AppCompatActivity) getContext());

                 */

                final ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(list.get(index)).into(imageView);

                final Dialog dialog = new Dialog(getContext(),R.style.FullActivity);
                WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
                attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(attributes);
                dialog.setContentView(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        };


    }

    private void initData(){
        /**
         * Test
         */
        userAbb = new UserAbb();
        userAbb.setNickname("测试名");
        userAbb.setGender("m");
        userAbb.setHeadPortraitUrl("https://kp-test1.oss-cn-beijing.aliyuncs.com/headPortrait/8b5915c445fae755423a8ab89a39b29.jpg");
        userAbb.setUserId(Long.valueOf(1));
        userAbb.setType(1);
        userAbb.setIntro("用户简介");


        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                configLoadMoreData();
                //configLoadMoreData();
            }
        });
    }

    private void getPersonalShares(final String url) {
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
                                        .replace("\\n", "\n")
                                        .replace("\\r","\r")
                                        .replace("\\t","\t"));
                        shareList.add(new MultipleItem(MultipleItem.SHAREFULL,httpRequest.getData().getShareList().get(i),nineGridAdapter));
                    }
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

    }

    private void getRecordData(){

    }

    private void configLoadMoreData() {
        url = "http://159.75.2.94:8080/api/community/getHotShare?token=" + token + "&&currentPage=" + currentPage;
        //url = "http://159.75.2.94:8080/api/community/getPersonalShare?token=" + token + "&userId="+ userAbb.getUserId() + "&currentPage=" + currentPage;//后期改为搜索动态接口
        getPersonalShares(url);
        adapter.notifyDataSetChanged();
        currentPage++;
        adapter.getLoadMoreModule().loadMoreComplete();
    }
}
