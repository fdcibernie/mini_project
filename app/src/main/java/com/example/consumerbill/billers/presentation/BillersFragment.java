package com.example.consumerbill.billers.presentation;

import static com.example.consumerbill.bill_info.presentation.CustomerBillFragment.ARG_BILLER_ID;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.consumerbill.R;
import com.example.consumerbill.bill_info.presentation.CustomerBillFragment;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.billers.presentation.adapters.BillerAdapter;
import com.example.consumerbill.billers.presentation.viewmodel.BillerViewModel;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.views.AppLoader;
import com.example.consumerbill.cores.views.MessageDialog;
import com.example.consumerbill.cores.volley.VolleySingleton;
import com.example.consumerbill.databinding.FragmentBillersBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Objects;


public class BillersFragment extends Fragment {
    private BillerViewModel billerViewModel;
    private VolleySingleton volleySingleton;
    private AppLoader appLoader;
    private AlertDialog alertDialog;
    private RecyclerView recyclerView;
    private FragmentBillersBinding layout;
    private MessageDialog messageDialog;

    public BillersFragment() {
    }

    public static BillersFragment newInstance() {
        BillersFragment fragment = new BillersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        billerViewModel = new ViewModelProvider(requireActivity()).get(BillerViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_billers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = FragmentBillersBinding.bind(view);

        appLoader = AppLoader.getInstance(requireActivity(),getResources());
        volleySingleton = VolleySingleton.getInstance(requireContext());
        recyclerView = layout.rcyBillers;
        createDialog();
        fetchBillers();
        observeBillersData();
    }
    private void createDialog(){
        @SuppressLint("UseCompatLoadingForDrawables")
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity())
                .setView(R.layout.app_loader)
                .setCancelable(false)
                .setBackground(getResources().getDrawable(R.color.white,null));

        alertDialog = builder.create();
    }

    private void fetchBillers() {
        //appLoader.showLoader();
        //Log.e("Here", String.valueOf("is alert null:"+appLoader == null));
        alertDialog.show();
        new Thread(() -> billerViewModel.fetchBillers(volleySingleton)).start();
    }

    private void setUpRecyclerView(ArrayList<Billers> list) {
        BillerAdapter adapter = new BillerAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setUpAdapter(list, biller -> {
            goToCustomerBill(biller.getBillerCode());
        });
    }

    private void goToCustomerBill(String billerCode) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_BILLER_ID,billerCode);
        FragmentManager manager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                )
                .setReorderingAllowed(true)
                .replace(R.id.main_container, CustomerBillFragment.class,bundle)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void observeBillersData() {
        billerViewModel.getLiveBillersData().observe(requireActivity(), billersApiResult -> {
            requireActivity().runOnUiThread(() ->
                    alertDialog.dismiss()
            );

            if(billersApiResult.getApiStatus() == ResponseStatus.SUCCESS && billersApiResult.getData() != null) {
                setUpRecyclerView(billersApiResult.getData());
            }else{
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity())
                        .setMessage(billersApiResult.getErrorMessage())
                        .setTitle("Failed")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}