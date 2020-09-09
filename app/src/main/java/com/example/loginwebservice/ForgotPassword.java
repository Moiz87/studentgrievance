package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
    Button btngetpwd,btnfpwdotp;
    TextView tvgetpwd,tvstfpwd;
    EditText etemail,etfpwdotp,etrollno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btngetpwd=findViewById(R.id.btngetpwd);
        //tvgetpwd=findViewById(R.id.tvgetpwd);
        etemail=findViewById(R.id.etemail);
        etrollno = findViewById(R.id.etrollno);
        //tvstfpwd=findViewById(R.id.tvstufpwdlogin);
       // btnfpwdotp=findViewById(R.id.btnsubotp);
       // etfpwdotp=findViewById(R.id.etfpwdotp);

//        tvstfpwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ForgotPassword.this,MainActivity.class);
//                startActivity(intent);
//
//            }
//        });
        btngetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tvgetpwd.setText("Your Password is :Mz@123");
                String email= etemail.getText().toString().trim();
                String rollno = etrollno.getText().toString().trim();
                getStudentPassword1(email,rollno);
            }
        });
//        btnfpwdotp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email= etemail.getText().toString().trim();
//                String otp=etfpwdotp.getText().toString().trim();
//                getStudentPassword2(email,otp);
//            }
//        });
    }
    private  void getStudentPassword1(final String email, final String rollno){
        RequestQueue queue = Volley.newRequestQueue(this);
//        String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/forgotpassword";
        String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/forgotpassword";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("rollno",rollno);
        jsonParams.put("email",email);

        final ProgressDialog progressDialog=new ProgressDialog(ForgotPassword.this);
        progressDialog.setMessage("Processing...");
        progressDialog.setTitle("Please Wait...!");
        progressDialog.setMax(1000);
        progressDialog.setCancelable(false);
        progressDialog.show();
        final AlertDialog.Builder builder=new AlertDialog.Builder(ForgotPassword.this);

        JsonObjectRequest postrequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String pass=null;
                try {

                    pass= response.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                   if(pass.equals("nodata"))
                   {
                       progressDialog.dismiss();
                       builder.setMessage("Incorrect Mail Id");
                       builder.setIcon(R.mipmap.ic_launcher);
                       builder.setTitle("Alert..!!");
                       //Toast.makeText(ForgotPassword.this,"Incorrect Email Id",Toast.LENGTH_SHORT).show();
                       builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               Intent intent = new Intent(ForgotPassword.this, ForgotPassword.class);
                               startActivity(intent);

                           }
                       });
                       AlertDialog alertDialog=builder.create();
                       alertDialog.setCancelable(false);
                       alertDialog.show();


                   }
                   else {
                       //tvgetpwd.setText(pass);
                       progressDialog.dismiss();
                       builder.setMessage("Otp Sent To Your Mail");
                       builder.setIcon(R.mipmap.ic_launcher);
                       builder.setTitle("Alert..!!");
                       //Toast.makeText(ForgotPassword.this,"Otp Sent To Your Mail",Toast.LENGTH_SHORT).show();
                       builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               Intent intent = new Intent(ForgotPassword.this, ForgotPassword2.class);
                               intent.putExtra("email",email);
                               intent.putExtra("rollno",rollno);
                               startActivity(intent);

                           }
                       });

//                       builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
//                           @Override
//                           public void onClick(DialogInterface dialog, int which) {
//
//                               Intent intent = new Intent(ForgotPassword.this, ForgotPassword.class);
//                               startActivity(intent);
//
//                           }
//                       });
                       AlertDialog alertDialog=builder.create();
                       alertDialog.setCancelable(false);
                       alertDialog.show();

                   }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgotPassword.this,"User Name Not Found",Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postrequest.setRetryPolicy(policy);
        queue.add(postrequest);
    }
//    private  void getStudentPassword2(String email,String otp){
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/forgotpassword2";
//        Map<String, String> jsonParams = new HashMap<String, String>();
//        jsonParams.put("email",email);
//        jsonParams.put("otp",otp);
//
//
//        JsonObjectRequest postrequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//
//                    String pass= response.getString("msg");
//                    if(pass.equals("nodata"))
//                    {
//                        Toast.makeText(ForgotPassword.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        tvgetpwd.setText(pass);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ForgotPassword.this,"User Name Not Found",Toast.LENGTH_SHORT).show();
//            }
//        });
//        int socketTimeout = 60000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        postrequest.setRetryPolicy(policy);
//        queue.add(postrequest);
//    }
}
