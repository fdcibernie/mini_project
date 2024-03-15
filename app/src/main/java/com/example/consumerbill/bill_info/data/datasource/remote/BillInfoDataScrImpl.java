package com.example.consumerbill.bill_info.data.datasource.remote;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.ConstRef;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.interfaces.IAppItemListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;
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

//    @Override
//    public CompletableFuture<ArrayList<ConsumerBill>> fetchConsumers(VolleySingleton volleySingleton, String id) {
//        String url = ConstRef.FIREBASE_DOMAIN+"/consumer/"+id+".json";
//        CompletableFuture<ArrayList<ConsumerBill>> completableFuture = new CompletableFuture<>();
//        DeserializeConsumerBill deserializeConsumerBill = DeserializeConsumerBill.getInstance();
//        Executors.newCachedThreadPool().submit(()->{
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, jsonObject -> {
//                ArrayList<ConsumerBill> list = deserializeConsumerBill.deserializeJsonObjectType(jsonObject);
//                Log.e("BillInfo","filteredList2:"+ list);
//                completableFuture.complete(list);
//            }, volleyError -> {
//                completableFuture.complete(null);
//            });
//
//            volleySingleton.addToRequestQueue(jsonObjectRequest);
//        });
//
//        return completableFuture;
//    }


    @Override
    public void searchConsumer(VolleySingleton volleySingleton, String text, String id, IAppItemListener<ApiResult<ArrayList<ConsumerBill>>> listener) {
        String url = ConstRef.FIREBASE_DOMAIN+"/consumer/"+id+".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, jsonObject -> {
            DeserializeConsumerBill deserializeConsumerBill = DeserializeConsumerBill.getInstance();
            ArrayList<ConsumerBill> list = deserializeConsumerBill.deserializeJsonObjectType(jsonObject);
            Log.e("BillInfo","filteredList2:"+ list);
            listener.onAppItemSelected(new ApiResult<>(list, ResponseStatus.SUCCESS, ""));
        }, volleyError -> {
            listener.onAppItemSelected(new ApiResult<>(null, ResponseStatus.ERROR, volleyError.getMessage()));
        });

        volleySingleton.addToRequestQueue(jsonObjectRequest);
    }

//    @Override
//    public CompletableFuture<ApiResult<ArrayList<ConsumerBill>>> searchConsumer(VolleySingleton volleySingleton, String text, String id) {
//
//        String url = ConstRef.FIREBASE_DOMAIN+"/consumer/"+id+".json";
//        CompletableFuture<ApiResult<ArrayList<ConsumerBill>>> completableFuture = new CompletableFuture<>();
//        DeserializeConsumerBill deserializeConsumerBill = DeserializeConsumerBill.getInstance();
//        Executors.newCachedThreadPool().submit(()->{
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, jsonObject -> {
//                ArrayList<ConsumerBill> list = deserializeConsumerBill.deserializeJsonObjectType(jsonObject);
//                Log.e("BillInfo","filteredList2:"+ list);
//                completableFuture.complete(new ApiResult<>(list, ResponseStatus.SUCCESS, ""));
//            }, volleyError -> {
//                completableFuture.complete(new ApiResult<>(null, ResponseStatus.ERROR, volleyError.getMessage()));
//            });
//
//            volleySingleton.addToRequestQueue(jsonObjectRequest);
//        });
//
//        return completableFuture;
//    }
}
