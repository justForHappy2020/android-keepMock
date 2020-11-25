package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class me_main extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ImageView img1 = view.findViewById(R.id.me_main_setting);
        ImageView img2= view.findViewById(R.id.me_main_scan);
        ImageView img3= view.findViewById(R.id.me_main_message);
        ImageView img4= view.findViewById(R.id.img_sport_detail);
        ImageView img5= view.findViewById(R.id.img_height_detail);
        ImageView img6= view.findViewById(R.id.me_main_mycourse_img);
        ImageView img7= view.findViewById(R.id.me_main_mycourse_detail);

        img1.setImageResource(R.drawable.setting);
        img2.setImageResource(R.drawable.scan);
        img3.setImageResource(R.drawable.envelope);
        img4.setImageResource(R.drawable.next);
        img5.setImageResource(R.drawable.next);
        img6.setImageResource(R.drawable.book);
        img7.setImageResource(R.drawable.next);
    }
}