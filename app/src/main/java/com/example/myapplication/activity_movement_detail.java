package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.utils.HtmlTagHandler;
import com.sendtion.xrichtext.RichTextView;
import com.sendtion.xrichtext.XRichText;

import java.net.URL;

public class activity_movement_detail extends Activity implements View.OnClickListener {

    ImageView lastMovement;
    ImageView nextMovement;
    Button startMovement;
    TextView contentTextView;
    RichTextView richContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_detail);

        initView();
    }

    private void initView(){
        lastMovement=findViewById(R.id.movement_detail_last);
        nextMovement=findViewById(R.id.movement_detail_next);
        startMovement=findViewById(R.id.movement_detail_start);
        contentTextView=findViewById(R.id.movement_detail_content);
        richContentTextView=findViewById(R.id.movement_detail_content_rich);

        lastMovement.setOnClickListener(this);
        nextMovement.setOnClickListener(this);
        startMovement.setOnClickListener(this);

        /**
         * TextView Test
         * 要点  ·双手撑地，前脚掌着地，身体与大腿夹角呈90°，膝关节夹角呈90°  ·膝盖悬空，收紧腹部，保持背部平直
         * 呼吸  ·自然呼吸
         * 动作感觉  ·整个腹部有强烈的收缩紧绷感
         */
        String text = "<font color='black' size='18px'><b><big>要点</big></b><br></font>"+"<font color='black' size='18px'>· 双手撑地，前脚掌着地，身体与大腿夹角呈90°<br>· 膝关节夹角呈90°</font><br>";

        String text1 = "<p><font color='black' size='18px'><b><big>呼吸</big></b><br></font>"+"<font color='black' size='18px'>· 自然呼吸</font><br>";

        String text2 = "<p><font color='black' size='18px'><b><big>动作感觉</big></b><br></font>"+"<font color='black' size='18px'>· 整个腹部有强烈的收缩紧绷感</font>";

        String img = "<p><img src=\"https://s3.ax1x.com/2020/12/07/Dza9Ig.jpg\"/>";//暂时无法显示

        Spanned spanned = Html.fromHtml(text+text1+text2+img);

        contentTextView.setText(spanned);
/*
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        int densityDPI = dm.densityDpi;//get ppi

 */

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.movement_detail_last:
                break;
            case R.id.movement_detail_next:
                break;
            case R.id.movement_detail_start:
                Intent intent = new Intent(this,play.class);
                intent.putExtra("courseMovementId","Id");
                startActivity(intent);
                break;
        }
    }
}
