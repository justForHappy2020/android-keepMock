package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class search_result extends AppCompatActivity {
    private ViewPager vp;
    //声明存储ViewPager下子视图的集合
    ArrayList<View> views = new ArrayList<>();
    //显示效果中每个视图的标题
    String[] titles = {"综合", "课程", "用户", "动态"};
    TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_course_result);
        vp = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        initView();//调用初始化视图方法
        tabLayout.setupWithViewPager(vp);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        vp.setAdapter(new MyAdapter());//设置适配器
    }

    //初始化视图的方法（通过布局填充器获取用于滑动的视图并存入对应的的集合）
    private void initView() {
        View v1 = getLayoutInflater().inflate(R.layout.fragment_search_all, null);
        View v2 = getLayoutInflater().inflate(R.layout.fragment_search_course, null);
        View v3 = getLayoutInflater().inflate(R.layout.fragment_search_user, null);
        View v4 = getLayoutInflater().inflate(R.layout.fragment_search_daily, null);

        views.add(v1);
        views.add(v2);
        views.add(v3);
        views.add(v4);
    }

    class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //重写销毁滑动视图布局（将子视图移出视图存储集合（ViewGroup））
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        //重写初始化滑动视图布局（从视图集合中取出对应视图，添加到ViewGroup）
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = views.get(position);
            container.addView(v);
            return v;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}

    /*ViewPager viewPager;
    TextView tv_title;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;
    ArrayList<MyFragment> fragments;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv_title = (TextView) findViewById(R.id.tv_title);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tv_title.setText("Tablayout使用");
        fragments = new ArrayList<>();
            fragments.add(new MyFragment("第一",R.layout.fragment1));



        adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
*/

