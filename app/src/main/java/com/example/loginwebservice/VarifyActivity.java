package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class VarifyActivity extends AppCompatActivity {
TextView tvlogout;
Button btnvrotp;
String otp;
    private static final String TAG ="Event" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify);
        tvlogout = findViewById(R.id.etfpwdotp);
        btnvrotp=findViewById(R.id.btnotpverify);

        btnvrotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = tvlogout.getText().toString();
                if (otp.equals("")) {
                    Toast.makeText(VarifyActivity.this, "Pleaase Eneter Otp", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    volleyCall(otp);

                }
            }
        });
    }


    private void volleyCall(String otp) {
        RequestQueue queue = Volley.newRequestQueue(this);
//        String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/varify";
        String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/varify";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("otp",otp);

        final ProgressDialog progressDialog=new ProgressDialog(VarifyActivity.this);
        progressDialog.setMessage("Work in is Progress...");
        progressDialog.setTitle("Please Wait...!");
        progressDialog.setMax(1000);
        progressDialog.setCancelable(false);
        progressDialog.show();

        final AlertDialog.Builder builder=new AlertDialog.Builder(VarifyActivity.this);



        Log.d(TAG, "Json:" + new JSONObject(jsonParams));
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String msg = null;
                        try {
                            msg = (String) response.get("msg");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // String msg=response.toString();
                        if (msg.equals("user activated")) {

                            progressDialog.dismiss();
                            builder.setMessage(msg);
                            builder.setIcon(R.mipmap.ic_launcher);
                            builder.setTitle("Alert..!!");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(VarifyActivity.this, MainActivity.class);
                                    startActivity(intent);

                                }
                            });

                            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(VarifyActivity.this, VarifyActivity.class);
                                    startActivity(intent);

                                }
                            });
                            AlertDialog alertDialog=builder.create();
                            alertDialog.setCancelable(false);
                            alertDialog.show();

                        }

                        else if (msg.equals("user Not activated")) {

                            progressDialog.dismiss();
                            builder.setMessage(msg);
                            builder.setIcon(R.mipmap.ic_launcher);
                            builder.setTitle("Alert..!!");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(VarifyActivity.this, VarifyActivity.class);
                                    startActivity(intent);

                                }
                            });

                            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(VarifyActivity.this, VarifyActivity.class);
                                    startActivity(intent);

                                }
                            });
                            AlertDialog alertDialog=builder.create();
                            alertDialog.setCancelable(false);
                            alertDialog.show();

                        }


                        else {

                            progressDialog.dismiss();
                            builder.setMessage(msg);
                            builder.setIcon(R.mipmap.ic_launcher);
                            builder.setTitle("Alert..!!");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(VarifyActivity.this, VarifyActivity.class);
                                    startActivity(intent);

                                }
                            });

                            builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(VarifyActivity.this, Register.class);
                                    startActivity(intent);

                                }
                            });
                            AlertDialog alertDialog=builder.create();
                            alertDialog.setCancelable(false);
                            alertDialog.show();






                        }    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error
                        Log.d(TAG, "Error: " + error
                                + "\nmessage" + error.getMessage());
                    }
                });
        int socketTimeout = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        queue.add(postRequest);

    }
}






