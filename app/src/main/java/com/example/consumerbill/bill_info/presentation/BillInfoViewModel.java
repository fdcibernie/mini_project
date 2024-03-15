package com.example.consumerbill.bill_info.presentation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.bill_info.domain.usecase.FetchCustomerBillInfo;
import com.example.consumerbill.billers.domain.usecase.FetchBillersUseCase;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAppItemListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BillInfoViewModel extends ViewModel {

    private final MutableLiveData<ApiResult<ArrayList<ConsumerBill>>> apiResultMutableLiveData = new MutableLiveData<>();
    private final FetchCustomerBillInfo fetchCustomerBillInfo = FetchCustomerBillInfo.getInstance();

    public LiveData<ApiResult<ArrayList<ConsumerBill>>> getLiveBillInfo() {
        return apiResultMutableLiveData;
    }

    public void fetchCustomerBillInfo(VolleySingleton volleySingleton, String text, String id) {

        fetchCustomerBillInfo.searchConsumer(volleySingleton,text,id, apiResultMutableLiveData::postValue);


//        Future<ApiResult<ArrayList<ConsumerBill>>> futureBillInfo = fetchCustomerBillInfo.searchConsumer(volleySingleton, text, id);
//        try {
//            ApiResult<ArrayList<ConsumerBill>> apiResult = futureBillInfo.get();
//            apiResultMutableLiveData.postValue(apiResult);
//        } catch (ExecutionException e) {
//            Log.e("BillInfoViewModel","ExecutionException:"+e.getMessage());
//        } catch (InterruptedException e) {
//            Log.e("BillInfoViewModel","InterruptedException:"+e.getMessage());
//        }
    }
}
