package com.example.consumerbill.bill_info.domain.repository;

import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAppItemListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface IBillInfoRepository {
    //CompletableFuture<ApiResult<ArrayList<ConsumerBill>>> searchConsumer(VolleySingleton volleySingleton, String text, String id);
    void searchConsumer(VolleySingleton volleySingleton, String text, String id, IAppItemListener<ApiResult<ArrayList<ConsumerBill>>> listener);
    void updatePaymentStatus(VolleySingleton volleySingleton,String biller,String key,IAppItemListener<ApiResult<Void>> listener);
}
