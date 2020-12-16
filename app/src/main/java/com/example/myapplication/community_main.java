package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Share;
import com.example.myapplication.entity.User;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class community_main extends Fragment {
    private View.OnClickListener onClickListener;
    List<Share> postList= new ArrayList<>();

    private List<Share> datas01= new ArrayList<>();
    private List<MultipleItem> datas02= new ArrayList<>();

    private String[] titles = {"热门", "关注"};

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_main, container, false);
        initView(view);
        initData();

        return view;
    }

    private void initView(View view){
        ImageView img1 = view.findViewById(R.id.community_main_follow);
        ImageView img2= view.findViewById(R.id.community_main_search);
        tabLayout = view.findViewById(R.id.community_main_tablayout);
        viewPager = view.findViewById(R.id.community_main_viewPager);

        viewPager.setOffscreenPageLimit(2);//设置缓存页面上限，默认为3
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommunityClick(view);
            }
        };
        img1.setOnClickListener(onClickListener);
        img2.setOnClickListener(onClickListener);

    }
    public void onCommunityClick(View view){
        Intent intent = null;
        switch (view.getId()) {
            case R.id.community_main_follow:
                intent = new Intent(getActivity(),search_user.class);//点击搜索按钮跳转到搜索结果
                startActivity(intent);
                break;
            case R.id.community_main_search:
                intent = new Intent(getActivity(),search.class);//点击搜索按钮跳转到搜索结果
                startActivity(intent);
                break;
        }}
    private void initData(){
        mFragments = new ArrayList<>();
        mFragments.add(new fragment_community_shares());
        mFragments.add(new fragment_community_shares());

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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

        viewPager.setAdapter(mAdapter);
    }
}
