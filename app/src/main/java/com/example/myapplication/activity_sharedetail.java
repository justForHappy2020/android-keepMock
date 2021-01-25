package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.entity.Comment;
import com.example.myapplication.entity.ShareAbb;
import com.example.myapplication.entity.User;
import com.example.myapplication.utils.HttpUtils;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class activity_sharedetail extends Activity implements View.OnClickListener{

    private List<List> dataSet = new ArrayList<>();
    private List<Comment> mainCommentList = new ArrayList();

    private ShareAbb shareAbb;

    private int TOTAL_PAGES;
    private RecyclerView recyclerView;
    private NineGridImageView<String> nineGridImageView;

    private Button loadMoreComments;
    private ImageView back;
    private RoundedImageView userHeadprotraitImg;
    private TextView userNameText;
    private TextView shareText;
    private TextView likeNumbers;
    private TextView commentsNumber;
    private TextView blikeNumbers;
    private TextView bcommentsNumber;

    //private List<ImageView> shareImgs = new ArrayList<>();
    private List<String> imgUrls = new ArrayList<>();

    private Dialog dialog;
    private BaseQuickAdapter<Comment, BaseViewHolder> quickAdapter;
    private String commentId;//传入帖子ID

    private int httpcode;
    private Boolean hasNext;
    private int currentPage; //要分页查询的页面


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_sharedetail);

        Intent intent = getIntent();
        shareAbb = (ShareAbb)intent.getSerializableExtra("shareAbb");
        dataSet.add(mainCommentList);

        initView();
        initData();

    }

    private void initView() {
        loadMoreComments = findViewById(R.id.sharedetail_loadmore_comments);
        back = findViewById(R.id.community1_leftarrow);
        userHeadprotraitImg=findViewById(R.id.userHeadprotrait);
        userNameText=findViewById(R.id.userNickName);
        shareText=findViewById(R.id.community1_playersay);
        likeNumbers=findViewById(R.id.community1_visitorthu);
        commentsNumber=findViewById(R.id.community1_visitors_commentnum);
        blikeNumbers=findViewById(R.id.community1_thumbsnum);
        bcommentsNumber=findViewById(R.id.community1_newsnum);
/*
        shareImgs.add((ImageView)findViewById(R.id.community1_playerimage1));
        shareImgs.add((ImageView)findViewById(R.id.community1_playerimage2));
        shareImgs.add((ImageView)findViewById(R.id.community1_playerimage3));
        shareImgs.add((ImageView)findViewById(R.id.community1_playerimage4));
        shareImgs.add((ImageView)findViewById(R.id.community1_playerimage5));
        shareImgs.add((ImageView)findViewById(R.id.community1_playerimage6));
        shareImgs.add((ImageView)findViewById(R.id.community1_playerimage7));
        shareImgs.add((ImageView)findViewById(R.id.community1_playerimage8));
        shareImgs.add((ImageView)findViewById(R.id.community1_playerimage9));

 */

        recyclerView = (RecyclerView) findViewById(R.id.community_reviews_main);
        nineGridImageView = findViewById(R.id.sharedetail_nine_grid);

        loadMoreComments.setOnClickListener(this);
        back.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity_sharedetail.this));

        recyclerView.setNestedScrollingEnabled(false);//禁止嵌套滚动，使得recyclerview可以平滑滚动

        quickAdapter =  new BaseQuickAdapter<Comment, BaseViewHolder >(R.layout.item_comment_main,dataSet.get(0)){
            @Override
            protected void convert(BaseViewHolder helper, Comment comment) {
                helper.setText(R.id.item_comment_main_username,comment.getUser().getNickname())
                        .setText(R.id.item_comment_main_text, comment.getTextContent());
                Glide.with(getContext()).load(comment.getUser().getHeadPortraitUrl()).into((ImageView)helper.getView(R.id.item_comment_main_username_headprotrait));
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

        nineGridImageView.setAdapter(new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView,String url) {
                Glide.with(context)
                        .load(url)
                        .into(imageView);
            }
            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List list) {
                super.onItemImageClick(context, index, list);
                final ImageView imageView = new ImageView(getApplicationContext());
                Glide.with(getBaseContext()).load(list.get(index)).into(imageView);

                dialog = new Dialog(getApplicationContext(),R.style.FullActivity);
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(attributes);
                dialog.setContentView(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private void initData() {
        if(shareAbb !=null){
            //userNameText.setText(shareAbb.getUser().getNickname());
            userNameText.setText(shareAbb.getNickName());
            shareText.setText(shareAbb.getContent());

            likeNumbers.setText(shareAbb.getLikeNumbers()+"人点赞");
            blikeNumbers.setText(shareAbb.getLikeNumbers());
            commentsNumber.setText(shareAbb.getCommentNumbers()+"评论");
            bcommentsNumber.setText(shareAbb.getCommentNumbers());

            //Glide.with(this).load(shareAbb.getUser().getHeadPortraitUrl()).into(userHeadprotraitImg);
            Glide.with(this).load(shareAbb.getHeadPortraitUrl()).into(userHeadprotraitImg);

            imgUrls.add(shareAbb.getImgUrls());//测试数据

            nineGridImageView.setImagesData(imgUrls);

/*
            int ImgUrls_length=1;//test data
            for(int n=0;n<9;n++){
                if(n<ImgUrls_length){
                    Glide.with(this).load(shareAbb.getImgUrls()).into(shareImgs.get(n));

                    shareImgs.get(n).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final ImageView imageView = new ImageView(getApplicationContext());
                            Glide.with(getApplicationContext()).load(shareAbb.getImgUrls()).into(imageView);

                            dialog = new Dialog(getApplicationContext(),R.style.FullActivity);
                            WindowManager.LayoutParams attributes = getWindow().getAttributes();
                            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                            attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
                            dialog.getWindow().setAttributes(attributes);
                            dialog.setContentView(imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();

                        }
                    });
                }else{
                    shareImgs.get(n).setVisibility(View.GONE);//setImageResource(R.color.transparent);
                }

            }

 */

        }

        //final String url = "http://159.75.2.94:8080/api/community/getShareDetail";
        //getHttpShareDetail(url);

        /*TestData*/

        Comment comment;
        User user = new User("乔瑟夫·乔斯达","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3392663359,4194879068&fm=26&gp=0.jpg");
        for (int i = 0; i < 10; i++) {
            comment = new Comment();
            comment.setUser(user);
            comment.setTextContent("评论"+i+": 昨天学习了波纹疾走，今天感觉肌肉力量增强了！");
            comment.setLikeNum(233+i);
            mainCommentList.add(comment);
        }
        dataSet.add(mainCommentList);
    }

    private void getHttpShareDetail(final String url) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String responseData = null;

                JSONObject jsonObject1 = null;
                try {
                    responseData = HttpUtils.connectHttpGet(url);

                    Log.d("ShareDetail_Json:",responseData);
                    //jsonObject1 = new JSONObject(responseData);
                    httpcode = jsonObject1.getInt("code");
                    if (httpcode == 200) {
                        //JSONObject jsonObject2 = jsonObject1.getJSONObject("data");
                        //hasNext = jsonObject2.getBoolean("hasNext");
                        //TOTAL_PAGES = jsonObject2.getInt("totalPages");
                        //得到筛选的课程list
                        //JSONArray JSONArrayShare = jsonObject2.getJSONArray("shareList");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (httpcode != 200) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
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
                List<Comment> newCommentList = new ArrayList();

                Comment comment;
                User user = new User("乔瑟夫·乔斯达","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3392663359,4194879068&fm=26&gp=0.jpg");
                for (int i = 10; i < 21; i++) {
                    comment = new Comment();
                    comment.setUser(user);
                    comment.setTextContent("评论"+i+": 昨天学习了波纹疾走，今天感觉肌肉力量增强了！");
                    comment.setLikeNum(23+i);
                    newCommentList.add(comment);
                }
                dataSet.add(newCommentList);

                quickAdapter.addData(newCommentList);//放入新评论
                quickAdapter.notifyDataSetChanged();
                break;
            case R.id.userHeadprotrait:
            case R.id.userNickName:
                Toast.makeText(activity_sharedetail.this, "进入个人主页", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}