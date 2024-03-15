package com.example.consumerbill.billers.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerbill.R;

public class BillerAdapter extends RecyclerView.Adapter<BillerAdapter.BillerViewHolder> {

    private Context context;

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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BillerViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBiller;
        public BillerViewHolder(@NonNull View itemView) {
            super(itemView);

            ivBiller = itemView.findViewById(R.id.iv_biller);
        }

    }
}
