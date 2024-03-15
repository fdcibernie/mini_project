package com.example.consumerbill.billers.presentation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.consumerbill.R;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.billers.presentation.viewmodel.BillerViewModel;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.views.AppLoader;
import com.example.consumerbill.cores.volley.VolleySingleton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;


public class BillersFragment extends Fragment {
    private BillerViewModel billerViewModel;
    private VolleySingleton volleySingleton;
    private AppLoader appLoader;
    private AlertDialog alertDialog;

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
        appLoader = AppLoader.getInstance(requireActivity(),getResources());
        volleySingleton = VolleySingleton.getInstance(requireContext());
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

    private void observeBillersData() {
        billerViewModel.getLiveBillersData().observe(requireActivity(), billersApiResult -> {
            requireActivity().runOnUiThread(() ->
                    //appLoader.dismissLoader()
                    alertDialog.dismiss()
            );
        });
    }
}