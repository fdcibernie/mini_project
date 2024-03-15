package com.example.consumerbill.billers.data.repository;

import android.content.Context;

import com.example.consumerbill.billers.data.datasource.remote.BillerDataSrcImpl;
import com.example.consumerbill.billers.data.datasource.remote.model.BillerModel;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.billers.domain.repository.IBillersRepository;
import com.example.consumerbill.cores.interfaces.IApiListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;

public class BillersRepositoryImpl implements IBillersRepository {

    private final BillerDataSrcImpl billerDataSrc;

    private static BillersRepositoryImpl instance = null;

    public BillersRepositoryImpl() {
        billerDataSrc = BillerDataSrcImpl.getInstance();
    }

    public static synchronized BillersRepositoryImpl getInstance() {
        if(instance == null) {
            instance = new BillersRepositoryImpl();
        }

        return instance;
    }

    @Override
    public void fetchBillers(VolleySingleton volleySingleton, IApiListener<ArrayList<Billers>> apiListener) {
        billerDataSrc.fetchBillers(volleySingleton,apiListener);
    }
}
