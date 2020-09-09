package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword2 extends AppCompatActivity {
    Button btnfpwdotp;
    TextView tvgetpwd,tvstfpwd;
    EditText etfpwdotp;
    String email,rollno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);

        tvstfpwd=findViewById(R.id.tvstufpwdlogin);
         btnfpwdotp=findViewById(R.id.btnsubotp);
         etfpwdotp=findViewById(R.id.etfpwdotp);
         tvgetpwd=findViewById(R.id.tvgetpwd);
        // email=getIntent().getExtras().getString("email");
         email=getIntent().getExtras().getString("email");
         rollno = getIntent().getExtras().getString("rollno");

        tvstfpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword2.this,MainActivity.class);
                startActivity(intent);

            }
        });

        btnfpwdotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp=etfpwdotp.getText().toString().trim();
                getStudentPassword2(rollno,email,otp);
            }
        });

    }
    private  void getStudentPassword2(String rollno,String email,String otp){
        RequestQueue queue = Volley.newRequestQueue(this);
//        String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/forgotpassword2";
        String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/forgotpassword2";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("rollno",rollno);
        jsonParams.put("email",email);
        jsonParams.put("otp",otp);


        JsonObjectRequest postrequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String pass= response.getString("msg");
                    if(pass.equals("nodata"))
                    {
                        Toast.makeText(ForgotPassword2.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        tvgetpwd.setText(pass);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgotPassword2.this,"User Name Not Found",Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postrequest.setRetryPolicy(policy);
        queue.add(postrequest);
    }
}
