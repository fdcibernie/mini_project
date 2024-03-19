package com.example.consumerbill.bill_info.presentation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.consumerbill.R;
import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.bill_info.domain.usecase.NumberFormat;
import com.example.consumerbill.cores.payment_util.PaymentsUtil;
import com.example.consumerbill.databinding.FragmentBillInfoBinding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.button.ButtonConstants;
import com.google.android.gms.wallet.button.ButtonOptions;
import com.google.android.gms.wallet.button.PayButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;


public class BillInfoFragment extends Fragment {

    public static final String ARG_CONSUMER_BILL = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ConsumerBill billInfo;
    private String billerInfo;

    private FragmentBillInfoBinding layout;
    private NumberFormat numberFormat;
    private PayButton payButton;
    private Button btnClose;
    private CheckOutViewModel checkOutViewModel;
    private AlertDialog alertDialog;

    ActivityResultLauncher<IntentSenderRequest> resolvePaymentForResult = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        Intent resultData = result.getData();
                        if (resultData != null) {
                            PaymentData paymentData = PaymentData.getFromIntent(result.getData());
                            if (paymentData != null) {
                                handlePaymentSuccess(paymentData);
                            }
                        }
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;
                }
            });

    public BillInfoFragment() {
        // Required empty public constructor
    }
    public static BillInfoFragment newInstance(String param1, String param2) {
        BillInfoFragment fragment = new BillInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONSUMER_BILL, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            billInfo = getArguments().getParcelable(ARG_CONSUMER_BILL);
            billerInfo = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = FragmentBillInfoBinding.bind(view);
        initializeMembers();
        bindUIData();
        popFragment();
        setUpPaymentButton();
        observeCanUseGooglePay();
        createDialog();
        closeThisPage();
    }
    private void initializeMembers() {

        ((AppCompatActivity) requireActivity()).setSupportActionBar(layout.toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Information");
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        checkOutViewModel = new ViewModelProvider(this).get(CheckOutViewModel.class);

        payButton = layout.googlePayPaymentButton;
        btnClose = layout.btnClose;
        numberFormat = NumberFormat.getInstance();

        payButton.setOnClickListener(this::requestPayment);
    }


    private void createDialog(){
        @SuppressLint("UseCompatLoadingForDrawables")
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity())
                .setView(R.layout.app_loader)
                .setCancelable(false)
                .setBackground(getResources().getDrawable(R.color.white,null));

        alertDialog = builder.create();
    }

    private void bindUIData() {

        String formattedTotalAmount = numberFormat.formatDecimal(String.valueOf(billInfo.getBillAmount() + billInfo.getBillPenalty()));
        String formattedBillAmount = numberFormat.formatDecimal(String.valueOf(billInfo.getBillAmount()));

        layout.tvAccountNo.setText(billInfo.getAccountNo());
        layout.tvAccountName.setText(billInfo.getName());
        layout.tvBillAmount.setText(String.valueOf(formattedBillAmount));
        layout.tvDueDate.setText(billInfo.getDueDate());
        layout.tvPenalty.setText(String.valueOf(billInfo.getBillPenalty()));
        layout.tvTotalAmount.setText(String.valueOf(formattedTotalAmount));
    }
    private void popFragment() {
        layout.toolbar.setNavigationOnClickListener(v->{
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    )
                    .remove(new BillInfoFragment())
                    .commit();
            fragmentManager.popBackStack();
        });
    }

    private void setUpPaymentButton() {

        try {
          ButtonOptions buttonOptions =  ButtonOptions.newBuilder()
                    .setAllowedPaymentMethods(PaymentsUtil.getAllowedPaymentMethods().toString())
                  .setButtonType(ButtonConstants.ButtonType.PAY)
                  .build();
          payButton.initialize(buttonOptions);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void observeCanUseGooglePay() {
        checkOutViewModel.canUseGooglePay.observe(requireActivity(), canUse -> {
            if(canUse) {
                payButton.setVisibility(View.VISIBLE);
            }else{
                payButton.setVisibility(View.GONE);
            }
        });
    }

    private void requestPayment(View view) {
        alertDialog.show();
        payButton.setClickable(false);
        long totalAmountToPay = (long) (billInfo.getBillAmount() + billInfo.getBillPenalty());
        final Task<PaymentData> task = checkOutViewModel.getLoadPaymentDataTask(totalAmountToPay);
        task.addOnCompleteListener(completedTask -> {
            if (completedTask.isSuccessful()) {
                handlePaymentSuccess(completedTask.getResult());
            } else {
                Exception exception = completedTask.getException();
                if (exception instanceof ResolvableApiException) {
                    PendingIntent resolution = ((ResolvableApiException) exception).getResolution();
                    resolvePaymentForResult.launch(new IntentSenderRequest.Builder(resolution).build());

                } else if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    handleError(apiException.getStatusCode(), apiException.getMessage());

                } else {
                    handleError(CommonStatusCodes.INTERNAL_ERROR, "Unexpected non API" +
                            " exception when trying to deliver the task result to an activity!");
                }
            }

            // Re-enables the Google Pay payment button.
            payButton.setClickable(true);
            alertDialog.dismiss();
        });
    }
    private void handleError(int statusCode, @Nullable String message) {
        Log.e("loadPaymentData failed",
                String.format(Locale.getDefault(), "Error code: %d, Message: %s", statusCode, message));
    }
    private void handlePaymentSuccess(PaymentData paymentData) {
        final String paymentInfo = paymentData.toJson();

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    requireActivity(), getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token", paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token"));

            //startActivity(new Intent(this, CheckoutSuccessActivity.class));

        } catch (JSONException e) {
            Log.e("handlePaymentSuccess", "Error: " + e);
        }
    }

    private void closeThisPage() {
        btnClose.setOnClickListener(v -> {
            popFragment();
        });
    }
}