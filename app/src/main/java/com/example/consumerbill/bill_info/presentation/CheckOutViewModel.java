package com.example.consumerbill.bill_info.presentation;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.consumerbill.cores.payment_util.PaymentsUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;

import org.json.JSONObject;

import java.util.Objects;

public class CheckOutViewModel extends AndroidViewModel {
    // A client for interacting with the Google Pay API.
    private final PaymentsClient paymentsClient;

    // LiveData with the result of whether the user can pay using Google Pay
    private final MutableLiveData<Boolean> _canUseGooglePay = new MutableLiveData<>();

    public final LiveData<Boolean> canUseGooglePay = _canUseGooglePay;
    public CheckOutViewModel(@NonNull Application application) {
        super(application);
        paymentsClient = PaymentsUtil.createPaymentsClient(application);

        fetchCanUseGooglePay();
    }

    private void fetchCanUseGooglePay() {
        final JSONObject isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        Log.e("fetchCanUseGooglePay", String.valueOf(isReadyToPayJson));
        if (isReadyToPayJson == null) {
            _canUseGooglePay.setValue(false);
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString());
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(
                completedTask -> {
                    if (completedTask.isSuccessful()) {
                        _canUseGooglePay.setValue(completedTask.getResult());
                    } else {
                        Log.e("isReadyToPay failed", String.valueOf(Objects.requireNonNull(completedTask.getException())));
                        _canUseGooglePay.setValue(false);
                    }
                });
    }

    public Task<PaymentData> getLoadPaymentDataTask(final long priceCents) {
        JSONObject paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents);
        if (paymentDataRequestJson == null) {
            return null;
        }

        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.toString());
        return paymentsClient.loadPaymentData(request);
    }
}
