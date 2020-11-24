package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entity.Post;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView>{

    private List<Post> posts;


    public MasonryAdapter(List<Post> list) {
        posts=list;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, int position) {
        masonryView.postImg.setImageResource(posts.get(position).getPostImg());
        masonryView.roundImgView.setImageResource(posts.get(position).getPortraitImg());

        masonryView.title.setText(posts.get(position).getTitle());
        masonryView.textContent.setText(posts.get(position).getTextContent());
        masonryView.userName.setText(posts.get(position).getUserName());
        masonryView.notificationNum.setText(posts.get(position).getNotificationNum());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class MasonryView extends  RecyclerView.ViewHolder{
        ImageView postImg;
        RoundedImageView roundImgView;
        TextView title;
        TextView textContent;
        TextView userName;
        TextView notificationNum;

        public MasonryView(View itemView){
            super(itemView);
            postImg = (ImageView) itemView.findViewById(R.id.masonry_item_post_img );
            roundImgView = (RoundedImageView) itemView.findViewById(R.id.masonry_item_portrait_img );
            title = (TextView) itemView.findViewById(R.id.masonry_item_title);
            textContent = (TextView) itemView.findViewById(R.id.masonry_item_textContent);
            userName = (TextView) itemView.findViewById(R.id.masonry_item_username);
            notificationNum = (TextView) itemView.findViewById(R.id.masonry_item_num);
        }
    }
}