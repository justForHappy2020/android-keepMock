package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Share;
import com.example.myapplication.entity.User;

import java.util.ArrayList;
import java.util.List;

public class fragment_search_daily extends Fragment {

    List<Share> shareList = new ArrayList<>();

    private List<Share> datas01= new ArrayList<>();
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
        User user = new User(6666,"迪奥·布兰度",R.drawable.sucai);

        Share share1 = new Share(user.getUserName(),"波纹呼吸法",R.drawable.sucai,R.drawable.scenery,"233","666",user);
        Share share2 = new Share(user.getUserName(),"波纹呼吸法",R.drawable.sucai,R.drawable.post_img2,"777","235",user);
        Share share3= new Share(user.getUserName(),"波纹呼吸法",R.drawable.sucai,R.drawable.post_img3,"666","857",user);

        datas01.add(share1);
        datas01.add(share2);
        datas01.add(share3);

        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(1)));
    }


}
