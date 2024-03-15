package com.example.consumerbill.bill_info.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.consumerbill.R;
import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.databinding.FragmentBillInfoBinding;

import java.util.Objects;


public class BillInfoFragment extends Fragment {

    public static final String ARG_CONSUMER_BILL = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ConsumerBill billInfo;
    private String billerInfo;

    private FragmentBillInfoBinding layout;

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
        ((AppCompatActivity) requireActivity()).setSupportActionBar(layout.toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Information");
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        bindUIData();
        popFragment();
    }

    private void bindUIData() {
        layout.tvAccountNo.setText(billInfo.getAccountNo());
        layout.tvAccountName.setText(billInfo.getName());
        layout.tvBillAmount.setText(String.valueOf(billInfo.getBillAmount()));
        layout.tvDueDate.setText(billInfo.getDueDate());
        layout.tvPenalty.setText(String.valueOf(billInfo.getBillPenalty()));
        layout.tvTotalAmount.setText(String.valueOf(billInfo.getBillAmount()));
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

}