package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


/**
* 类名：MainActivity_ViewPage
* 功能：主界面Tab容器，用于装载Fragments，非常方便地切换tab
* 规范：运动、饮食、社区、我界面的类需extends Fragment类，示例如community_main类
*/

public class MainActivity_ViewPage extends FragmentActivity implements View.OnClickListener {
    //声明ViewPager
    private ViewPager mViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;

    //四个Tab对应的布局
    private LinearLayout mTabSport;
    private LinearLayout mTabSocial;
    private LinearLayout mTabEating;
    private LinearLayout mTabMe;

    //四个Tab对应的ImageButton
    private ImageView mImgSport;
    private ImageView mImgSocial;
    private ImageView mImgEating;
    private ImageView mImgMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_view_page);
        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
    }

    private void initDatas() {
        mFragments = new ArrayList<>();
        //将四个Fragment加入集合中
        //mFragments.add(new exercise_main());
        mFragments.add(new community_main());
        mFragments.add(new community_main());
        mFragments.add(new diet_main());
        mFragments.add(new me_main());
        //mFragments.add(new diet_main());
        //mFragments.add(new me_main());

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

        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        //设置ViewPager的切换监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                mViewPager.setCurrentItem(position);
                resetImgs();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置四个Tab的点击事件
        mTabSport.setOnClickListener(this);
        mTabSocial.setOnClickListener(this);
        mTabEating.setOnClickListener(this);
        mTabMe.setOnClickListener(this);

    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mTabSport = (LinearLayout) findViewById(R.id.sport_buttton);
        mTabSocial = (LinearLayout) findViewById(R.id.social_button);
        mTabEating = (LinearLayout) findViewById(R.id.eating_button);
        mTabMe = (LinearLayout) findViewById(R.id.me_button);

        mImgSport = (ImageView) findViewById(R.id.sport_img);
        mImgSocial= (ImageView) findViewById(R.id.social_img);
        mImgEating = (ImageView) findViewById(R.id.eating_img);
        mImgMe = (ImageView) findViewById(R.id.me_img);

    }

    @Override
    public void onClick(View v) {
        //先将四个ImageButton置为灰色
        resetImgs();

        //根据点击的Tab切换不同的页面及设置对应的ImageButton为深色
        switch (v.getId()) {
            case R.id.sport_buttton:
                selectTab(0);
                break;
            case R.id.social_button:
                selectTab(1);
                break;
            case R.id.eating_button:
                selectTab(2);
                break;
            case R.id.me_button:
                selectTab(3);
                break;
        }
    }

    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为深色
        switch (i) {
            case 0:
                mImgSport.setImageResource(R.drawable.sport_choosen);
                break;
            case 1:
                mImgSocial.setImageResource(R.drawable.social_chossen);
                break;
            case 2:
                mImgEating.setImageResource(R.drawable.eating_choosen);
                break;
            case 3:
                mImgMe.setImageResource(R.drawable.me_choosen);
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //将四个ImageButton设置为浅灰色
    private void resetImgs() {
        mImgSport.setImageResource(R.drawable.sport_unchoosen);
        mImgSocial.setImageResource(R.drawable.social_unchoosen);
        mImgEating.setImageResource(R.drawable.eating_unchoosen);
        mImgMe.setImageResource(R.drawable.me_unchoosen);
    }
}
