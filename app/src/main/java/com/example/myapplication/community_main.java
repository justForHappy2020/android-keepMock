package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Post;
import com.example.myapplication.entity.Share;
import com.example.myapplication.utils.HttpUtils;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class community_main extends Fragment implements LoadMoreModule {
    private View.OnClickListener onClickListener;
    private List<List> dataSet = new  ArrayList<>();
    private int TOTAL_PAGES;
    private int currentPage; //要分页查询的页面
    private List<MultipleItem> shareList = new ArrayList();
    MultipleItemQuickAdapter quickAdapter;
    private ViewPager viewpager;
    private List<Fragment> fragments;
    private FragmentPagerAdapter mAdapter;
    private int from;
    private TabLayout mTabLayout;
    private String[] titles = {"热门","关注"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_main, container, false);
        viewpager = view.findViewById(R.id.community_main_viewPager);
        currentPage = 1;
        //Bundle bundle = getArguments();
        initData();
        initView(view);
        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragments();
        viewpager.setAdapter(new MPagerAdapter(getChildFragmentManager()));

    }
    private void initFragments() {
        Bundle bundle = new Bundle();
        fragments = new ArrayList<>();
        fragment_community_main_hot fh = new fragment_community_main_hot();
        fragment_community_main_follow ff = new fragment_community_main_follow();
        fh.setArguments(bundle);
        ff.setArguments(bundle);
        fragments.add(fh);
        fragments.add(ff);
    }

    private class MPagerAdapter extends FragmentPagerAdapter {


        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private void initView(View view) {
        ImageButton img1 = view.findViewById(R.id.community_main_follow);
        ImageButton img2 = view.findViewById(R.id.community_main_search);
        final ImageButton float_btn = view.findViewById(R.id.float_button);
        mTabLayout = view.findViewById(R.id.community_main_tablayout);
        mTabLayout.setupWithViewPager(viewpager);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommunityClick(view);
            }
        };
        img1.setOnClickListener(onClickListener);
        img2.setOnClickListener(onClickListener);
        float_btn.setOnClickListener(onClickListener);
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
                intent = new Intent(getActivity(),community4.class);//点击浮动按钮跳转到发表动态界面
                startActivity(intent);
                break;
        }}
    private void initData(){
        Bundle bundle = new Bundle();
        fragments = new ArrayList<>();

        fragment_community_main_follow ff = new fragment_community_main_follow();
        fragment_community_main_hot fh = new fragment_community_main_hot();

        ff.setArguments(bundle);
        fh.setArguments(bundle);

        fragments.add(ff);
        fragments.add(fh);

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return fragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return fragments.size();
            }
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

        };
        viewpager.setAdapter(mAdapter);
        switch (from){
            case 0:
                viewpager.setCurrentItem (0);
                break;
            case 1:
                viewpager.setCurrentItem (1);
                break;
            default:
                break;
        }
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                viewpager.setCurrentItem(position);
            }
            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }}
