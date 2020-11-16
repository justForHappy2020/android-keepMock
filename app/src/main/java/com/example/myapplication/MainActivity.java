package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY = 600;
    private TimerTask task;
    private String token;
    private SharedPreferences readSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent1 = new Intent(this, register1.class);
        final Intent intent2 = new Intent(this, exercise_main.class);
/*        Timer timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                readSP = getSharedPreferences("saved_token",MODE_PRIVATE);
                token = readSP.getString("token","");
                if(token == "")startActivity(intent1);
                else {
                    intent2.putExtra("token",token);
                    startActivity(intent2);
                }
            }
        };
        timer.schedule(task,DELAY);*/
        readSP = getSharedPreferences("saved_token",MODE_PRIVATE);
        token = readSP.getString("token","");//test

        startActivity(intent1);//test

/*        if(token.equals("")||token.equals("null"))startActivity(intent1);
        else {
            intent2.putExtra("token",token);
            startActivity(intent2);
        }*/

    }
}
