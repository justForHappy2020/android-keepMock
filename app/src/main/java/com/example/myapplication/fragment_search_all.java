package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragment_search_all extends Fragment {

    List<Post> postList = new ArrayList<>();

    private List<Post> datas01= new ArrayList<>();
    private List<MultipleItem> datas02= new ArrayList<>();
    private List<MultipleItem> datas03= new ArrayList<>();

    String searchContent;//传入用户在搜索界面输入的内容


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_all, container, false);

        Bundle bundle = getArguments();
        searchContent = bundle.getString("searchContent");

        initData();
        initView(view);

        return view;
    }

    private void initView(View view){
        RecyclerView postRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_all_recyclerView);

        //设置recyclerView的样式
        postRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //设置adapter
        MultipleItemQuickAdapter myAdapter = new MultipleItemQuickAdapter(datas02);

        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_course_mini,null);

        RecyclerView courseRecyclerView = (RecyclerView) view1.findViewById(R.id.fragment_course_recyclerView);

        courseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        courseRecyclerView.setAdapter(new MultipleItemQuickAdapter(datas03));

        myAdapter.addHeaderView(view1);//待增添逻辑：如果无课程则不添加“课程”TextView和加载更多按钮（小于等于三条也不显示）

        postRecyclerView.setAdapter(myAdapter);

        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        postRecyclerView.addItemDecoration(decoration);
    }

    private void initData(){

        Course course1 = new Course();
        course1.setCourseName("腹肌");
        course1.setCourseIntro("K1零基础  . 3002.5万人参加");

        Post post1 = new Post(R.drawable.scenery,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","233");
        Post post2 = new Post(R.drawable.post_img2,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","666");
        Post post3 = new Post(R.drawable.post_img3,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","233");

        datas01.add(post1);
        datas01.add(post2);
        datas01.add(post3);

        datas03.add(new MultipleItem(MultipleItem.TEXTONLY,"课程"));

        datas03.add(new MultipleItem(MultipleItem.MINICOURSE,course1));
        datas03.add(new MultipleItem(MultipleItem.MINICOURSE,course1));
        datas03.add(new MultipleItem(MultipleItem.MINICOURSE,course1));

        datas03.add(new MultipleItem(MultipleItem.BUTTON,"加载更多"));

        datas03.add(new MultipleItem(MultipleItem.TEXTONLY,"动态"));

        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.MASONRYPOST, datas01.get(0)));


    }
}
