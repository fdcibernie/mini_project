package com.example.consumerbill.billers.data.datasource.remote;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.consumerbill.billers.data.datasource.remote.model.BillerModel;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.billers.domain.usecase.DeserializeBillerResponse;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.ConstRef;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.interfaces.IApiListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import org.json.JSONObject;

import java.util.ArrayList;

public class BillerDataSrcImpl implements IBillersDataSrc{

    public static final String TAG = "BillerDataSrcImpl";
    private static BillerDataSrcImpl instance = null;


    public BillerDataSrcImpl() {}


    public static synchronized BillerDataSrcImpl getInstance() {
        if (instance == null) {
            instance = new BillerDataSrcImpl();
        }

        return instance;
    }

    @Override
    public void fetchBillers(VolleySingleton volleySingleton
            ,IApiListener<ArrayList<Billers>> apiListener) {
        DeserializeBillerResponse deserializeBillerResponse = DeserializeBillerResponse.getInstance();
        String url = ConstRef.FIREBASE_DOMAIN + "/billers.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, jsonObject -> {
            deserializeBillerResponse.deserializeJsonObject(jsonObject,apiListener);
        }, volleyError -> {
            Log.e(TAG, "response-error: "+ volleyError.getMessage());
            apiListener.onApiResponse(new ApiResult<>(null, ResponseStatus.SUCCESS,""));
        });

        volleySingleton.addToRequestQueue(jsonObjectRequest);
    }
}
