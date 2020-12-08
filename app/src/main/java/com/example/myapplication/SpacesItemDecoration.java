package com.example.myapplication;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 类名：SpacesItemDecoration
     * 功能：RecyclerView展示Item时添加空隙
     * 构造函数参数：int space //间隔大小
     */
    private int space;
    public SpacesItemDecoration(int space) {
        this.space=space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;

        /*
        if(parent.getChildAdapterPosition(view)<=1){
            outRect.top=space;
        }

        */

    }
}
