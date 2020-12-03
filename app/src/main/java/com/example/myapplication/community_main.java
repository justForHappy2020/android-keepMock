package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Post;
import com.example.myapplication.entity.Share;

import java.util.ArrayList;
import java.util.List;


public class community_main extends Fragment {
    private View.OnClickListener onClickListener;
    List<Share> postList= new ArrayList<>();

    private List<Share> datas01= new ArrayList<>();
    private List<MultipleItem> datas02= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_main, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view){
        ImageButton img1 = view.findViewById(R.id.community_main_follow);
        ImageButton img2= view.findViewById(R.id.community_main_search);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommunityClick(view);
            }
        };
        img1.setOnClickListener(onClickListener);
        img2.setOnClickListener(onClickListener);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.community_main_recyclerView);
        //设置recyclerView的样式
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        MultipleItemQuickAdapter myAdapter = new MultipleItemQuickAdapter(datas02);
        recyclerView.setAdapter(myAdapter);

        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
    }
    public void onCommunityClick(View view){
        Intent intent = null;
        switch (view.getId()) {
            case R.id.community_main_follow:
                intent = new Intent(getActivity(),search_user.class);//点击搜索按钮跳转到搜索结果
                startActivity(intent);
                break;
            case R.id.community_main_search:
                intent = new Intent(getActivity(),search.class);//点击搜索按钮跳转到搜索结果
                startActivity(intent);
                break;
        }}
    private void initData(){

        Share share1 = new Share("用户1","测试内容哈哈哈",R.drawable.sucai,R.drawable.scenery,"666","233");
        Share share2 = new Share("用户2","测试内容哈哈哈",R.drawable.sucai,R.drawable.post_img2,"777","666");
        Share share3 = new Share("用户3","测试内容哈哈哈",R.drawable.sucai,R.drawable.post_img3,"888","233");

        datas01.add(share1);
        datas01.add(share2);
        datas01.add(share3);

        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(1)));
    }
}
