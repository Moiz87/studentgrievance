package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayComplaintType extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spdispctype;
    EditText etcompdesc;
    Button btncompsubmit;
    String status,desc,compcat,cid;

//       ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_complaint_type);
        spdispctype = findViewById(R.id.spdisctype);
        etcompdesc =findViewById(R.id.etcomplaintdesc);
        btncompsubmit= findViewById(R.id.btncompsubmit);
        getComplaintType();

        btncompsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "Pending";
                desc=etcompdesc.getText().toString().trim();
                cid="CID4";
                if(desc.equals("") || compcat.equals(""))
                {
                    Toast.makeText(DisplayComplaintType.this,"Please Select the category and write description",Toast.LENGTH_SHORT).show();
                }
                else {

                    registercomplaint(cid,desc,status,compcat);

                }
            }
        });
    }
    private void registercomplaint(final String cid, String description, String status, String ctype) {
        RequestQueue registercomplaint = Volley.newRequestQueue(this);
//        String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/complaintregistration";
        String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/complaintregistration";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("cid", cid);
        jsonParams.put("description", description);
        jsonParams.put("status", status);
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
                            Toast.makeText(DisplayComplaintType.this,"Complaint Registered Succesfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DisplayComplaintType.this,ComplaintRegistered.class);
                            intent.putExtra("cid",cid);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(DisplayComplaintType.this,"Complaint Not Registered",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        registercomplaint.add(postRequest);
    }

        private void getComplaintType() {
        RequestQueue queue = Volley.newRequestQueue(this);
//        String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/displaycomplainttype";
            String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/displaycomplainttype";
        StringRequest getrequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<String> list= new ArrayList<String>();
                try
                {
                    JSONArray jsonArray= new JSONArray(response);
                    for(int i=0 ;i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String ctype =jsonObject.getString("ctype");
                        list.add(ctype);
                       // list.add(jsonArray.getJSONObject(i).getString("cname"));
                    }
                    spdispctype.setOnItemSelectedListener(DisplayComplaintType.this);
                    ArrayAdapter adapter =new ArrayAdapter(DisplayComplaintType.this,android.R.layout.simple_spinner_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spdispctype.setAdapter(adapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DisplayComplaintType.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        getrequest.setRetryPolicy(policy);
        //this is used add the request to queue
        queue.add(getrequest);
    }
        @Override
        public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
            if(parent.getId() == R.id.spdisctype)
            {
                compcat = parent.getItemAtPosition(position).toString();
            }
        }

        @Override
        public void onNothingSelected (AdapterView < ? > parent){

        }
    }
