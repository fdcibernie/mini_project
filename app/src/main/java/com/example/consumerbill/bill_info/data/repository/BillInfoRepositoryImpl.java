package com.example.consumerbill.bill_info.data.repository;

import com.example.consumerbill.bill_info.data.datasource.remote.BillInfoDataScrImpl;
import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.bill_info.domain.repository.IBillInfoRepository;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAppItemListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class BillInfoRepositoryImpl implements IBillInfoRepository {

    private static BillInfoRepositoryImpl instance = null;
    private final BillInfoDataScrImpl billInfoDataScr;
    public BillInfoRepositoryImpl() {
        billInfoDataScr = BillInfoDataScrImpl.getInstance();
    }

    public static synchronized BillInfoRepositoryImpl getInstance() {
        if(instance == null) {
            instance = new BillInfoRepositoryImpl();
        }

        return instance;
    }

    @Override
    public void searchConsumer(VolleySingleton volleySingleton, String text, String id, IAppItemListener<ApiResult<ArrayList<ConsumerBill>>> listener) {
        billInfoDataScr.searchConsumer(volleySingleton, text, id, listener);
    }

    @Override
    public void updatePaymentStatus(VolleySingleton volleySingleton, String key, IAppItemListener<ApiResult<Void>> listener) {
        billInfoDataScr.updatePaymentStatus(volleySingleton, key, listener);
    }
}
