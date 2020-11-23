package com.example.myapplication;
import com.example.myapplication.utils.HttpUtils;
import com.example.myapplication.utils.KeyboardUtils;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.sax.TemplatesHandler;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.myapplication.utils.HttpUtils.connectHttp;

public class register2 extends AppCompatActivity implements View.OnClickListener{

    private TextView tvGetPhoneNum;
    private EditText etVcode;
    private Button btNameNext;
    private TextView tvRequireAgain;
    private Intent intentAccept;
    private SharedPreferences saveSP;

    private Long userId;
    private String token;
    private Boolean isNewUser;
    private String message;
    private int httpcode;
/*    private SharedPreferences readSP;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        initView();
        initData();

        //设置延迟并弹出小键盘
        etVcode.setFocusable(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etVcode,InputMethodManager.SHOW_FORCED);
                etVcode.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus) KeyboardUtils.showKeyboard(etVcode);
                        else KeyboardUtils.hideKeyboard(etVcode);
                    }
                });
            }
        },998);
    }

    private void initData(){
/*        readSP = getSharedPreferences("saved_mobile",MODE_PRIVATE);
        String phoneNum = readSP.getString("mobile","");*/
        intentAccept = getIntent();
        String phoneNum = intentAccept.getStringExtra("mobile");

        if(phoneNum.equals("")) Toast.makeText(this,"没有手机号数据",Toast.LENGTH_SHORT).show();
        else tvGetPhoneNum.setText("+86-"+phoneNum);
    }

    private void initView(){
        tvGetPhoneNum = findViewById(R.id.getphone_num);
        etVcode = findViewById(R.id.vcode);
        btNameNext = findViewById(R.id.name_next);
        tvRequireAgain = findViewById(R.id.require_again);

        saveSP = getSharedPreferences("saved_token",Context.MODE_PRIVATE);

/*        //弹出小键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘*/

        btNameNext.setOnClickListener(this);
        tvRequireAgain.setOnClickListener(this);

      /*  etVcode.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)KeyboardUtils.showKeyboard(etVcode);
                else KeyboardUtils.hideKeyboard(etVcode);
            }
        });*/
        //tvRequireAgain.setEnabled(Boolean.FALSE);

    }

    public void onFocusChange(View v,boolean hasFocus){

    }
    public void onClick(View view){
        final Intent intent = new Intent(this, register3.class);
        final Intent intent2 = new Intent(this, exercise_main.class);
        final String mobile = intentAccept.getStringExtra("mobile");
        switch (view.getId()){
            case R.id.require_again:
                tvRequireAgain.setEnabled(Boolean.FALSE);
                intentAccept = getIntent();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //设置JSON数据
                            JSONObject json = new JSONObject();
                            try {
                                json.put("phoneNumber", mobile);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String url = "http://192.168.16.1:8080/api/user/getCode";
                            String responseData = connectHttp(url,json);
                            getfeedback(responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    private void getfeedback(String responseData) {
                        try {
                            //解析JSON数据
                            JSONObject jsonObject1 = new JSONObject(responseData);
                            httpcode = jsonObject1.getInt("code");
/*                        JSONArray jsonArray = jsonObject1.getJSONArray("codes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //相应的内容
                            String message = jsonObject.getString("message");
                            int code = jsonObject.getInt("code");
                        }*/
                        } catch (JSONException e){
                            Toast.makeText(register2.this,"ERROR",Toast.LENGTH_SHORT).show();
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
                if(httpcode==200)Toast.makeText(this,"验证码已重新发送",Toast.LENGTH_SHORT).show();
                if(httpcode!=200)Toast.makeText(register2.this,"手机号有误",Toast.LENGTH_SHORT).show();
                tvRequireAgain.setEnabled(Boolean.TRUE);
                break;
            case R.id.name_next:
                final String code = etVcode.getText().toString().trim();
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //设置JSON数据
                            JSONObject json = new JSONObject();
                            try {
                                json.put("code", code);
                                json.put("phoneNumber",mobile);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String responseData = connectHttp("http://192.168.16.1:8080/api/user/login",json);//okhttp
                            //getfeedback(responseData);
                            try {
                                JSONObject jsonObject = new JSONObject(responseData);
                                message = jsonObject.getString("message");
                                httpcode = jsonObject.getInt("code");
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                    isNewUser = jsonObject1.getBoolean("newUser");
                                    userId = jsonObject1.getLong("userId");
                                    token = jsonObject1.getString("token");
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                            SharedPreferences.Editor editor = saveSP.edit();
                            editor.putLong("userId",userId).commit();
                            editor.putString("token",token).commit();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(httpcode==200){
                            //判定是否新用户，新用户跳转注册页面，旧用户跳转主页
                            if(isNewUser) startActivity(intent);
                            else startActivity(intent2);
                        }
                    }

/*                    private void getfeedback(String responseData) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(responseData);
                            JSONArray jsonArray = jsonObject1.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //相应的内容
                                String message = jsonObject.getString("message");
                                String data = jsonObject.getString("data");
                                int code = jsonObject.getInt("code");
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }*/

                });
                thread2.start();
                try {
                    thread2.join(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(httpcode!=200)Toast.makeText(register2.this,"验证码有误，请重新输入",Toast.LENGTH_SHORT).show();
                }
    }

}