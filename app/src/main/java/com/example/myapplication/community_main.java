package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class community_main extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_main, container, false);

        initView(view);

        return view;
    }

    private void initView(View view){
        ImageView img1 = view.findViewById(R.id.community_main_follow);
        ImageView img2= view.findViewById(R.id.community_main_search);
        RecyclerView mRecyclerView = view.findViewById(R.id.community_main_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Intent i = new Intent(getActivity(),activity_sharedetail.class);
        startActivity(i);
    }

}
