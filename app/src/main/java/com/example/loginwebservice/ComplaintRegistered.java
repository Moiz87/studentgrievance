package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ComplaintRegistered extends AppCompatActivity {

    TextView textView7,textView6,tvchkstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_registered);
        textView7 = findViewById(R.id.textView7);
        tvchkstatus= findViewById(R.id.tvchkstatus);
        String cid=getIntent().getExtras().getString("cid");
        textView7.setText("Your Complaint Id is :"+cid);

        tvchkstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComplaintRegistered.this,CoplaintCheckStaus.class);
                startActivity(intent);
            }
        });

    }
}
