package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class AdminChangeStatus extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        TextView tvcidchstatus;
        Spinner mysppinnerstatus;
        Button btnchangestatus;
        String cid,statusvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_status);

       cid=getIntent().getExtras().getString("cid");

        tvcidchstatus=findViewById(R.id.tvcidchstatus);
        mysppinnerstatus=findViewById(R.id.spstatus);
        btnchangestatus=findViewById(R.id.btnchangestatus);
        tvcidchstatus.setText(cid);

        mysppinnerstatus.setOnItemSelectedListener(this);
        String staus[]={"Pending","ActionTaken","Resolved"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, staus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mysppinnerstatus.setAdapter(adapter);

        btnchangestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(cid,statusvalue);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spstatus)
        {
            statusvalue = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateStatus(String cid,String status){
        RequestQueue queue = Volley.newRequestQueue(this);
       // String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/changeStatus";
        String URL ="http://192.168.0.103:8080/demorest/webapi/myresource/changeStatus";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("cid",cid);
        jsonParams.put("status",status);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String msg = null;
                        try {
                            msg = (String) response.get("msg");

                            if (msg.equals("success")) {
                                Toast.makeText(AdminChangeStatus.this,"Status Updated Scccesfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminChangeStatus.this,RecyclerViewExample.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(AdminChangeStatus.this,"Update Not Successful",Toast.LENGTH_SHORT).show();

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    },

                new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //   Handle Error
                            Toast.makeText(AdminChangeStatus.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        queue.add(postRequest);
                }

    }


