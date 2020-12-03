package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.entity.Comment;
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

        quickAdapter =  new BaseQuickAdapter<Comment, BaseViewHolder >(R.layout.item_comment_main,mainCommentList){
            @Override
            protected void convert(BaseViewHolder helper, Comment comment) {
                User user = comment.getUser();
                helper.setText(R.id.item_comment_main_username,comment.getUser().getUserName())
                        .setText(R.id.item_comment_main_text, comment.getTextContent())
                        .setImageResource(R.id.item_comment_main_username_headprotrait, comment.getUser().getPortraitImg());
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
        quickAdapter.addChildClickViewIds(R.id.item_comment_main_like, R.id.item_comment_main_subcomment, R.id.item_comment_main_loadmoresub,R.id.item_comment_main_username,R.id.item_comment_main_username_headprotrait);
// 设置子控件点击监听
        quickAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Intent intent;
                switch (view.getId()){
                    case R.id.item_comment_main_like:
                        Toast.makeText(activity_sharedetail.this, "点赞第"+position+"个回复成功！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_comment_main_subcomment:
                        Toast.makeText(activity_sharedetail.this, "回复第"+position+"个回复", Toast.LENGTH_SHORT).show();
                        /*
                        intent= new Intent(activity_sharedetail.this,activity_subcomment.class);
                        intent.putExtra("main_commentId",0123);
                        startActivity(intent);

                         */
                        break;
                    case R.id.item_comment_main_loadmoresub:
                        Toast.makeText(activity_sharedetail.this, "加载第"+position+"个回复的子回复", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_comment_main_username:

                    case R.id.item_comment_main_username_headprotrait:
                        Toast.makeText(activity_sharedetail.this, "跳转到第"+position+"个用户的详情页", Toast.LENGTH_SHORT).show();
                         /*
                        intent= new Intent(activity_sharedetail.this,activity_userdetail.class);
                        intent.putExtra("userId",0123);
                        startActivity(intent);

                         */
                        break;
                }
            }
        });

        recyclerView.setAdapter(quickAdapter);

    }

    private void initData() {


        /*TestData*/

        Comment comment;
        User user = new User(0,"乔瑟夫·乔斯达",R.drawable.sucai);
        for (int i = 0; i < 5; i++) {
            comment = new Comment();
            comment.setUser(user);
            comment.setTextContent("昨天学习了波纹疾走，今天感觉肌肉力量增强了！");
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