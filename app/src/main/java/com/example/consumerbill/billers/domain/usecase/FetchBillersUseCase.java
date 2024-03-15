package com.example.consumerbill.billers.domain.usecase;

import android.content.Context;

import com.example.consumerbill.billers.data.datasource.remote.model.BillerModel;
import com.example.consumerbill.billers.data.repository.BillersRepositoryImpl;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.cores.interfaces.IApiListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;

public class FetchBillersUseCase {

    private final BillersRepositoryImpl repository;
    private static FetchBillersUseCase instance = null;

    public FetchBillersUseCase() {
        repository = BillersRepositoryImpl.getInstance();
    }

    public static synchronized FetchBillersUseCase getInstance() {
        if(instance == null) {
            instance = new FetchBillersUseCase();
        }

        return instance;
    }

    public void fetchBillers(VolleySingleton volleySingleton, IApiListener<ArrayList<Billers>> apiListener) {
        repository.fetchBillers(volleySingleton,apiListener);
    }
}
