package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AddComplaintType extends AppCompatActivity {
    Button btnaddctype, btndctype;
    Spinner spaddctype;
    EditText etctype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint_type);

        btnaddctype = findViewById(R.id.btnaddctype);
        btndctype = findViewById(R.id.btndisplaytype);
        spaddctype = findViewById(R.id.spaddctype);
        etctype = findViewById(R.id.etctype);

        btnaddctype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ctype = etctype.getText().toString().trim();
                if (ctype.equals("")) {
                    Toast.makeText(AddComplaintType.this, "plz enter complaint type", Toast.LENGTH_SHORT).show();
                } else {
                    addComplaintType(ctype);
                }


            }
        });

        btndctype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddComplaintType.this,DisplayComplaintType.class);
                startActivity(intent);
            }
        });

    }

    public void addComplaintType(String ctype) {
        RequestQueue queue = Volley.newRequestQueue(this);
//        String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/insCtype";
        String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/insCtype";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("ctype", ctype);

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
                        if (msg.equals("success")) {
                            Toast.makeText(AddComplaintType.this, "Complaint Added Sucessfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddComplaintType.this, "Something Gone Wrong", Toast.LENGTH_SHORT).show();

                        }


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error
//                        Log.d(TAG, "Error: " + error
//                                + "\nmessage" + error.getMessage());
                        Toast.makeText(AddComplaintType.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

        int socketTimeout = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        //this is used add the request to queue
        queue.add(postRequest);
    }
}

