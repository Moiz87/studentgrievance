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

public class Register extends AppCompatActivity {
    private static final String TAG="event";
    EditText etuname,etpwd,etcpwd,etemail,etrollno;
    Button btnregister;
    TextView tvlogin;
    String  name,pass,email,roll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etuname = findViewById(R.id.etruname);
        etpwd = findViewById(R.id.etrpwd);
        etcpwd = findViewById(R.id.etcpwd);
        etemail =findViewById(R.id.etremail);
        etrollno=findViewById(R.id.etrrollno);
        btnregister = findViewById(R.id.btnregister);
        tvlogin = findViewById(R.id.tvlogin);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etuname.getText().toString().trim();
                pass = etpwd.getText().toString().trim();
                email = etemail.getText().toString().trim();
                roll = etrollno.getText().toString().trim();
                if (name.equals("") || pass.equals("") || email.equals("") || roll.equals(""))  {
                    Toast.makeText(Register.this, "Plz Fill the Fields", Toast.LENGTH_LONG).show();
                } else {
                    volleyCall(name, pass, email,roll);
                }
            }
        });
    }
        // private void volleyCall(String name, String pass,String email) {
        private void volleyCall(String name, String pass,String email,String roll)
        {
            RequestQueue queue = Volley.newRequestQueue(this);
//            String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/register";
            String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/register";
            Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("name", name);
            jsonParams.put("pass", pass);
            jsonParams.put("email",email);
            jsonParams.put("rollno",roll);

            final ProgressDialog progressDialog=new ProgressDialog(Register.this);
            progressDialog.setMessage("Work in is Progress...");
            progressDialog.setTitle("Please Wait...!");
            progressDialog.setMax(1000);
            progressDialog.setCancelable(false);
            progressDialog.show();

            final AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);


            Log.d(TAG, "Json:" + new JSONObject(jsonParams));
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String msg = null;
                            try {
                                msg = (String) response.get("msg");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // String msg=response.toString();
                            if (msg.equals("sucess")) {

                                progressDialog.dismiss();
                                builder.setMessage(msg);
                                builder.setIcon(R.mipmap.ic_launcher);
                                builder.setTitle("Alert..!!");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(Register.this, VarifyActivity.class);
                                        startActivity(intent);

                                    }
                                });

                                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(Register.this, Register.class);
                                        startActivity(intent);

                                    }
                                });
                                AlertDialog alertDialog=builder.create();
                                alertDialog.setCancelable(false);

//                                Toast.makeText(Register.this, "Your Data is Submited", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(Register.this, MainActivity.class);
//                                startActivity(intent);

                            }

                            else{

                                progressDialog.dismiss();
                                builder.setMessage(msg);
                                builder.setIcon(R.mipmap.ic_launcher);
                                builder.setTitle("Alert..!!");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(Register.this, VarifyActivity.class);
                                        startActivity(intent);

                                    }
                                });

                                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(Register.this, Register.class);
                                        startActivity(intent);

                                    }
                                });
                                AlertDialog alertDialog=builder.create();
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //   Handle Error
                            Log.d(TAG, "Error: " + error
                                    + "\nmessage" + error.getMessage());
                        }
                    });
            //this is used when we send the request to the server there would be chances that it would take large time or it would take some time we get two responses from the server so to restrict that we use this
            int socketTimeout = 60000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            postRequest.setRetryPolicy(policy);
            //this is used add the request to queue
            queue.add(postRequest);
        }
    public void login(View view){
        Intent intent = new Intent(Register.this,MainActivity.class);
        startActivity(intent);
    }

}
