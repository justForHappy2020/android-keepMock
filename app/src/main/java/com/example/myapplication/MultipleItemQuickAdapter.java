package com.example.myapplication;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.entity.MultipleItem;

import java.util.List;

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(List data) {
        super(data);
        addItemType(MultipleItem.TEXTONLY, R.layout.item_textonly);
        addItemType(MultipleItem.BUTTON, R.layout.item_buttononly);
        addItemType(MultipleItem.MINICOURSE, R.layout.item_course_mini);
        addItemType(MultipleItem.MASONRYPOST, R.layout.item_post_simplified);
        addItemType(MultipleItem.USER, R.layout.item_user_result);
        addItemType(MultipleItem.SHARE, R.layout.item_post_full);
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
                helper.setText(R.id.user_id, item.getUser().getTitle())
                    .setImageResource(R.id.user_head, R.mipmap.ic_launcher);
            break;
            case MultipleItem.SHARE:
                helper.setText(R.id.users_id, item.getShare().getUsers_id())
                        .setText(R.id.contents, item.getShare().getContents())
                        .setText(R.id.praises, item.getShare().getPraises())
                        .setText(R.id.comments,item.getShare().getComments())
                        .setImageResource(R.id.users_haed, item.getShare().getUser_heads())
                        .setImageResource(R.id.content_pics, item.getShare().getContent_pics());
                break;
        }
    }

}