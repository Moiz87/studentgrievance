package com.example.loginwebservice;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    SharedPreferences sharedPreferences;
    Context context;
//
//    public String getEmial() {
//        emial= sharedPreferences.getString("userdata","");
//        return emial;
//    }
//
//    public void setEmial(String emial) {
//        this.emial = emial;
//        sharedPreferences.edit().putString("userdata",emial).commit();
//
//    }
//
//    private String emial;
//    public void remove(){
//        sharedPreferences.edit().clear().commit();
//    }
public String rollno;
    public String getRollno() {
        rollno= sharedPreferences.getString("userdata","");
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
        sharedPreferences.edit().putString("userdata",rollno).commit();
    }
        public void remove()
        {
        sharedPreferences.edit().clear().commit();
    }




    public User(Context context){
        this.context = context;
        sharedPreferences=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

    }
}
