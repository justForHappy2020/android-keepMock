package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.myapplication.utils.HttpUtils.connectHttp;

public class register1 extends AppCompatActivity implements View.OnClickListener{

    private EditText etPhoneNum;
    private Button btAcquireCode;

    private int httpcode;
    //private SharedPreferences saveSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        initView();
    }

    private void initView(){
        etPhoneNum = findViewById(R.id.phone_num);
        btAcquireCode = findViewById(R.id.acquire_vcode);
        btAcquireCode.setOnClickListener(this);//监听获取验证码按钮
        btAcquireCode.setBackgroundColor(Color.GRAY);
        btAcquireCode.setEnabled(Boolean.FALSE);

        //没有输入手机号时为灰色按钮
        etPhoneNum.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(etPhoneNum.getText()) && !TextUtils.isEmpty(etPhoneNum.getText())) {
                    btAcquireCode.setBackgroundColor(Color.parseColor("#4CAF50"));
                    btAcquireCode.setEnabled(Boolean.TRUE);//启用按钮
                }else{
                    //btAcquireCode.setBackgroundColor(Color.GREEN);
                    btAcquireCode.setBackgroundColor(Color.GRAY);
                    btAcquireCode.setEnabled(Boolean.FALSE);//不启用按钮
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    public void onClick(View view){
        final String mobile = etPhoneNum.getText().toString().trim();
        //SharedPreferences.Editor editor = saveSP.edit();//获取SharedPreferences实例保存数据
        final Intent intent2 = new Intent(this, register2.class);
        if(mobile.length()!=11){
            Intent intent1 = new Intent(this, register1.class);
            startActivity(intent1);
            finish();
            Toast.makeText(this,  "手机号格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
        }
        else {
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
                    if(httpcode==200){
                        intent2.putExtra("mobile",mobile);
                        startActivity(intent2);
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
            if(httpcode!=200)Toast.makeText(register1.this,"ERROR",Toast.LENGTH_SHORT).show();
        }
            //editor.putString("mobile",mobile);//保存手机号在本地
    }
}