package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.entity.MultipleItem;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> implements UpFetchModule, LoadMoreModule{

    public MultipleItemQuickAdapter(List data) {
        super(data);
        addItemType(MultipleItem.TEXTONLY, R.layout.item_textonly);
        addItemType(MultipleItem.BUTTON, R.layout.item_search_all_checkmore_button);
        addItemType(MultipleItem.MINICOURSE, R.layout.item_course_mini);
        addItemType(MultipleItem.NORMCOURSE, R.layout.item_course_simplified);

        addItemType(MultipleItem.MASONRYPOST, R.layout.item_post_simplified);
        addItemType(MultipleItem.USER, R.layout.item_user_result);
        addItemType(MultipleItem.SHAREABB, R.layout.item_post);
        addItemType(MultipleItem.ACTION, R.layout.item_course_movement);
        addItemType(MultipleItem.ADDIMAGE,R.layout.item_photo);
        addItemType(MultipleItem.SHAREFULL,R.layout.item_post_full);

    }

    @Override
    protected void convert(final BaseViewHolder helper, final MultipleItem item) {

        switch (helper.getItemViewType()) {
            case MultipleItem.TEXTONLY:
                helper.setText(R.id.textViewOnly, item.getText());
                break;
            case MultipleItem.BUTTON:
                helper.setText(R.id.buttonOnly, item.getText());
                break;
            case MultipleItem.MINICOURSE:
                helper.setText(R.id.course_name, item.getCourse().getCourseName())
                        .setText(R.id.course_intro, item.getCourse().getCourseIntro());
                Glide.with(getContext()).load(item.getCourse().getBackgroundUrl()).placeholder(R.drawable.ic_placeholder).into((ImageView) helper.getView(R.id.course_img));
                break;

            case MultipleItem.MASONRYPOST:
                helper.setText(R.id.masonry_item_textContent, item.getShareAbb().getContent().replace("\\n", "\t").replace("\\r","\r").replace("\\t","\t"))//缩略展示，将换行替换为空格，以展示更多内容
                        .setText(R.id.masonry_item_username, item.getShareAbb().getNickName())
                        .setText(R.id.masonry_item_num,String.valueOf(item.getShareAbb().getLikeNumbers()));

                Glide.with(getContext())
                        .load(item.getShareAbb().getHeadPortraitUrl())
                        .asBitmap()
                        .placeholder(R.drawable.headprotrait)
                        .error(R.drawable.ic_load_pic_error)
                        .into((RoundedImageView) helper.getView(R.id.masonry_item_portrait_img));

                helper.getView(R.id.masonry_item_post_img).setTag(R.id.masonry_item_post_img,item.getShareAbb().getImgUrls());
                Glide.with(getContext())
                        .load(item.getShareAbb().getImgUrls())
                        .error(R.drawable.ic_load_pic_error)
                        // 取消动画，防止第一次加载不出来
                        .dontAnimate()
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource,GlideAnimation<? super GlideDrawable> glideAnimation) {
                                String tag = (String) helper.getView(R.id.masonry_item_post_img).getTag(R.id.masonry_item_post_img);
                                // 如果一样，显示图片
                                if (TextUtils.equals(item.getShareAbb().getImgUrls(), tag)) {
                                    helper.setImageDrawable(R.id.masonry_item_post_img,resource);
                                }
                            }
                        });

                break;
            case MultipleItem.USER:
                helper.setText(R.id.user_id, item.getUser().getNickname());
                Glide.with(getContext())
                        .load(item.getUser().getHeadPortraitUrl())
                        .placeholder(R.drawable.headprotrait)
                        .error(R.drawable.ic_load_pic_error)
                        .transform(new CenterCrop(getContext()), new GlideRoundTransform(getContext(),15))
                        .into((ImageView) helper.getView(R.id.user_head));
            break;
            case MultipleItem.SHAREABB:
                helper.setText(R.id.users_id, item.getShareAbb().getNickName())
                        .setText(R.id.content, item.getShareAbb().getContent())
                        .setText(R.id.praises, item.getShareAbb().getLikeNumbers())
                        .setText(R.id.comments,item.getShareAbb().getCommentNumbers());
                if(item.getShareAbb().getRelations()==0||item.getShareAbb().getRelations()==2)helper.setText(R.id.share_follow,"关注");
                else{
                    helper.setText(R.id.share_follow ,"已关注");
                    helper.setBackgroundResource(R.id.share_follow,R.drawable.button_radius_green_stroke);
                }
                if(item.getShareAbb().isLike())helper.setImageResource(R.id.postlike,R.drawable.like_click);
                else helper.setImageResource(R.id.postlike,R.drawable.like);

                Glide.with(getContext())
                        .load(item.getShareAbb().getHeadPortraitUrl())
                        .asBitmap()
                        .placeholder(R.drawable.headprotrait)
                        .error(R.drawable.ic_load_pic_error)
                        .into((RoundedImageView)helper.getView(R.id.share_users_head));
                Glide.with(getContext()).load(item.getShareAbb().getImgUrls()).placeholder(R.drawable.ic_placeholder).into((ImageView) helper.getView(R.id.content_pics));
                break;

            case MultipleItem.ACTION:
                helper.setText(R.id.item_movement_name, item.getAction().getActionName())
                        .setText(R.id.item_movement_duration, item.getAction().getDuration());
                Glide.with(getContext()).load(item.getAction().getActionImgs()).placeholder(R.drawable.ic_placeholder).into((ImageView) helper.getView(R.id.item_movement_img));
                        //.setImageResource(R.id.item_movement_img,R.drawable.course_movement);//item.getAction().getBackgroundUrl()
                break;

            case MultipleItem.ADDIMAGE:
                //helper.setImageResource(R.id.community4_item_image, item.getAddimage().getImgUrl());
                break;
            case MultipleItem.NORMCOURSE:
                String item_text = item.getCourse().getDegree() + "·" + item.getCourse().getDuration() + "·" +item.getCourse().getHits() + "万人已参加";//后期需要完善
                helper.setText(R.id.course_item_norm_name, item.getCourse().getCourseName())
                        .setText(R.id.course_item_norm_text, item_text);
                Glide.with(getContext())
                        .load(item.getCourse().getBackgroundUrl())
                        .error(R.drawable.ic_load_pic_error)
                        .transform(new CenterCrop(getContext()), new GlideRoundTransform(getContext(),15))
                        .into((ImageView) helper.getView(R.id.course_item_norm_bgImg));
                break;
            case MultipleItem.SHAREFULL:
                helper.setText(R.id.item_post_full_username, item.getShareAbb().getNickName())
                        .setText(R.id.item_post_full_text, item.getShareAbb().getContent())
                        .setText(R.id.item_post_full_likenumber, item.getShareAbb().getLikeNumbers())
                        .setText(R.id.item_post_full_commentnumber,item.getShareAbb().getCommentNumbers());
                if(item.getShareAbb().isLike())helper.setImageResource(R.id.item_post_full_like,R.drawable.like_click);
                else helper.setImageResource(R.id.item_post_full_like,R.drawable.like);

                Glide.with(getContext())
                        .load(item.getShareAbb().getHeadPortraitUrl())
                        .asBitmap()
                        .placeholder(R.drawable.headprotrait)
                        .error(R.drawable.ic_load_pic_error)
                        .into((RoundedImageView)helper.getView(R.id.item_post_full_userhead));
                NineGridImageView<String> nineGridImageView = helper.getView(R.id.item_post_full_nine_grid);
                nineGridImageView.setAdapter(item.getNineGridAdapter());
                List<String> imgUrls = new ArrayList<>();
                imgUrls.add(item.getShareAbb().getImgUrls());
                imgUrls.add(item.getShareAbb().getImgUrls());
                nineGridImageView.setImagesData(imgUrls);//后期需要改接口返回图片为list
                break;
        }
    }

}