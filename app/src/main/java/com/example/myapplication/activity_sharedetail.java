package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private RecyclerView recyclerView;

    private Button loadMoreComments;
    private ImageView back;

    BaseQuickAdapter<Comment, BaseViewHolder> quickAdapter;


    private String commentId;//传入帖子ID

    private int httpcode;
    private Boolean hasNext;
    private int currentPage; //要分页查询的页面
    private List<Comment> mainCommentList = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_sharedetail);

        initData();
        initView();

    }

    private void initView() {
        loadMoreComments = findViewById(R.id.sharedetail_loadmore_comments);
        back = findViewById(R.id.community1_leftarrow);
        recyclerView = (RecyclerView) findViewById(R.id.community_reviews_main);

        loadMoreComments.setOnClickListener(this);
        back.setOnClickListener(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(activity_sharedetail.this));

        recyclerView.setNestedScrollingEnabled(false);//禁止嵌套滚动，使得recyclerview可以平滑滚动

        quickAdapter =  new BaseQuickAdapter<Comment, BaseViewHolder >(R.layout.item_comment_main,dataSet.get(0)){
            @Override
            protected void convert(BaseViewHolder helper, Comment comment) {
                User user = comment.getUser();
                helper.setText(R.id.item_comment_main_username,comment.getUser().getNickname())
                        .setText(R.id.item_comment_main_text, comment.getTextContent())
                        .setImageResource(R.id.item_comment_main_username_headprotrait, R.drawable.sucai);//comment.getUser().getHeadPortraitUrl()
            }
        };

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
        User user = new User("乔瑟夫·乔斯达","URL");
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
        switch (view.getId()){
            case R.id.community1_leftarrow:
                finish();
                break;
            case R.id.community1_news:
                Toast.makeText(activity_sharedetail.this, "回复", Toast.LENGTH_SHORT).show();
                break;
            case R.id.community1_thumbs:
                Toast.makeText(activity_sharedetail.this, "点赞成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sharedetail_loadmore_comments:
                Toast.makeText(activity_sharedetail.this, "读取中···", Toast.LENGTH_SHORT).show();

                /*TestData*/

                List<Comment> testList = new ArrayList();
                Comment comment;
                User user = new User("乔瑟夫·乔斯达","URL");
                for (int i = 0; i < 5; i++) {
                    comment = new Comment();
                    comment.setUser(user);
                    comment.setTextContent("今天获得了替身，照相机粉碎者！");
                    comment.setLikeNum(666);
                    testList.add(comment);
                }
                dataSet.add(testList);

                quickAdapter.addData(testList);//放入新评论
                break;
            case R.id.headprotrait:
            case R.id.community1_playername_btn:
                Toast.makeText(activity_sharedetail.this, "进入个人主页", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}