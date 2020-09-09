package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //for hiding the tool bar
        getSupportActionBar().hide();
        final User user = new User(SplashActivity.this);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

//                if(user.getEmial() != "") {
                if(user.getRollno() != "") {
                    Intent intent = new Intent(SplashActivity.this, Welcome.class);
                    intent.putExtra("rollno",user.getRollno());
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
}
