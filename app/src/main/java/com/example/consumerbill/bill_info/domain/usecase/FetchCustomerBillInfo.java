package com.example.consumerbill.bill_info.domain.usecase;

import com.example.consumerbill.bill_info.data.repository.BillInfoRepositoryImpl;
import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAppItemListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;

public class FetchCustomerBillInfo {

    private static FetchCustomerBillInfo instance = null;
    private final BillInfoRepositoryImpl billInfoRepository;

    public FetchCustomerBillInfo() {
        billInfoRepository = BillInfoRepositoryImpl.getInstance();
    }

    public static synchronized FetchCustomerBillInfo getInstance() {
        if(instance == null) {
            instance = new FetchCustomerBillInfo();
        }
        return instance;
    }

//    public CompletableFuture<ApiResult<ArrayList<ConsumerBill>>> searchConsumer(VolleySingleton volleySingleton, String text, String id) {
//        return billInfoRepository.searchConsumer(volleySingleton, text, id);
//    }

    public void searchConsumer(VolleySingleton volleySingleton, String text,String id, IAppItemListener<ApiResult<ArrayList<ConsumerBill>>> listener) {
        billInfoRepository.searchConsumer(volleySingleton, text, id, listener);
    }
}
