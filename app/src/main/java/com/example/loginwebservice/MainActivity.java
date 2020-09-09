package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etuname,etpwd;
    Button btnlogin;
    TextView tvfpwd,tvregister;
    String lemail,lpass;
    private static final String TAG="event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etuname = findViewById(R.id.etlemail);
        etpwd = findViewById(R.id.etlpwd);
        btnlogin = findViewById(R.id.btnlogin);
        tvfpwd = findViewById(R.id.tvforgotpwd);
        tvregister = findViewById(R.id.tvregister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lemail = etuname.getText().toString().trim();
               lpass = etpwd.getText().toString().trim();

                if (lemail.equals("") || lpass.equals("")) {
                    Toast.makeText(MainActivity.this, "Plz Fill the Fields", Toast.LENGTH_LONG).show();
                }
                else {
                    volleyCall(lemail,lpass);
                }
            }
        });
    }
        private void volleyCall(final String rollno, String pass)
        {
            RequestQueue queue = Volley.newRequestQueue(this);
            String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/login";
           // String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/login";
//            String URL = "http://192.168.43.75:8080/demorest/webapi/myresource/login";

            Map<String, String> jsonParams = new HashMap<String, String>();

            jsonParams.put("rollno",rollno);
            jsonParams.put("pass", pass);

//            final ProgressDialog progressDialog=new ProgressDialog(Register.this);
//            progressDialog.setMessage("Work in is Progress...");
//            progressDialog.setTitle("Please Wait...!");
//            progressDialog.setMax(1000);
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//
//            final AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);


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
                            if (msg.equals("success")){

                                Toast.makeText(MainActivity.this,"Login Success...", Toast.LENGTH_LONG).show();
                                //shared preference
                                User user = new  User(MainActivity.this);
                                //user.setEmial(email);
                                user.setRollno(rollno);

                                Intent intent = new Intent(MainActivity.this,Welcome.class);
                                intent.putExtra("rollno",rollno);
                                startActivity(intent);

                            }
                            else if(msg.equals("Not Active"))
                            {
                                Toast.makeText(MainActivity.this,"Authenticate User First",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,VarifyActivity.class);
                                startActivity(intent);
                            }

                            else
                                Toast.makeText(MainActivity.this, "Invalid User Name or Pass", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Json" + response);
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
            //postRequest.setShouldCache(false);
            queue.add(postRequest);
        }




    public void register(View view)
    {
        Intent intent = new Intent(MainActivity.this,Register.class);
        startActivity(intent);
    }
//    public void welcome(View view)
//    {
//        Intent intent = new Intent(MainActivity.this,Welcome.class);
//        startActivity(intent);
//    }

}
