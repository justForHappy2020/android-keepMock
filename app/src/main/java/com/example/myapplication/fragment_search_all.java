package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapplication.entity.MasonryPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragment_search_all extends Fragment {

    List<MasonryPost> postList= new ArrayList<>();
    private List<Map<String,Object>>courseList = new ArrayList<>();;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_all, container, false);

        initData();
        initView(view);
        return view;
    }

    private void initView(View view){
        RecyclerView postRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_daily_recyclerView);
        ListView courseListView = (ListView) view.findViewById(R.id.fragment_course_listView);

        //设置recyclerView的样式
        postRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //设置adapter
        postRecyclerView.setAdapter(new MasonryAdapter(postList));
        courseListView.setAdapter(new SimpleAdapter(getActivity()
                ,courseList
                ,R.layout.course_item_mini
                ,new String[]{"courseImg","courseName","courseText"}
                ,new int[]{R.id.course_img,R.id.course_name,R.id.course_text}));

        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        postRecyclerView.addItemDecoration(decoration);
    }

    private void initData(){

        String[] name = {"腹肌撕裂者初级","腹肌撕裂者进阶","腹肌撕裂者高阶"};
        String[] content ={"K1零基础 . 13分钟 . 3002万人已参加","K1零基础 . 20分钟 . 302万人已参加","K2级基础 . 30分钟 . 6万人已参加"};
        int[] courseImgs = {R.drawable.sucai,R.drawable.sucai,R.drawable.sucai};

        for(int i = 0;i < name.length;i++){
            Map<String,Object> map =new HashMap<>();
            map.put("courseImg",courseImgs[i]);
            map.put("courseName",name[i]);
            map.put("courseText",content[i]);
            courseList.add(map);
        }

        MasonryPost post1 = new MasonryPost(R.drawable.scenery,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","233");
        MasonryPost post2 = new MasonryPost(R.drawable.post_img2,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","233");
        MasonryPost post3 = new MasonryPost(R.drawable.post_img3,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","233");

        postList.add(post1);
        postList.add(post2);
        postList.add(post3);
        postList.add(post1);
        postList.add(post3);
        postList.add(post3);
        postList.add(post2);
    }
}
