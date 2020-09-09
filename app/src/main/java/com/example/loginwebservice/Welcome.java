package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
        TextView tvwel;
        Button btnallcomplaints,btnaddComplaintType,btnstat,btnfpwd,btnrecyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tvwel=findViewById(R.id.tvwel);
        btnallcomplaints = findViewById(R.id.btnallcomplaints);
        btnaddComplaintType = findViewById(R.id.btnaddcomplainttype);
        btnstat=findViewById(R.id.btnstat);
        btnfpwd=findViewById(R.id.btnforgotpwd);
        btnrecyclerview=findViewById(R.id.btnrecyclerview);

        String msg=getIntent().getExtras().getString("rollno");
        tvwel.setText("Welcome To :"+msg);
        btnallcomplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,AllComplaints.class);
                startActivity(intent);
            }
        });
        btnaddComplaintType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,AddComplaintType.class);
                startActivity(intent);
            }
        });
        btnstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,Statistics.class);
                startActivity(intent);
            }
        });
        btnfpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
        btnrecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,RecyclerViewExample.class);
                startActivity(intent);
            }
        });
    }
    public void logout(View view){
        new User(Welcome.this).remove();
        Intent intent = new Intent(Welcome.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
