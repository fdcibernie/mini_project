package com.example.consumerbill.billers.presentation.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.billers.domain.usecase.FetchBillersUseCase;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;

public class BillerViewModel extends ViewModel {
    private final MutableLiveData<ApiResult<ArrayList<Billers>>> liveBillersData = new MutableLiveData<>();
    public LiveData<ApiResult<ArrayList<Billers>>> getLiveBillersData() {
        return liveBillersData;
    }

    private final FetchBillersUseCase fetchBillersUseCase;

    public BillerViewModel() {
        this.fetchBillersUseCase = FetchBillersUseCase.getInstance();
    }


    public void fetchBillers(VolleySingleton volleySingleton) {
        fetchBillersUseCase.fetchBillers(volleySingleton,liveBillersData::postValue);
    }
}
