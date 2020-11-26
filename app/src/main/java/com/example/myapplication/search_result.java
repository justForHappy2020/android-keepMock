package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class search_result extends FragmentActivity{

    final int SEARCH_ALL = 0;
    final int SEARCH_COURSE = 1;

    private EditText etInput;
    //声明ViewPager
    private ViewPager mViewPager;
    //声明TabLayout
    private TabLayout mTabLayout;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合

    private List<Fragment> mFragments;
    private String[] titles = {"综合", "课程", "动态", "用户"};
    private String searchContent;
    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_result);

        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
    }

    private void initDatas() {

        Bundle bundle = new Bundle();
        from = getIntent().getIntExtra("from",0);
        bundle.putString("searchContent", searchContent);

        fragment_search_all f1 = new fragment_search_all();
        fragment_search_course f2 = new fragment_search_course();
        fragment_search_daily f3 = new fragment_search_daily();
        fragment_search_daily f4 = new fragment_search_daily();

        f1.setArguments(bundle);
        f2.setArguments(bundle);
        f3.setArguments(bundle);
        f4.setArguments(bundle);

        mFragments = new ArrayList<>();
        //将四个Fragment加入集合中
        mFragments.add(f1);
        mFragments.add(f2);
        mFragments.add(f3);
        mFragments.add(f4);

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
        switch (from){
            case 0:
                break;
            case 1:
                mViewPager.setCurrentItem (1);
                break;
             default:
                 break;
        }

    }

    private void initEvents() {

        findViewById(R.id.search_back).setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                finish();
            }
        });
    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) this.findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) this.findViewById(R.id.tabLayout);
        etInput = this.findViewById(R.id.text_input_search);

        mViewPager.setOffscreenPageLimit(4);//设置缓存页面上限，默认为3，会出现recyclerview中item重复问题
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        etInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager manager = ((InputMethodManager)search_result.this.getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

}