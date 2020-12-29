package com.example.myapplication.Beans;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownImageTask extends AsyncTask<String, Void, Drawable> {

    private ImageView imageView;

    public DownImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Drawable doInBackground(String... params) {
        return loadImageFromNetwork(params[0]);
    }

    @Override

    protected void onPostExecute(Drawable result) {
        imageView.setImageDrawable(result);
    }


    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }

        return drawable ;
    }
}