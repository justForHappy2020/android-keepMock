package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Share;

import java.util.ArrayList;
import java.util.List;

public class  fragment_search_all extends Fragment {

    private List<Share> datas01= new ArrayList<>();
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
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_course_mini,null);

        RecyclerView postRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_all_recyclerView);
        RecyclerView courseRecyclerView = (RecyclerView) headerView.findViewById(R.id.fragment_course_recyclerView);

        MultipleItemQuickAdapter myAdapter = new MultipleItemQuickAdapter(datas02);
        MultipleItemQuickAdapter miniCourseAdapter = new MultipleItemQuickAdapter(datas03);

        miniCourseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int position) {
                Toast.makeText(getActivity(),"OnClick Position: " + position,Toast.LENGTH_SHORT).show();
            }
        });
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseRecyclerView.setAdapter(miniCourseAdapter);

        //设置recyclerView的样式
        postRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        myAdapter.addHeaderView(headerView);//待增添逻辑：如果无课程则不添加“课程”TextView和加载更多按钮（小于等于三条也不显示）
        postRecyclerView.setAdapter(myAdapter);

        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        postRecyclerView.addItemDecoration(decoration);
    }

    private void initData(){

        Course course1 = new Course();
        course1.setCourseName("腹肌");
        course1.setCourseIntro("K1零基础  . 3002.5万人参加");

        Share share1 = new Share(R.drawable.scenery,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","233");
        Share share2 = new Share(R.drawable.post_img2,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","666");
        Share share3 = new Share(R.drawable.post_img3,R.drawable.sucai,"测试标题","测试内容哈哈哈","测试用户名","233");

        datas01.add(share1);
        datas01.add(share2);
        datas01.add(share3);

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
