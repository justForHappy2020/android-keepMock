package com.example.myapplication;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Share;

import java.util.List;

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> implements LoadMoreModule {

    public MultipleItemQuickAdapter(List data) {
        super(data);
        addItemType(MultipleItem.TEXTONLY, R.layout.item_textonly);
        addItemType(MultipleItem.BUTTON, R.layout.item_buttononly);
        addItemType(MultipleItem.MINICOURSE, R.layout.item_course_mini);
        addItemType(MultipleItem.MASONRYPOST, R.layout.item_post_simplified);
        addItemType(MultipleItem.USER, R.layout.item_user_result);
        addItemType(MultipleItem.SHARE, R.layout.item_post_full);
        addItemType(MultipleItem.POST,R.layout.item_post_simplified);
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
                helper.setImageResource(R.id.masonry_item_post_img, item.getPost().getPostImg())
                        .setImageResource(R.id.masonry_item_portrait_img,item.getPost().getPortraitImg())
                        .setText(R.id.masonry_item_title, item.getPost().getTitle())
                        .setText(R.id.masonry_item_textContent, item.getPost().getTextContent())
                        .setText(R.id.masonry_item_username, item.getPost().getUserName())
                        .setText(R.id.masonry_item_num, item.getPost().getNotificationNum());
                break;
            case MultipleItem.USER:
                helper.setText(R.id.user_id, item.getUser().getNickname())
                        .setImageResource(R.id.user_head,R.drawable.sucai );//item.getUser().getHeadPortraitUrl()
                break;
            case MultipleItem.SHARE:
                helper.setText(R.id.users_id, item.getShare().getNickname())
                        .setText(R.id.contents, item.getShare().getContents())
                        .setText(R.id.praises, item.getShare().getLikeNumbers())
                        .setText(R.id.comments,item.getShare().getCommentsNumbers())
                        .setImageResource(R.id.postlike,R.drawable.like_unclick)
                        .setImageResource(R.id.share_users_head, R.drawable.sucai)//item.getShare().getHeadPortraitUrl()
                        .setImageResource(R.id.content_pics,R.drawable.post_img2);// item.getShare().getImgUrls()
                break;
            case MultipleItem.POST:
                helper.setImageResource(R.id.masonry_item_post_img, item.getPost().getPostImg())
                        .setImageResource(R.id.masonry_item_portrait_img,item.getPost().getPortraitImg())
                        .setText(R.id.masonry_item_title, item.getPost().getTitle())
                        .setText(R.id.masonry_item_textContent, item.getPost().getTextContent())
                        .setText(R.id.masonry_item_username, item.getPost().getUserName())
                        .setText(R.id.masonry_item_num, item.getPost().getNotificationNum())
                        .setImageResource(R.id.masonry_item_post_img,R.drawable.scenery);
                break;
        }
    }

}