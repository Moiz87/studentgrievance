package com.example.loginwebservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

public class ComplaintStatus extends AppCompatActivity {
    TextView tvcdcid;
    TableLayout tbstcomp;
    TableRow tr;
    TextView tvstcid, tvstdesc, tvststatus, tvstcompcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status);
        tvcdcid = findViewById(R.id.tvcdcid);
        tbstcomp = findViewById(R.id.tbstcomp);

        String cid = getIntent().getExtras().getString("cid");
        tvcdcid.setText(cid);

        tbstcomp.setColumnStretchable(0, true);
        tbstcomp.setColumnStretchable(1, true);
        tbstcomp.setColumnStretchable(2, true);
        tbstcomp.setColumnStretchable(3, true);

        loadStudentComplaints(cid);
    }

    private void loadStudentComplaints(String cid) {
        RequestQueue queue = Volley.newRequestQueue(this);
//        String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/getstudentcomplaints";
        String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/getstudentcomplaints";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("cid", cid);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    JSONArray jsonArray = new JSONArray();
//                    JSONArray jsonArray= new JSONArray(response);
                    JSONArray jsonArray = response.getJSONArray("msg");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String cid = jsonObject.getString("cid");
                            String desc = jsonObject.getString("desc");
                            String status = jsonObject.getString("status");
                            String ctype = jsonObject.getString("ctype");


                            tr = new TableRow(ComplaintStatus.this);
                            tvstcid = new TextView(ComplaintStatus.this);
                            tvstdesc = new TextView(ComplaintStatus.this);
                            tvststatus = new TextView(ComplaintStatus.this);
                            tvstcompcat = new TextView(ComplaintStatus.this);


                            tvstcid.setText(cid);
                            tvstdesc.setText(desc);
                            tvststatus.setText(status);
                            tvstcompcat.setText(ctype);

                            tr.addView(tvstcid);
                            tr.addView(tvstdesc);
                            tr.addView(tvststatus);
                            tr.addView(tvstcompcat);

                            tbstcomp.addView(tr);

                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintStatus.this,"There is an error in loading compliants please try again",Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(postRequest);
    }
}

