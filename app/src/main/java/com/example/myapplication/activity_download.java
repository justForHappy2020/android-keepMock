package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.Runnable.DownloadRunnable;
import com.example.myapplication.Beans.TaskInfo;
import com.example.myapplication.entity.Course;
import com.example.myapplication.utils.VideoCacheUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class activity_download extends Activity implements View.OnClickListener {

    private FrameLayout download_progress;
    private ConstraintLayout dl_constraintLayout;
    private ProgressBar download_progressBar;
    private TextView download_name;
    private TextView download_quit;
    private TextView download_progressText;

    private TaskInfo dlInfo;//任务信息
    private List<TaskInfo> taskList;
    private DownloadRunnable dlRunnable;//下载任务
    private VideoCacheUtil vcu;
    private Course course;

    private String movementVideoUrl;
    private String cacheRootDir;
    private int currentOne;

    final int Mb =1048576;
    final int DOWNLOAD_FAINED = -1;

    /**
     * TestData
     */
    String Url = "http://qkds47aiq.hn-bkt.clouddn.com/%C2%B6%C2%AF%C3%97%C3%B7%C2%B6%C3%BE.mp4";


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case -2:
                    //使用Handler制造一个200毫秒为周期的循环
                    handler.sendEmptyMessageDelayed(-2, 200);
                    //计算下载进度
                    int l = (int) ((float) dlInfo.getCompletedLen() / (float) dlInfo.getContentLen() * 100);

                    //设置进度条进度
                    download_progressBar.setProgress(l);
                    if (l>=100) {//当进度>=100时，取消Handler循环
                        download_progressText.setText("下载完成");
                        handler.removeCallbacksAndMessages(null);

                    }

                    download_progressText.setText("正在下载 "
                            +dlInfo.getCompletedLen()/Mb+"M"
                            +" / "
                            +dlInfo.getContentLen()/Mb+"M");
                    break;
                case DOWNLOAD_FAINED:
                    Toast.makeText(activity_download.this, "下载失败", Toast.LENGTH_LONG).show();
                    break;
                 default:
                     Toast.makeText(activity_download.this, "下载完成", Toast.LENGTH_LONG).show();

                     Intent intent = new Intent(getApplicationContext(),play.class);
                     intent.putExtra("movementVideoLocPath",taskList.get(msg.what).getPath()+taskList.get(msg.what).getName());//vcu.getVideoLocPath(movementVideoUrl)
                     intent.putExtra("currentOne",currentOne);
                     intent.putExtra("course",(Serializable) course);
                     startActivity(intent);
                     finish();
                     break;
            }
            return true;
        }
    });

    @Override
    protected void onDestroy() {
        //在Activity销毁时移除回调和msg，并置空，防止内存泄露
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        vcu.stop();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Intent intent = getIntent();
        movementVideoUrl=intent.getStringExtra("movementVideoUrl");
        cacheRootDir=intent.getStringExtra("cacheRootDir");
        currentOne=intent.getIntExtra("currentOne",0);
        course=(Course)intent.getSerializableExtra("course");

        initView();
        initData();
    }

    private void initView(){
        download_progress = findViewById(R.id.download_progress);
        dl_constraintLayout = findViewById(R.id.dl_constraintLayout);
        download_name = findViewById(R.id.download_name);
        download_quit = findViewById(R.id.download_quit);
        download_progressText = findViewById(R.id.download_progressText);
        download_progressBar = findViewById(R.id.download_progressBar);

        download_quit.setOnClickListener(this);
        download_progressBar.setIndeterminate(false);


    }

    private void initData(){

        download_progressBar.setProgress(0);
        download(movementVideoUrl);
    }


    private void download(String Url){
        Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();

        //实例化任务信息对象
        taskList =  new ArrayList<>();
        dlInfo = new TaskInfo(String.valueOf(currentOne)+".mp4",cacheRootDir,Url);
        taskList.add(dlInfo);

        vcu=new VideoCacheUtil(taskList,cacheRootDir,getApplicationContext(),handler);
        vcu.download();

        //开始Handler循环
        handler.sendEmptyMessageDelayed(-2, 200);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.download_quit:
                //dlRunnable.stop();
                //dlRunnable = null;//强迫症，不用的对象手动置空
                vcu.stop();
                finish();
                break;
            case R.id.download_bgImageView:
                break;

        }
    }
}
