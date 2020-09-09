package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class Statistics extends AppCompatActivity {
    TextView tvregistered, tvpending, tvactiontaken, tvsolved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvregistered = findViewById(R.id.tvregistered);
        tvpending = findViewById(R.id.tvpending);
        tvactiontaken = findViewById(R.id.tvactiontaken);
        tvsolved = findViewById(R.id.tvsolved);
        //tvpending.setText(String.valueOf(12));
        getStatistics();


    }

    public void getStatistics() {
        RequestQueue queue = Volley.newRequestQueue(this);
      //  String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/statistics";
        // String URL = "http://192.168.56.1:8080/demorest/webapi/myresource/statistics";
        String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/statistics";
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int pending, actiontaken,resolved,registered;
                        try {
                           pending=response.getInt("pending");
                          actiontaken= response.getInt("actiontaken");
                          resolved= response.getInt("resolved");
                          registered=response.getInt("registered");

                          tvsolved.setText("Number of complaints Resolved : "+String.valueOf(resolved));
                        tvactiontaken.setText("Number of complaints Action Taken : "+String.valueOf(actiontaken));
                        tvpending.setText("Number of complaints Pending : "+String.valueOf(pending));
                        tvregistered.setText("Number of complaints Registered : "+String.valueOf(registered));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Statistics.this, "Some thing Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(postRequest);
    }
}

//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray jsonArray= new JSONArray(response);
//                    for(int i=0 ; i<jsonArray.length() ; i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        int pending =jsonObject.getInt("pending");
//                        int resolved =jsonObject.getInt("resolved");
//                        int actiontaken=jsonObject.getInt("actiontaken");
//                        tvsolved.setText(String.valueOf(resolved));
//                        tvactiontaken.setText(String.valueOf(actiontaken));
//                        tvpending.setText(String.valueOf(pending));
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Statistics.this,"Something went wrong",Toast.LENGTH_SHORT).show();
//            }
//        });
//        queue.add(stringRequest);
//    }

//}