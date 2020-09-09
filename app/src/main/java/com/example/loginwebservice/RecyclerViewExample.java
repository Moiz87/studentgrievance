package com.example.loginwebservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewExample extends AppCompatActivity {
    List<ListItem> listItems;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerview);
        //this means everey item of the recycler view has a fixed size
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        listItems = new ArrayList<>();
        loadComplaints();
//        for(int i=0;i<10;i++)
//        {
//            ListItem listItem = new ListItem(
//                    "CID" +(i+1),
//                    "This Complaint Related to Ragging",
//                    "Pending",
//                    "Ragging"
//            );
//            listItems.add(listItem);
//        }
//        adapter= new MyAdapter(listItems,this);
//        recyclerView.setAdapter(adapter);
    }
    private void loadComplaints() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        // String URL = "http://192.168.0.101:8080/demorest/webapi/myresource/allcomplaints";
        String URL = "http://192.168.0.103:8080/demorest/webapi/myresource/allcomplaints";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray= new JSONArray(response);

                    for(int i=0 ; i<jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String cid = jsonObject.getString("cid");
                        String desc = jsonObject.getString("desc");
                        String status = jsonObject.getString("status");
                        String cat = jsonObject.getString("ctype");
                        ListItem item = new ListItem(cid,desc,status,cat);
                        listItems.add(item);
                    }
                    adapter= new MyAdapter(listItems,RecyclerViewExample.this);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RecyclerViewExample.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }


}
