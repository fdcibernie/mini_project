package com.example.consumerbill.cores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private SharedPreferences sharedPreferences;
    private Context context;

    @SuppressLint("StaticFieldLeak")
    private static MySharedPreferences instance = null;

    public MySharedPreferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("ConsumerPref",Context.MODE_PRIVATE);
    }

    public static synchronized MySharedPreferences getInstance(Context context) {
        if(instance == null) {
            instance = new MySharedPreferences(context);
        }

        return instance;
    }

    public void savePrefString(String key,String value) {
        sharedPreferences.edit()
                .putString(key,value)
                .apply();
    }

    public void savePrefBoolean(String key,boolean value) {
        sharedPreferences.edit()
                .putBoolean(key,value)
                .apply();
    }

    public boolean getBooleanPref(String key) {
        return sharedPreferences.getBoolean(key,false);
    }
}
