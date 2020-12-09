package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.sendtion.xrichtext.RichTextView;

import java.util.ArrayList;
import java.util.List;

public class activity_movement_detail extends Activity implements View.OnClickListener {

    ImageView lastMovement;
    ImageView nextMovement;
    Button startMovement;
    TextView movementNameTextView;
    TextView contentTextView;
    RichTextView richContentTextView;
    private Long actionID;

    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_detail);

        Intent intent = getIntent();
        actionID=intent.getLongExtra("actionID",0);

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

        changeContent(0);


        //contentTextView.setText(spanned);
/*
        contentTextView.post(new Thread(new Runnable() {
            @Override
            public void run() {
                Html.ImageGetter imgGetter = new Html.ImageGetter() {
                    public Drawable getDrawable(String source) {
                        Drawable drawable = null;
                        URL url;
                        try {
                            url = new URL(source);
                            drawable = Drawable.createFromStream(url.openStream(), "");;//new NetImageUtils().loadImageFromNetwork(source);// 获取网路图片
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                                drawable.getIntrinsicHeight());
                        Log.i("RG", "url---?>>>" + url);
                        return drawable;
                    }
                };
                Spanned spanned = HtmlCompat.fromHtml(text+text1+text2+img,0,imgGetter,null);//Html.fromHtml(text+text1+text2+img);
                contentTextView.setText(spanned);
            }
        }));
        new Thread(){
            @Override
            public void run() {

            }
        }.start();

 */

/*
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        int densityDPI = dm.densityDpi;//get ppi

 */

    }

    private void changeContent(int p){

        movementNameTextView.setText("下一个动作名称");
        /**
         * TextView Test
         * 步骤  ·双手撑地，前脚掌着地，身体与大腿夹角呈90°，膝关节夹角呈90°  ·膝盖悬空，收紧腹部，保持背部平直
         * 呼吸  ·自然呼吸
         * 动作感觉  ·整个腹部有强烈的收缩紧绷感
         */


        Spanned spanned = HtmlCompat.fromHtml(data.get(p),0);
        contentTextView.setText(spanned);
    }

    private void initData(){
        data = new ArrayList<>();

        final String text = "<font color='black' size='18px'><b><big>要点</big></b><br></font>"+"<font color='black' size='18px'>· 双手撑地，前脚掌着地，身体与大腿夹角呈90°<br>· 膝关节夹角呈90°</font><br>";
        final String text1 = "<p><font color='black' size='18px'><b><big>呼吸</big></b><br></font>"+"<font color='black' size='18px'>· 自然呼吸</font><br>";
        final String text2 = "<p><font color='black' size='18px'><b><big>动作感觉</big></b><br></font>"+"<font color='black' size='18px'>· 整个腹部有强烈的收缩紧绷感</font>";
        final String img = "<p><img src=\"https://s3.ax1x.com/2020/12/07/Dza9Ig.jpg\"/>";//暂时无法显示

        final String text11 = "<font color='black' size='18px'><b><big>步骤</big></b><br></font>"+"<font color='black' size='18px'>· 仰卧在瑜伽垫上，下背部用力贴紧地面，双腿伸直，勾起脚尖<br>· 双腿交替在与地面呈45°角和70°角的区间内抬起落下</font><br>";
        final String text22 = "<p><font color='black' size='18px'><b><big>呼吸</big></b><br></font>"+"<font color='black' size='18px'>· 全程保持均匀呼吸</font><br>";
        final String text33 = "<p><font color='black' size='18px'><b><big>动作感觉</big></b><br></font>"+"<font color='black' size='18px'>· 整个腹肌始终保持紧绷感，动作持续越久，腹肌灼烧感越强</font>";
        final String img1 = "<p><img src=\"https://s3.ax1x.com/2020/12/07/Dza9Ig.jpg\"/>";//暂时无法显示

        data.add(text+text1+text2+img);
        data.add(text11+text22+text33+img1);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.movement_detail_last:
                changeContent(0);
                break;
            case R.id.movement_detail_next:
                changeContent(1);
                break;
            case R.id.movement_detail_start:
                Intent intent = new Intent(this,play.class);
                intent.putExtra("courseMovementId","Id");
                startActivity(intent);
                break;
        }
    }
}
