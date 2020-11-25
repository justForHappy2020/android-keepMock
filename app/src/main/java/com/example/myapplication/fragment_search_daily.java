package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class fragment_search_daily extends Fragment {

    List<Post> postList= new ArrayList<>();

    private List<Post> datas01= new ArrayList<>();
    private List<MultipleItem> datas02= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_daily, container, false);

        initData();
        initView(view);

        return view;
    }

    private void initView(View view){
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.fragment_daily_recyclerView);
        //设置recyclerView的样式
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //设置adapter
        MultipleItemQuickAdapter myAdapter = new MultipleItemQuickAdapter(datas02);
        recyclerView.setAdapter(myAdapter);

        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
    }

    private void initData(){

        Post post1 = new Post(R.drawable.scenery,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","233");
        Post post2 = new Post(R.drawable.post_img2,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","666");
        Post post3 = new Post(R.drawable.post_img3,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","233");

        datas01.add(post1);
        datas01.add(post2);
        datas01.add(post3);

        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(1)));
    }


}
