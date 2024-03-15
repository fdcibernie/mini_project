package com.example.consumerbill.cores;

import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.cores.interfaces.IApiListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class HelperClass {
    public HelperClass() {
    }

    public void deserializeJsonObject(JSONObject data, IApiListener<ArrayList<Billers>> apiListener){}
}
