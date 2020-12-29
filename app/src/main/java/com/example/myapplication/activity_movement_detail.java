package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.example.myapplication.entity.Course;
import com.example.myapplication.utils.VideoCacheUtil;
import com.sendtion.xrichtext.RichTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class activity_movement_detail extends Activity implements View.OnClickListener {

    int currentOne=0;

    private ImageView lastMovement;
    private ImageView nextMovement;
    private Button startMovement;
    private TextView movementNameTextView;
    private TextView contentTextView;
    private RichTextView richContentTextView;//富文本TextView，暂未能使用

    private Course course;
    private List<String> introList;

    /**
     * testData
     */
    private String actionVideoUrl = "http://qkds47aiq.hn-bkt.clouddn.com/%E5%8A%A8%E4%BD%9C%E4%B8%80%E6%B5%8B%E8%AF%95%E7%89%88%EF%BC%88%E6%9C%AA%E5%8C%85%E8%A3%85%EF%BC%89.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_detail);

        Intent intent = getIntent();
        course=(Course)intent.getSerializableExtra("course");
        currentOne=intent.getIntExtra("courseActionPosition",0);

        initData();
        initView();
    }

    private void initView(){
        movementNameTextView=findViewById(R.id.movement_detail_name);
        lastMovement=findViewById(R.id.movement_detail_last);
        nextMovement=findViewById(R.id.movement_detail_next);
        startMovement=findViewById(R.id.movement_detail_start);
        contentTextView=findViewById(R.id.movement_detail_content);
        richContentTextView=findViewById(R.id.movement_detail_content_rich);

        lastMovement.setOnClickListener(this);
        nextMovement.setOnClickListener(this);
        startMovement.setOnClickListener(this);

        changeContent(currentOne);

    }

    private void changeContent(int position){

        movementNameTextView.setText(course.getActionList().get(position).getActionName());

        Spanned spanned = HtmlCompat.fromHtml(introList.get(position),0);
        contentTextView.setText(spanned);
    }

    private void initData(){

        final String text = "<font color='black' size='18px'><b><big>要点</big></b><br></font>"+"<font color='black' size='18px'>· 双手撑地，前脚掌着地，身体与大腿夹角呈90°<br>· 膝关节夹角呈90°</font><br>";
        final String text1 = "<p><font color='black' size='18px'><b><big>呼吸</big></b><br></font>"+"<font color='black' size='18px'>· 自然呼吸</font><br>";
        final String text2 = "<p><font color='black' size='18px'><b><big>动作感觉</big></b><br></font>"+"<font color='black' size='18px'>· 整个腹部有强烈的收缩紧绷感</font>";
        final String img = "<p><img src=\"https://s3.ax1x.com/2020/12/07/Dza9Ig.jpg\"/>";//暂时无法显示

        final String text11 = "<font color='black' size='18px'><b><big>步骤</big></b><br></font>"+"<font color='black' size='18px'>· 仰卧在瑜伽垫上，下背部用力贴紧地面，双腿伸直，勾起脚尖<br>· 双腿交替在与地面呈45°角和70°角的区间内抬起落下</font><br>";
        final String text22 = "<p><font color='black' size='18px'><b><big>呼吸</big></b><br></font>"+"<font color='black' size='18px'>· 全程保持均匀呼吸</font><br>";
        final String text33 = "<p><font color='black' size='18px'><b><big>动作感觉</big></b><br></font>"+"<font color='black' size='18px'>· 整个腹肌始终保持紧绷感，动作持续越久，腹肌灼烧感越强</font>";
        final String img1 = "<p><img src=\"https://s3.ax1x.com/2020/12/07/Dza9Ig.jpg\"/>";//暂时无法显示

        course.getActionList().get(0).setIntro(text+text1+text2+img);//testdata,后面需要改成发请求拿数据
        course.getActionList().get(1).setIntro(text11+text22+text33+img1);
        course.getActionList().get(2).setIntro(text+text22+text2+img);

        introList = new ArrayList<>();//把每一个action的intro内容装入introList

        for(int j=0;j<course.getActionList().size();j++){
            introList.add(course.getActionList().get(j).getIntro());
        }

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.movement_detail_last:
                if(currentOne>0){
                    changeContent(--currentOne);
                }
                break;
            case R.id.movement_detail_next:
                if(currentOne<course.getActionList().size()-1){
                    changeContent(++currentOne);
                }
                break;
            case R.id.movement_detail_start:
                Intent intent;
                String rootDir = this.getCacheDir().getPath() + "/videoCache/"+course.getCourseId()+"/";

                VideoCacheUtil vcu = new VideoCacheUtil(rootDir,getApplicationContext());
                if(vcu.isExistInLocal(actionVideoUrl)){
                    intent = new Intent(this,play.class);
                    intent.putExtra("movementVideoLocPath",vcu.getVideoLocPath(actionVideoUrl));
                    Log.d("movementVideoLocPath",vcu.getVideoLocPath(actionVideoUrl));
                }else{
                    intent = new Intent(this,activity_download.class);
                    intent.putExtra("movementVideoUrl",actionVideoUrl);
                    intent.putExtra("cacheRootDir",rootDir);
                }
                intent.putExtra("currentOne",currentOne);
                intent.putExtra("course",(Serializable) course);
                startActivity(intent);
                break;
        }
    }
}
