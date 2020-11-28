package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class search extends Activity implements View.OnClickListener {
    AutoFlowLayout flowLayout;
    EditText et;
    ImageButton search;
    Button clear_history;
    Button quit;

    String estr;
    SharedPreferences shp;
    ArrayList strList;
    Set<String> strSet = new HashSet<String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initData();
    }

    private void initView(){
        flowLayout = findViewById(R.id.flowLayout);
        et = findViewById(R.id.text_inout_search);
        search = findViewById(R.id.searching_button);
        clear_history = findViewById(R.id.clean_history);
        quit = findViewById(R.id.quit_button);

        search.setOnClickListener(this);
        clear_history.setOnClickListener(this);
        quit.setOnClickListener(this);
    }

    private void initData(){
        shp = getSharedPreferences("search_history",MODE_PRIVATE);
        strSet = shp.getStringSet("search_history_list",new HashSet<String>());
        strList = new ArrayList<>(strSet);

        addData(strList);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.searching_button:
                estr = et.getText().toString().trim();
                if(!estr.isEmpty()){
                    Intent i = new Intent(search.this , search_result.class);//启动课程结果activity
                    i.putExtra("searchContent",estr);
                    startActivity(i);

                    strSet= new HashSet<String>(strSet);
                    strSet.add(estr);
                    strList.add(estr);
                    addData(strList);

                    SharedPreferences.Editor editor = shp.edit();
                    editor.putStringSet("search_history_list",strSet);
                    editor.commit();
                    
                }else{
                    Toast.makeText(search.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clean_history:
                flowLayout.removeAllViews();
                et.getText().clear();
                strList.clear();
                strSet.clear();

                SharedPreferences.Editor editor = shp.edit();
                editor.clear();
                editor.commit();
                break;
            case R.id.quit_button:
                finish();
                break;
        }
    }

    private void addData(final ArrayList list) {
        //流式布局适配器
        flowLayout.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(int i) {
                //引入视图
                View inflate = LayoutInflater.from(search.this).inflate(R.layout.item_searchhistory_flowlayout, null, false);
                //获取视图控件
                final TextView auto_tv = inflate.findViewById(R.id.auto_tv);
                //修改值
                auto_tv.setText(list.get(i).toString());
                auto_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取最近搜索中的点击内容进行传值
                        String str = auto_tv.getText().toString();
                        Intent intent = new Intent(search.this, search_result.class);
                        intent.putExtra("searchContent",str);
                        startActivity(intent);
                    }
                });

                //返回视图
                return inflate;
            }
        });
        //清空当前集合
        list.clear();
    }
}
