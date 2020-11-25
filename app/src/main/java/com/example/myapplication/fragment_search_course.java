package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragment_search_course extends Fragment {

    private List<Map<String,Object>> lists;

    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_course, container, false);

        initData();
        initView(view);

        return view;
    }

    private void initView(View view){
        listView = (ListView) view.findViewById(R.id.fragment_course_listView);
        listView.setAdapter(new SimpleAdapter(getActivity(),lists,R.layout.course_item
                ,new String[]{"name","text","hint","img"}
                ,new int[]{R.id.course_img,R.id.course_item_text,R.id.course_item_hint,R.id.course_item_bgImg}));
    }

    private void initData(){

        lists = new ArrayList<>();

        //testData
        String[] courseNames = {"腹肌撕裂者初级","哑铃手臂塑形","腹肌撕裂者进阶","腹肌撕裂者进阶","腹肌撕裂者进阶"};
        String[] courseHints = {"“明星也在练的腹肌课！”","“虐腹就是它了”","“虐腹就是它了”","“虐腹就是它了”","“虐腹就是它了”"};
        String[] courseTexts ={"K1零基础 . 13分钟 . 3002万人已参加","K1零基础 . 13分钟 . 3002万人已参加","K1零基础 . 13分钟 . 3002万人已参加","K1零基础 . 13分钟 . 3002万人已参加","K1零基础 . 13分钟 . 3002万人已参加"};
        int[] courseImgs = {R.drawable.course_background,R.drawable.course_background,R.drawable.course_background,R.drawable.course_background,R.drawable.course_background};

        for(int i = 0;i < courseNames.length;i++){
            Map<String,Object> map =new HashMap<>();
            map.put("name",courseNames[i]);
            map.put("img",courseImgs[i]);
            map.put("text",courseTexts[i]);
            map.put("hint",courseHints[i]);
            lists.add(map);
        }
    }
}
