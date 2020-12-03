package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


public class play extends Activity {

    private int httpcode;
    private String VIDEO_URL =
            "http://qkds47aiq.hn-bkt.clouddn.com/2333.mp4";
    private VideoView mVideoView;
    private TextView mBufferingTextView;
    private View mPortraitPosition;
    private View mPortraitContent;
    private ConstraintLayout clRootContainer;
    private int width;// 屏幕宽度（像素）
    private int height;// 屏幕高度（像素）

    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        SharedPreferences readSP = getSharedPreferences("saved_token",MODE_PRIVATE);
        final String token = readSP.getString("token","");
        Intent intentAccept = null;
        intentAccept = getIntent();
        //VIDEO_URL=intentAccept.getStringExtra("courseUrl");
        final Long courseId = intentAccept.getLongExtra("courseID",0);

        //记录播放记录http
/*        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.16.1:8080/api/course/playTheVideo?token=" + token + "&&courseId=" + courseId.toString().trim();
                String responseData = null;
                try {
                    responseData = HttpUtils.connectHttpGet(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject1 = null;
                try {
                    jsonObject1 = new JSONObject(responseData);
                    httpcode = jsonObject1.getInt("code");
                    if (httpcode == 200) {
                        Log.d("test", "successful");
                    }
                } catch (JSONException e) {
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
        if(httpcode!=200) Toast.makeText(play.this,"ERROR", Toast.LENGTH_SHORT).show();*/

        mVideoView = findViewById(R.id.video_view);
        mBufferingTextView = findViewById(R.id.buffering_text_view);
        clRootContainer = findViewById(R.id.constraintLayout);
        mPortraitPosition = findViewById(R.id.main_portrait_position);
        mPortraitContent = findViewById(R.id.main_portrait_content);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);
        //使用post是由于会使用mPortraitPosition的宽高信息
        mVideoView.post(new Runnable() {
            @Override
            public void run() {
                onStart();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Load the media each time onStart() is called.
        initializePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // In Android versions less than N (7.0, API 24), onPause() is the
        // end of the visual lifecycle of the app.  Pausing the video here
        // prevents the sound from continuing to play even after the app
        // disappears.
        //
        // This is not a problem for more recent versions of Android because
        // onStop() is now the end of the visual lifecycle, and that is where
        // most of the app teardown should take place.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }

    private void initializePlayer() {
        // Show the "Buffering..." message while the video loads.
        mBufferingTextView.setVisibility(VideoView.VISIBLE);

        // Buffer and decode the video sample.
        Uri videoUri = getMedia(VIDEO_URL);
        mVideoView.setVideoURI(videoUri);

        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        // Hide buffering message.
                        mBufferingTextView.setVisibility(VideoView.INVISIBLE);

                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            mVideoView.seekTo(1);
                        }

                        //改变视频的大小和位置
                        //setVideoViewPosition();
                        changeToPortraitLayout();
                        // Start playing!
                        mVideoView.start();
                    }
                });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(play.this,
                                R.string.toast_message,
                                Toast.LENGTH_SHORT).show();

                        // Return the video position to the start.
                        mVideoView.seekTo(0);
                    }
                });
    }

    private void setVideoViewPosition() {
        //判断当前横竖屏配置
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE: {//横屏
                changeToLandscapeLayout();
                break;
            }
            case Configuration.ORIENTATION_PORTRAIT:{
                changeToPortraitLayout();
                break;
            }
            default: {//竖屏
                changeToPortraitLayout();
                break;
            }
        }

    }

    /**
     * 屏幕方向改变时被调用
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setVideoViewPosition();
        super.onConfigurationChanged(newConfig);
    }
 /*   @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int mCurrentOrientation = getResources().getConfiguration().orientation;
        //竖屏
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            changeToPortraitLayout();
        } else {
            //横屏
            changeToLandscapeLayout();
        }
    }*/

    //横屏
    private void changeToLandscapeLayout() {
        ConstraintSet cs = new ConstraintSet();
        //获取当前目标控件的约束集合
        cs.clone(this, R.layout.activity_video_play);

        //修改mVideoView约束
        //清除约束
        cs.clear(mVideoView.getId());
        cs.clear(mBufferingTextView.getId());
        cs.connect(mVideoView.getId(), ConstraintSet.LEFT, clRootContainer.getId(), ConstraintSet.LEFT);
        cs.connect(mVideoView.getId(), ConstraintSet.TOP, clRootContainer.getId(), ConstraintSet.TOP);
        cs.connect(mVideoView.getId(), ConstraintSet.RIGHT, clRootContainer.getId(), ConstraintSet.RIGHT);
        cs.connect(mVideoView.getId(), ConstraintSet.BOTTOM, clRootContainer.getId(), ConstraintSet.BOTTOM);
        //cs.constrainHeight(mVideoView.getId(), mPortraitPosition.getHeight());
        cs.constrainWidth(mVideoView.getId(), height);
        cs.setDimensionRatio(mVideoView.getId(),"1");

        //将修改过的约束重新应用到ConstrainLayout
        cs.applyTo(clRootContainer);

      /*  ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                mPortraitPosition.getHeight(), mPortraitPosition.getHeight());
        mVideoView.setLayoutParams(params);//设置VideoView的布局参数*/
    }

    //竖屏
    private void changeToPortraitLayout() {
        ConstraintSet cs = new ConstraintSet();
        //获取当前目标控件的约束集合
        cs.clone(this, R.layout.activity_video_play);

        //修改mVideoView约束
        //清除约束
        cs.clear(mVideoView.getId());
        cs.clear(mBufferingTextView.getId());
        cs.connect(mVideoView.getId(), ConstraintSet.LEFT, clRootContainer.getId(), ConstraintSet.LEFT);
        cs.connect(mVideoView.getId(), ConstraintSet.TOP, clRootContainer.getId(), ConstraintSet.TOP);
        cs.connect(mVideoView.getId(), ConstraintSet.RIGHT, clRootContainer.getId(), ConstraintSet.RIGHT);
        cs.connect(mVideoView.getId(), ConstraintSet.BOTTOM, clRootContainer.getId(), ConstraintSet.BOTTOM);
        cs.constrainHeight(mVideoView.getId(), height);
        cs.setDimensionRatio(mVideoView.getId(),"1");

        //将修改过的约束重新应用到ConstrainLayout
        cs.applyTo(clRootContainer);
    }


    // Release all media-related resources. In a more complicated app this
    // might involve unregistering listeners or releasing audio focus.
    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    // Get a Uri for the media sample regardless of whether that sample is
    // embedded in the app resources or available on the internet.
    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {

            // you can also put a video file in raw package and get file from there as shown below

            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + mediaName);


        }
    }

}