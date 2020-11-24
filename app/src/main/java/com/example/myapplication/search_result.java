package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.entity.Course;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：search_result
 * 功能：主界面Tab容器，用于装载Fragments，非常方便地切换tab
 * 规范：运动、饮食、社区、我界面的类需extends Fragment类，示例如community_main类
 */

public class search_result extends FragmentActivity{
    //声明ViewPager
    private ViewPager mViewPager;
    //声明TabLayout
    private TabLayout mTabLayout;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;
    String[] titles = {"综合", "课程", "动态", "用户"};

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
        mFragments.add(new fragment_search_daily());
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
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

}


/*
public class search_result extends AppCompatActivity {
    private ViewPager vp;
    //声明存储ViewPager下子视图的集合
    ArrayList<View> views = new ArrayList<>();
    //显示效果中每个视图的标题
    String[] titles = {"综合", "课程", "动态", "用户"};
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

        ArrayList fragments = new ArrayList<>();
        fragments.add(new fragment_search_daily());
        fragments.add(new fragment_search_daily());
        fragments.add(new fragment_search_daily());
        fragments.add(new fragment_search_daily());


        adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    //初始化视图的方法（通过布局填充器获取用于滑动的视图并存入对应的的集合）
    private void initView() {
        View v1 = getLayoutInflater().inflate(R.layout.fragment_search_course_mini, null);
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
*/
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

