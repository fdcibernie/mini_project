package com.example.consumerbill.bill_info.presentation;

import static com.example.consumerbill.bill_info.presentation.BillInfoFragment.ARG_BILLER_CODE;
import static com.example.consumerbill.bill_info.presentation.BillInfoFragment.ARG_CONSUMER_BILL;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.consumerbill.R;
import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.bill_info.presentation.adapter.CustomerAdapter;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.interfaces.IAppItemListener;
import com.example.consumerbill.cores.views.AppLoader;
import com.example.consumerbill.cores.volley.VolleySingleton;
import com.example.consumerbill.databinding.FragmentCustomerBillBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Objects;


public class CustomerBillFragment extends Fragment {
    public static final String ARG_BILLER_ID = "biller_id";
    public static final String ARG_BILLER_NAME = "biller_name";
    private String billerId,billerName;
    private String queryText;
    private FragmentCustomerBillBinding layout;
    private BillInfoViewModel viewModel;
    private AlertDialog alertDialog;
    private VolleySingleton volleySingleton;

    public CustomerBillFragment() {
        // Required empty public constructor
    }
    public static CustomerBillFragment newInstance(String param1, String param2) {
        CustomerBillFragment fragment = new CustomerBillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BILLER_ID, param1);
        args.putString(ARG_BILLER_NAME, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            billerId = getArguments().getString(ARG_BILLER_ID);
            billerName = getArguments().getString(ARG_BILLER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = FragmentCustomerBillBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(BillInfoViewModel.class);
        volleySingleton = VolleySingleton.getInstance(requireContext());

        ((AppCompatActivity) requireActivity()).setSupportActionBar(layout.toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Customer");
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        layout.tvBillerName.setText(billerName);

        createDialog();
        popFragment();
        onSearchView();
        observeCustomerInfo();
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
                    .remove(new CustomerBillFragment())
                    .commit();
            fragmentManager.popBackStack();
        });
    }

    private void createDialog(){
        @SuppressLint("UseCompatLoadingForDrawables")
        AppLoader appLoader = new AppLoader(requireActivity(),getResources());
        alertDialog = appLoader.getBuilder().create();
    }

    private void onSearchView() {
        layout.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.isEmpty()) {
                    prepareAdapter(new ArrayList<>());
                }else{
                    fetchCustomer(newText);
                }
                return false;
            }
        });
    }

    private void fetchCustomer(String text) {
        viewModel.fetchCustomerBillInfo(volleySingleton,text,billerId);
    }

    private void observeCustomerInfo() {
        viewModel.getLiveBillInfo().observe(requireActivity(), arrayListApiResult -> {
            if(arrayListApiResult.getApiStatus() == ResponseStatus.SUCCESS) {
              prepareAdapter(arrayListApiResult.getData());
            }
        });
    }

    private void prepareAdapter(ArrayList<ConsumerBill> list) {
        CustomerAdapter adapter = new CustomerAdapter(requireContext());
        layout.rcyListSearch.setAdapter(adapter);
        layout.rcyListSearch.setHasFixedSize(true);
        adapter.setUpAdapter(list, data -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ARG_CONSUMER_BILL,data);
            bundle.putString(ARG_BILLER_CODE,billerId);
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    )
                    .setReorderingAllowed(true)
                    .replace(R.id.main_container, BillInfoFragment.class,bundle)
                    .addToBackStack(null);
            fragmentTransaction.commit();
        });
    }
}