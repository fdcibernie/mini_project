package com.example.consumerbill.bill_info.presentation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerbill.R;
import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.billers.presentation.adapters.BillerAdapter;
import com.example.consumerbill.cores.interfaces.IAppItemListener;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerVH> {

    private ArrayList<ConsumerBill> list;
    private Context context;
    private IAppItemListener<ConsumerBill> listener;
    public CustomerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerAdapter.CustomerVH(LayoutInflater.from(context).inflate(R.layout.customer_adapter_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerVH holder, int position) {
        ConsumerBill bill = list.get(position);
        holder.tvCustomer.setText(bill.getName());
        holder.tvAccountNo.setText(bill.getAccountNo());
        holder.tvBillAmount.setText(String.valueOf(bill.getBillAmount()));
        holder.view.setOnClickListener(v -> {
            listener.onAppItemSelected(bill);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUpAdapter(ArrayList<ConsumerBill> list, IAppItemListener<ConsumerBill> listener) {
        this.list = list;
        this.listener = listener;
        notifyDataSetChanged();
    }

    public static class CustomerVH extends RecyclerView.ViewHolder {
        TextView tvCustomer,tvAccountNo,tvBillAmount;
        View view;
        public CustomerVH(@NonNull View itemView) {
            super(itemView);
            tvCustomer = itemView.findViewById(R.id.tv_customer_name);
            tvAccountNo = itemView.findViewById(R.id.tv_account_no);
            tvBillAmount = itemView.findViewById(R.id.tv_bill_amount);
            view = itemView;
        }
    }
}
