package com.example.myapplication;

import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.entity.MultipleItem;


import org.jetbrains.annotations.NotNull;

import java.util.List;


public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> implements UpFetchModule, LoadMoreModule{

    public MultipleItemQuickAdapter(List data) {
        super(data);
        addItemType(MultipleItem.TEXTONLY, R.layout.item_textonly);
        addItemType(MultipleItem.BUTTON, R.layout.item_buttononly);
        addItemType(MultipleItem.MINICOURSE, R.layout.item_course_mini);
        addItemType(MultipleItem.MASONRYPOST, R.layout.item_post_simplified);
        addItemType(MultipleItem.USER, R.layout.item_user_result);
        addItemType(MultipleItem.SHARE, R.layout.item_post_full);
        addItemType(MultipleItem.ACTION, R.layout.item_course_movement);
        addItemType(MultipleItem.ADDIMAGE,R.layout.item_photo);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {

        switch (helper.getItemViewType()) {
            case MultipleItem.TEXTONLY:
                helper.setText(R.id.textViewOnly, item.getText());
                break;
            case MultipleItem.BUTTON:
                helper.setText(R.id.buttonOnly, item.getText());
                break;
            /*case MultipleItem.MINICOURSE:
                helper.setImageResource(R.id.masonry_item_post_img, item.getCourse().getBackgroundUrl())
                        .setText(R.id.tv_title, item.getData().getTitle())
                        .setText(R.id.tv_content, item.getData().getContent());
                break;*/

            case MultipleItem.MASONRYPOST:
                helper.setText(R.id.masonry_item_textContent, item.getShare().getContents())
                        .setText(R.id.masonry_item_username, item.getShare().getUser().getNickname())
                        .setText(R.id.masonry_item_num,String.valueOf(item.getShare().getLikeNumbers()));
                Glide.with(getContext()).load(item.getShare().getUser().getHeadPortraitUrl()).into((ImageView) helper.getView(R.id.masonry_item_portrait_img));
                Glide.with(getContext()).load(item.getShare().getImgUrls()).into((ImageView) helper.getView(R.id.masonry_item_post_img));

                break;
            case MultipleItem.USER:
                helper.setText(R.id.user_id, item.getUser().getNickname());
                Glide.with(getContext()).load(item.getUser().getHeadPortraitUrl()).into((ImageView) helper.getView(R.id.user_head));
                    //.setImageResource(R.id.user_head, R.mipmap.ic_launcher);// item.getUser().getHeadPortraitUrl()
            break;
            case MultipleItem.SHARE:
                helper.setText(R.id.users_id, item.getShare().getUser().getNickname())
                        .setText(R.id.contents, item.getShare().getContents())
                        .setText(R.id.praises, item.getShare().getLikeNumbers())
                        .setText(R.id.comments,item.getShare().getCommentsNumbers());
                if(item.getShare().getRelations()==0||item.getShare().getRelations()==2)helper.setText(R.id.share_follow,"关注");
                else  helper.setText(R.id.share_follow ,"已关注");
                if(item.getShare().isLike())helper.setImageResource(R.id.postlike,R.drawable.like_click);
                else helper.setImageResource(R.id.postlike,R.drawable.like);

                Glide.with(getContext()).load(item.getShare().getUser().getHeadPortraitUrl()).into((ImageView) helper.getView(R.id.share_users_head));
                Glide.with(getContext()).load(item.getShare().getImgUrls()).into((ImageView) helper.getView(R.id.content_pics));
                        //.setImageResource(R.id.share_users_head, R.drawable.sucai)// item.getShare().getUser().getHeadPortraitUrl()
                        //.setImageResource(R.id.content_pics, R.drawable.post_img3);//item.getShare().getImgUrls()
                break;

            case MultipleItem.ACTION:
                helper.setText(R.id.item_movement_name, item.getAction().getActionName())
                        .setText(R.id.item_movement_duration, item.getAction().getDuration());
                Glide.with(getContext()).load(item.getAction().getActionImgs()).into((ImageView) helper.getView(R.id.item_movement_img));
                        //.setImageResource(R.id.item_movement_img,R.drawable.course_movement);//item.getAction().getBackgroundUrl()
                break;

            case MultipleItem.ADDIMAGE:
                //helper.setImageResource(R.id.community4_item_image, item.getAddimage().getImgUrl());

        }
    }

}