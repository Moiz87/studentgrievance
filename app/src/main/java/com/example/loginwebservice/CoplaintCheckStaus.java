package com.example.loginwebservice;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CoplaintCheckStaus extends AppCompatActivity {
    EditText etchkstaus;
    Button btnchkstaus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coplaint_check_staus);
        etchkstaus =findViewById(R.id.etcheckstatus);
        btnchkstaus= findViewById(R.id.btncheckstatus);

        btnchkstaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = etchkstaus.getText().toString().trim();
                studentComplaintDetails(cid);
            }
        });
    }

    private  void studentComplaintDetails(final String cid){
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
                    if(jsonArray.isNull(0))
                    {
                        Toast.makeText(CoplaintCheckStaus.this,"No Data Found please Enter Correct CID",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(CoplaintCheckStaus.this,ComplaintStatus.class);
                        intent.putExtra("cid",cid);
                        startActivity(intent);
                    }

            } catch (
            JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(CoplaintCheckStaus.this,"There is an error in loading compliants please try again",Toast.LENGTH_SHORT).show();
        }
    });

        queue.add(postRequest);
}
}