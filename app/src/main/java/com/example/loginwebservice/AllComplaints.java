package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AllComplaints extends AppCompatActivity {
    TableLayout statustable;
    TableRow tr;
    TextView tvcid, tvdesc, tvastatus, tvcompcat, tvcstatus;
    TextView tvcadd;
    private static final String TAG = "event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_complaints);

        tvcadd = findViewById(R.id.tvaddcomp);
        tvastatus = new TextView(this);

        statustable = findViewById(R.id.statustable);
        // db = new DatabaseHelper(this);
        statustable.setColumnStretchable(0, true);
        statustable.setColumnStretchable(1, true);
        statustable.setColumnStretchable(2, true);
        statustable.setColumnStretchable(3, true);
        //statustable.setColumnStretchable(3, true);
        tvcadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadComplaints();
            }
        });
    }

    private void loadComplaints() {
        RequestQueue queue = Volley.newRequestQueue(this);
       // String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/allcomplaints";
        String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/allcomplaints";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray= new JSONArray(response);

               for(int i=0 ; i<jsonArray.length() ; i++){
                   JSONObject jsonObject = jsonArray.getJSONObject(i);

                   String cid = jsonObject.getString("cid");
                   String desc = jsonObject.getString("desc");
                   String status = jsonObject.getString("status");
                   String cat = jsonObject.getString("ctype");


                   tr = new TableRow(AllComplaints.this);
                   tvcid = new TextView(AllComplaints.this);
                   tvdesc = new TextView(AllComplaints.this);
                   tvastatus = new TextView(AllComplaints.this);
                   tvcompcat = new TextView(AllComplaints.this);
                   // tvcstatus = new TextView(this);



                   tvcid.setText(cid);
                   tvdesc.setText(desc);
                   tvastatus.setText(status);
                   tvcompcat.setText(cat);
                   // tvcstatus.setText("Click Here To Change Status");

//        tvcid.setText("CID1");
//        tvdesc.setText("This is so and so complaint and this");
//        tvstatus.setText("Progress");
//        tvcompcat.setText("Ragging");

                   tvcid.setGravity(Gravity.CENTER);
                   tvdesc.setGravity(Gravity.CENTER);
                   tvastatus.setGravity(Gravity.CENTER);
                   tvcompcat.setGravity(Gravity.CENTER);
                   //  tvcstatus.setGravity(Gravity.CENTER);


//        tvcid.setBackgroundResource(R.drawable.border_style);
//        tvdesc.setBackgroundResource(R.drawable.border_style);
//        tvstatus.setBackgroundResource(R.drawable.border_style);
//        tvcompcat.setBackgroundResource(R.drawable.border_style);

//                   tr.setBackgroundResource(R.drawable.border_style);
//                   tr.setBackgroundResource(R.color.white);

                   tvcid.setWidth(100);
                   tvdesc.setWidth(100);
                   tvastatus.setWidth(100);
                   tvcompcat.setWidth(100);
                   //tvcstatus.setWidth(100);

                   tvastatus.setTextColor(getResources().getColor(R.color.colorAccent));
                   // tvcstatus.setTextColor(getResources().getColor(R.color.blue));
                   tr.addView(tvcid);
                   tr.addView(tvdesc);
                   tr.addView(tvastatus);
                   tr.addView(tvcompcat);
                   //tr.addView(tvcstatus);


                   statustable.addView(tr);

               }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Toast.makeText(AllComplaints.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
            queue.add(stringRequest);
    }
}