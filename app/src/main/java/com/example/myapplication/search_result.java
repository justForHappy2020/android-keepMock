package com.example.myapplication;

import android.os.Bundle;
import android.view.Window;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class search_result extends FragmentActivity{
    //声明ViewPager
    private ViewPager mViewPager;
    //声明TabLayout
    private TabLayout mTabLayout;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;
    private String[] titles = {"综合", "课程", "动态", "用户"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_course_result);
        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
    }

    private void initDatas() {
        mFragments = new ArrayList<>();
        //将四个Fragment加入集合中
        mFragments.add(new fragment_search_all());
        mFragments.add(new fragment_search_course());
        mFragments.add(new fragment_search_daily());
        mFragments.add(new fragment_search_daily());

        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);

    }

    private void initEvents() {

    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) this.findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) this.findViewById(R.id.tabLayout);

        mViewPager.setOffscreenPageLimit(4);//设置缓存页面上限，默认为3，会出现recyclerview中item重复问题
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

}