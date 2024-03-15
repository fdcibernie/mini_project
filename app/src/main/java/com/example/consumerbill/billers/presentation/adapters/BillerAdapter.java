package com.example.consumerbill.billers.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerbill.R;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.billers.presentation.IBillersListener;

import java.util.ArrayList;

public class BillerAdapter extends RecyclerView.Adapter<BillerAdapter.BillerViewHolder> {

    private Context context;
    private ArrayList<Billers> list;
    private IBillersListener listener;

    public BillerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BillerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillerViewHolder(LayoutInflater.from(context).inflate(R.layout.biller_adapter_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillerViewHolder holder, int position) {
        Billers biller = list.get(position);
        holder.view.setOnClickListener(v -> {
            listener.onSelectedBiller(biller);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUpAdapter(ArrayList<Billers> list, IBillersListener listener) {
        this.list = list;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BillerViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBiller;
        View view;
        public BillerViewHolder(@NonNull View itemView) {
            super(itemView);

            ivBiller = itemView.findViewById(R.id.iv_biller);
            view = itemView;
        }
    }
}
