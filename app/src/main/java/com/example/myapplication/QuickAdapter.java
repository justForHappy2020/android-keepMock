package com.example.myapplication;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.myapplication.entity.Course;

import java.util.List;

public class QuickAdapter extends BaseQuickAdapter<Course, BaseViewHolder> implements UpFetchModule, LoadMoreModule {

    public QuickAdapter(@LayoutRes int layoutResId, @Nullable List<Course> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Course item) {
        //可链式调用赋值
        String item_text = item.getDegree() + " . " + item.getDuration() + " . " +item.getHits() + "万人已参加";
        helper.setText(R.id.course_item_name, item.getCourseName())

                .setText(R.id.course_item_tag, item.getCourseIntro())
                .setText(R.id.course_item_text, item_text)//后期需要修改为正确的形式
                .setImageResource(R.id.course_item_bgImg, R.drawable.course_background);//item.getBackgroundUrl();

        //获取当前条目position
        //int position = helper.getLayoutPosition();
    }
}