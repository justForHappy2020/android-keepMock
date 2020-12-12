package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.myapplication.entity.ActionCom;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class play extends Activity implements View.OnClickListener{

    private int httpcode;
    private String movementVideoLocPath;
    //private String VIDEO_URL = "http://qkds47aiq.hn-bkt.clouddn.com/%C2%B6%C2%AF%C3%97%C3%B7%C2%B6%C3%BE.mp4";
            //"http://qkds47aiq.hn-bkt.clouddn.com/%C2%B6%C2%AF%C3%97%C3%B7%C2%B6%C3%BE.mp4";
            //"http://qkds47aiq.hn-bkt.clouddn.com/%C2%B6%C2%AF%C3%97%C3%B7O%CC%80%C2%BB.mp4";
            //"http://qkds47aiq.hn-bkt.clouddn.com/2333.mp4";

    private VideoView mVideoView;
    private TextView mBufferingTextView;
    private ProgressBar progressBar;
    private ProgressBar progressBar_cir;
    private TextView tvShowTime;
    private TextView tvActionName;
    private ImageButton last;
    private ImageButton stop;
    private ImageButton stop_cir;
    private ImageButton next;
    private ImageButton ibQuit;
    private ImageButton ibKeepTrain;
    private Button detail;
    //private View mPortraitPosition;
    //private View mPortraitContent;
    private ConstraintLayout clRootContainer;
    private FrameLayout bottom_framelayout;
    private FrameLayout framelayout_cir;
    private LinearLayout bottom_linerlayout;

    private int width;// 屏幕宽度（像素）
    private int height;// 屏幕高度（像素）
    private String token;
    private long actionID;
    private ActionCom actionCom;

    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

    //暂停对话框
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private ImageView imageView;
    private View layout;

    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Intent intent = getIntent();
        movementVideoLocPath = intent.getStringExtra("movementVideoLocPath");

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }


        initView();

    }

    private void initView(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）

        SharedPreferences readSP = getSharedPreferences("saved_token",MODE_PRIVATE);
        token = readSP.getString("token","");

        Intent intentAccept = null;
        intentAccept = getIntent();
        actionID = intentAccept.getLongExtra("actionID",0);
        getActionCom();//http获取动作类
        //VIDEO_URL=intentAccept.getStringExtra("courseUrl");

        mVideoView = findViewById(R.id.video_view);
        mBufferingTextView = findViewById(R.id.buffering_text_view);
        progressBar = findViewById(R.id.progressBar);
        //progressBar_cir=findViewById(R.id.progressBar_cir);
        tvShowTime = findViewById(R.id.showTime);
        tvActionName = findViewById(R.id.actionName);
        last = findViewById(R.id.last);
        stop = findViewById(R.id.stop);
        //stop_cir=findViewById(R.id.stop_cir);
        next = findViewById(R.id.next);
        detail = findViewById(R.id.detail);
        clRootContainer = findViewById(R.id.constraintLayout);
        bottom_framelayout=findViewById(R.id.bottom_framelayout);
        //framelayout_cir=findViewById(R.id.framelayout_cir);
        bottom_linerlayout=findViewById(R.id.bottom_linerlayout);

        //mPortraitPosition = findViewById(R.id.main_portrait_position);
        //mPortraitContent = findViewById(R.id.main_portrait_content);

        last.setOnClickListener(this);
        stop.setOnClickListener(this);
        next.setOnClickListener(this);
        detail.setOnClickListener(this);

        //VIDEO_URL = actionCom.getActionUrl();
        //进度条
        tvShowTime.setText(actionCom.getDuration());
        tvActionName.setText(actionCom.getActionName());//还要显示第几个动作，一共几个动作

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        controller.setVisibility(View.GONE);
        mVideoView.setMediaController(controller);
        //使用post是由于会使用mPortraitPosition的宽高信息
        mVideoView.post(new Runnable() {
            @Override
            public void run() {
                onStart();
            }
        });
    }

    private void getActionCom() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/community/actionId2Action?actionId=" + actionID;
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
                    if(httpcode == 200){
                        //得到相应的内容
                        JSONObject jsonObject = jsonObject1.getJSONObject("data");
                            //ActionCom相应的内容
                            actionCom = new ActionCom();
                        actionCom.setActionId(jsonObject.getLong("actionId"));
                        actionCom.setActionName(jsonObject.getString("actionName"));
                        actionCom.setActionImgs(jsonObject.getString("actionImgs"));
                        actionCom.setActionUrl(jsonObject.getString("actionUrl"));
                        actionCom.setDuration(jsonObject.getString("duration"));
                        actionCom.setIntroId(jsonObject.getLong("introId"));
                        actionCom.setActionGif(jsonObject.getString("actionGif"));
                        actionCom.setKeyPoint(jsonObject.getString("keyPoint"));
                        actionCom.setBreath(jsonObject.getString("breath"));
                        actionCom.setFeel(jsonObject.getString("feel"));
                        actionCom.setFellImg(jsonObject.getString("fellImg"));
                        actionCom.setMistake(jsonObject.getString("mistake"));
                        actionCom.setDetail(jsonObject.getString("detail"));
                        actionCom.setDetailImg(jsonObject.getString("detailImg"));
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
        if(httpcode!=200) Toast.makeText(play.this,"ERROR", Toast.LENGTH_SHORT).show();
        httpcode = 0;
    }

    //记录播放记录http
    private void savedPlayRecord(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.16.1:8080/api/course/playTheVideo?token=" + token + "&&courseId=" + actionID;
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
        if(httpcode!=200) Toast.makeText(play.this,"ERROR", Toast.LENGTH_SHORT).show();
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
        Uri videoUri = Uri.parse(movementVideoLocPath);//getMedia(movementVideoLocPath);
        mVideoView.setVideoURI(videoUri);

        //mVideoView.setAutofillId();
        //mVideoView.setClipToOutline(true);

        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        progressBar.setMax(mVideoView.getDuration());
                        if (timer != null) {
                            timer = null;
                            timerTask = null;
                        }
                        timer = new Timer();
                        timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                if (mVideoView != null) {
                                    if (mVideoView.isPlaying()) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressBar.setProgress(mVideoView.getCurrentPosition());
                                                //tvShowTime.setText();
                                            }
                                        });
                                    }
                                }
                            }
                        };
                        timer.schedule(timerTask, 0, 1000);
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
        /*
        framelayout_cir.setVisibility(View.VISIBLE);
        progressBar_cir.setVisibility(View.VISIBLE);
        stop_cir.setVisibility(View.VISIBLE);

        bottom_framelayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        bottom_linerlayout.setVisibility(View.GONE);

         */


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
        /*
        framelayout_cir.setVisibility(View.INVISIBLE);
        progressBar_cir.setVisibility(View.INVISIBLE);
        stop_cir.setVisibility(View.INVISIBLE);

        bottom_framelayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        bottom_linerlayout.setVisibility(View.VISIBLE);

         */

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

    public void viewInit() {

        builder = new AlertDialog.Builder(this);//创建对话框
        inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.hamepage_specificcourses_quitex, null);//获取自定义布局
        builder.setView(layout);//设置对话框的布局
        dialog = builder.create();//生成最终的对话框
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        //window.setWindowAnimations(R.style.dialog_animation);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
       window.setAttributes(lp);
        dialog.show();//显示对话框

        ibQuit = layout.findViewById(R.id.quit);
        ibKeepTrain = layout.findViewById(R.id.keepTrain);

        ibQuit.setOnClickListener(this);
        ibKeepTrain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.last:
                break;
            case R.id.stop:
                mVideoView.pause();
                viewInit();
                break;
            case R.id.next:
                break;
            case R.id.detail:
                break;
            case R.id.quit:
                dialog.dismiss();
                finish();
                break;
            case R.id.keepTrain:
                dialog.dismiss();//关闭对话框
                mVideoView.start();
                break;
        }
    }

}