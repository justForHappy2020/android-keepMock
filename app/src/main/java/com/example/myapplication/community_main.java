package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.module.LoadMoreModule;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.ShareAbb;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class community_main extends Fragment implements LoadMoreModule {
    private View.OnClickListener onClickListener;
    private List<List> dataSet = new  ArrayList<>();
    private int TOTAL_PAGES;
    private int currentPage; //要分页查询的页面
    private List<MultipleItem> shareList = new ArrayList();
    MultipleItemQuickAdapter quickAdapter;

    private List<Fragment> fragments;

    private FragmentPagerAdapter mAdapter;
    private int from;
    private TabLayout mTabLayout;
    List<ShareAbb> postList= new ArrayList<>();
    private List<ShareAbb> datas01= new ArrayList<>();
    private List<MultipleItem> datas02= new ArrayList<>();

    private String[] titles = {"热门", "关注"};

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_main, container, false);

        currentPage = 1;
        //Bundle bundle = getArguments();
        initView(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
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
/*
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

 */

    private void initView(View view) {
        ImageView img1 = view.findViewById(R.id.community_main_follow);
        ImageView img2 = view.findViewById(R.id.community_main_search);
        final ImageButton float_btn = view.findViewById(R.id.float_button);

        viewPager=view.findViewById(R.id.community_main_viewPager);
        mTabLayout = view.findViewById(R.id.community_main_tablayout);
        mTabLayout.setupWithViewPager(viewPager);
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
                intent = new Intent(getActivity(), activity_community_postNewShare.class);//点击浮动按钮跳转到发表动态界面
                startActivity(intent);
                break;
        }}
    private void initData(){
        initFragments();

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return fragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };

        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                viewPager.setCurrentItem(position);
            }
            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    }
