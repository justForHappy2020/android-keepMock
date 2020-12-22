package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.entity.AddImage;

import java.util.List;

import static com.example.myapplication.R.drawable.add;

/**
 * Created by 16838 on 2018/5/17.
 */
public class MyAdapter extends BaseAdapter {
    private  Det det;
    //  把按钮回调出去
    public void delete(Det det){
        this.det=det;
    }
    public interface  Det{
        public void del(int position);
    }

    List<AddImage> imagesList;
    Context context;
    public MyAdapter(List<AddImage> imagesList, Context context){
        this.imagesList=imagesList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return imagesList.size() + 1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if(convertView==null) {
            viewHoder=new ViewHoder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_photo,null);
            viewHoder.Image= (ImageView) convertView.findViewById(R.id.community4_item_image);
            viewHoder.Delete=(ImageView) convertView.findViewById(R.id.community4_item_cancel);
            //viewHoder.addImage=(ImageView) convertView.findViewById(R.id.community4_AddImage_btn4);
            convertView.setTag(viewHoder);
        }else{
            viewHoder= (ViewHoder) convertView.getTag();
        }
        if (position < imagesList.size()) {
            Glide.with(context).load(imagesList.get(position).getImgUrl()).into(viewHoder.Image);
        } else {
                viewHoder.Delete.setVisibility(View.GONE);
                viewHoder.Image.setImageResource(R.drawable.add);
        }
        viewHoder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(det!=null) {
                    det.del(position);

                }

            }
        });

        return convertView;
    }
    private class ViewHoder{
        ImageView Image;
        ImageView Delete;
        ImageView addImage;
    }
    @Override
    public Object getItem(int position) {
        return imagesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}