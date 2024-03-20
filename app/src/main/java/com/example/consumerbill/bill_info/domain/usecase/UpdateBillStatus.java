package com.example.consumerbill.bill_info.domain.usecase;

import com.example.consumerbill.bill_info.data.repository.BillInfoRepositoryImpl;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAppItemListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

public class UpdateBillStatus {
    private static UpdateBillStatus instance = null;
    private final BillInfoRepositoryImpl billInfoRepository;

    public UpdateBillStatus() {
        billInfoRepository = BillInfoRepositoryImpl.getInstance();
    }

    public static synchronized UpdateBillStatus getInstance() {
        if(instance == null) {
            instance = new UpdateBillStatus();
        }
        return instance;
    }

    public void updatePaymentStatus(VolleySingleton volleySingleton,String biller, String key, IAppItemListener<ApiResult<Void>> listener) {
        billInfoRepository.updatePaymentStatus(volleySingleton,biller, key, listener);
    }
}
