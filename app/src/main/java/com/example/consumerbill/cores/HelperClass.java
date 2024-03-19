package com.example.consumerbill.cores;

import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.cores.interfaces.IApiListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class HelperClass<T> {
    public HelperClass() {
    }

    //public void deserializeJsonObject(JSONObject data, IApiListener<ArrayList<Billers>> apiListener){}
    public ArrayList<T> deserializeJsonObjectType(JSONObject data){
        return null;
    }
    public String formatDecimal(String numberStr) {
        return null;
    }
}
