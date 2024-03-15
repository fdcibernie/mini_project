package com.example.consumerbill.bill_info.data.datasource.remote;

import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAppItemListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface IBillInfoDataSrc {
    //CompletableFuture<ArrayList<ConsumerBill>> fetchConsumers(VolleySingleton volleySingleton, String id);
    //CompletableFuture<ApiResult<ArrayList<ConsumerBill>>> searchConsumer(VolleySingleton volleySingleton, String text, String id);
    void searchConsumer(VolleySingleton volleySingleton, String text, String id, IAppItemListener<ApiResult<ArrayList<ConsumerBill>>> listener);
}
