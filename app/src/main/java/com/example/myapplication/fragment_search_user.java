package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Post;
import com.example.myapplication.entity.User;

import java.util.ArrayList;
import java.util.List;

public class fragment_search_user extends Fragment {

    List<User> postList= new ArrayList<>();

    private List<User> datas01= new ArrayList<>();
    private List<MultipleItem> datas02= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view){
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.fragment_user_recyclerView);
        //设置recyclerView的样式
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //设置adapter
        MultipleItemQuickAdapter myAdapter = new MultipleItemQuickAdapter(datas02);
        recyclerView.setAdapter(myAdapter);

        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
    }

    private void initData(){
        User user1 = new User("用户1",R.drawable.sucai);
        User user2 = new User("用户2",R.drawable.sucai);
        User user3 = new User("用户3",R.drawable.sucai);

        datas01.add(user1);
        datas01.add(user2);
        datas01.add(user3);

        datas02.add(new MultipleItem(MultipleItem.USER, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.USER, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.USER, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.USER, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.USER, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.USER, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.USER, datas01.get(1)));
    }

}