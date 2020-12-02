package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.entity.Comment;
import com.example.myapplication.entity.Course;
import com.example.myapplication.entity.User;

import java.util.ArrayList;
import java.util.List;

public class activity_sharedetail extends Activity implements View.OnClickListener{

    private List<List> dataSet = new ArrayList<>();
    private int TOTAL_PAGES;

    BaseQuickAdapter<Comment, BaseViewHolder> quickAdapter;
    RecyclerView recyclerView;

    private String commentId;//传入帖子ID

    private int httpcode;
    private Boolean hasNext;
    private int currentPage; //要分页查询的页面
    private List<Comment> mainCommentList = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_sharedetail);

        initView();
        initData();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.community_reviews_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity_sharedetail.this));


        quickAdapter =  new BaseQuickAdapter<Comment, BaseViewHolder >(R.layout.item_review_main,mainCommentList){


            @Override
            protected void convert(BaseViewHolder helper, Comment comment) {
                //可链式调用赋值
                User user = comment.getUser();
                helper.setText(R.id.community1_visitors1,comment.getUser().getUserName())
                        .setText(R.id.community1_visitors1_text, comment.getTextContent())
                        .setImageResource(R.id.headprotrait_visitor1, comment.getUser().getPortraitImg());//comment.getUser().getPortraitImg()
            }

        };

        //new QuickAdapter(R.layout.item_review_main, mainCommentList);

        /*
        configLoadMoreData();

        quickAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            //int mCurrentCunter = 0;

            @Override
            public void onLoadMore() {
                if (currentPage > TOTAL_PAGES) {
                    quickAdapter.getLoadMoreModule().loadMoreEnd();
                } else {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            configLoadMoreData();
                        }
                    }, 1000);

                }

            }
        });

         */

        //具体课程的监听
        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter quickAdapter, @NonNull View view, int position) {
                Toast.makeText(activity_sharedetail.this, "Click:"+String.valueOf(position), Toast.LENGTH_SHORT).show();
                /*
                Intent intent;
                intent = new Intent(activity_sharedetail.this, course_main.class);
                intent.putExtra("review", mainCommentList.get(position).getCourseId());
                startActivity(intent);
                 */

            }
        });

        recyclerView.setAdapter(quickAdapter);

    }

    private void initData() {


        /*TestData*/

        Comment comment;
        User user = new User(0,"TestName",R.drawable.sucai);
        for (int i = 0; i < 5; i++) {
            comment = new Comment();
            comment.setUser(user);
            comment.setTextContent("测试内容哦");
            comment.setLikeNum(233);
            mainCommentList.add(comment);
        }
        dataSet.add(mainCommentList);
    }

    private void configLoadMoreData() {
        String url;//http请求的url
        //url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/course/searchCourse?keyword=" + keyWord + "&&currentPage=" + currentPage;
        //getHttpSearch(url);
        /*
        dataSet.add(mainCommentList);
        quickAdapter.addData(dataSet.get(currentPage-1));
        currentPage++;
        quickAdapter.getLoadMoreModule().loadMoreEnd();

         */
    }

    @Override
    public void onClick(View view) {

    }
}