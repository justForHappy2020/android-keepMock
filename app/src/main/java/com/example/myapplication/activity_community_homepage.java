package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.myapplication.entity.ShareAbb;
import com.example.myapplication.entity.User;
import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class activity_community_homepage extends FragmentActivity implements View.OnClickListener {
    private ImageButton goBack;
    private ImageButton more;
    private RoundedImageView headprotraitImg;
    private Button userNameBtn;

    private WrapContentHeightViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentPagerAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private String[] titles = { "动态", "记录"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_homepage);
        initView();
        initData();
    }

    public void initView(){
        goBack = findViewById(R.id.community_homepage_back);
        more = findViewById(R.id.community_homepage_more);
        headprotraitImg = findViewById(R.id.community_homepage_headprotrait);
        userNameBtn  = findViewById(R.id.community_homepage_username);

        mTabLayout = findViewById(R.id.community_homepage_tablayout);
        mViewPager = findViewById(R.id.community_homepage_viewPager);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        goBack.setOnClickListener(this);
        more.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra("userPortraitUrl")).into(headprotraitImg);
        userNameBtn.setText(intent.getStringExtra("userName"));

        fragmentList = new ArrayList<>();
        fragmentList.add(new fragment_community_homepage_shares());
        fragmentList.add(new fragment_community_homepage_shares());
        //初始化适配器
        fragmentAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        mViewPager.setAdapter(fragmentAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.community_homepage_back:
                finish();
                break;
        }
    }
}
