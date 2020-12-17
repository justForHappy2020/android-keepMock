package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class me_main extends Fragment {
    private View.OnClickListener onClickListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        TextView tv_username = view.findViewById(R.id.user_id);
        TextView tv_flowcount = view.findViewById(R.id.flow_count);
        TextView tv_fanscount = view.findViewById(R.id.fans_count);
        TextView tv_sharecount = view.findViewById(R.id.share_count);
        TextView tv_alltime = view.findViewById(R.id.all_sport_time);
        TextView tv_weekburn = view.findViewById(R.id.week_burn);
        TextView tv_bodyheight = view.findViewById(R.id.body_height);
        TextView tv_lasttime = view.findViewById(R.id.last_record_time);
        ImageButton btn_setting = view.findViewById(R.id.setting);
        ImageButton btn_scan = view.findViewById(R.id.scan);
        ImageButton btn_information = view.findViewById(R.id.information);
        ImageButton btn_cmore = view.findViewById(R.id.my_course_more);
        ImageButton btn_sportdatamore = view.findViewById(R.id.sportdata_more);
        ImageButton btn_bodydatamore = view.findViewById(R.id.bodydata_more);
        ImageButton btn_view = view.findViewById(R.id.hide_data);


        btn_setting.setImageResource(R.drawable.setting);
        btn_scan.setImageResource(R.drawable.scan);
        btn_information.setImageResource(R.drawable.envelope);
        btn_cmore.setImageResource(R.drawable.my_course_more);
        btn_sportdatamore.setImageResource(R.drawable.my_course_more);
        btn_bodydatamore.setImageResource(R.drawable.my_course_more);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMeClick(view);
            }
        };
        btn_setting.setOnClickListener(onClickListener);
        btn_scan.setOnClickListener(onClickListener);
        btn_information.setOnClickListener(onClickListener);
        btn_cmore.setOnClickListener(onClickListener);
        btn_sportdatamore.setOnClickListener(onClickListener);
        btn_bodydatamore.setOnClickListener(onClickListener);
        //img1.setImageResource(R.drawable.setting);
        //img2.setImageResource(R.drawable.scan);
        //img3.setImageResource(R.drawable.envelope);
        //img4.setImageResource(R.drawable.next);
        //img5.setImageResource(R.drawable.next);
        //img6.setImageResource(R.drawable.book);
        //img7.setImageResource(R.drawable.next);
    }
    public void onMeClick(View view){
        Intent intent = null;
        switch (view.getId()) {
            case R.id.me_main_setting:
                intent = new Intent(getActivity(),me_setting.class);//点击搜索按钮跳转到搜索结果
                startActivity(intent);
                break;
            case R.id.me_main_message:
                intent= new Intent(getActivity(),me_information.class);//点击搜索按钮跳转到搜索结果
                //启动
                startActivity(intent);
                break;
            case R.id.me_main_mycourse_detail:
                break;
            case R.id.setting:
                    intent = new Intent(getActivity(),me_setting.class);//点击搜索按钮跳转到搜索结果
                    startActivity(intent);
                    break;
            case R.id.information:
                    intent= new Intent(getActivity(),me_information.class);//点击搜索按钮跳转到搜索结果
                    //启动
                    startActivity(intent);
                    break;
            case R.id.my_course_more:
                intent= new Intent(getActivity(),my_course.class);//点击搜索按钮跳转到搜索结果
                //启动
                startActivity(intent);
                break;
            case R.id.img_height_detail:
                break;
            case R.id.bodydata_more:
                intent= new Intent(getActivity(),me_body_data.class);//点击搜索按钮跳转到搜索结果
                //启动
                startActivity(intent);
                break;

            case R.id.img_sport_detail:
                break;
            case R.id.sportdata_more:
                intent= new Intent(getActivity(),me_sport_data.class);//点击搜索按钮跳转到搜索结果
                //启动
                startActivity(intent);
                break;
        }

    }
}