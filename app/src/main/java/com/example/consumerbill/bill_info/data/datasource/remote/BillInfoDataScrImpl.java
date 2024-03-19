package com.example.consumerbill.bill_info.data.datasource.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.ConstRef;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.interfaces.IAppItemListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class BillInfoDataScrImpl implements IBillInfoDataSrc{

    private static BillInfoDataScrImpl instance = null;

    public BillInfoDataScrImpl() {
    }

    public static synchronized BillInfoDataScrImpl getInstance() {
        if(instance == null) {
            instance = new BillInfoDataScrImpl();
        }
        return instance;
    }

    @Override
    public void searchConsumer(VolleySingleton volleySingleton, String text, String id, IAppItemListener<ApiResult<ArrayList<ConsumerBill>>> listener) {
        String url = ConstRef.FIREBASE_DOMAIN+"/consumer/"+id+".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, jsonObject -> {
            DeserializeConsumerBill deserializeConsumerBill = DeserializeConsumerBill.getInstance();
            ArrayList<ConsumerBill> list = deserializeConsumerBill.deserializeJsonObjectType(jsonObject);
            listener.onAppItemSelected(new ApiResult<>(list, ResponseStatus.SUCCESS, ""));
        }, volleyError -> {
            listener.onAppItemSelected(new ApiResult<>(null, ResponseStatus.ERROR, volleyError.getMessage()));
        });

        volleySingleton.addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void updatePaymentStatus(VolleySingleton volleySingleton, String key, IAppItemListener<ApiResult<Void>> listener) {
        String url = ConstRef.FIREBASE_DOMAIN+"/consumer/"+key+".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH,url, null, jsonObject -> {
            listener.onAppItemSelected(new ApiResult<>(null, ResponseStatus.SUCCESS, ""));
        }, volleyError -> {
            listener.onAppItemSelected(new ApiResult<>(null, ResponseStatus.ERROR, volleyError.getMessage()));
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("payment_status", "paid");
                return params;
            }
        };

        volleySingleton.addToRequestQueue(jsonObjectRequest);
    }
}
