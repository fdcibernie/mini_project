package com.example.consumerbill.cores.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AlertDialog;

import com.example.consumerbill.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AppLoader {
    private final Context context;
    private final Resources resources;
    private AlertDialog alertDialog;
    MaterialAlertDialogBuilder builder;

    @SuppressLint("StaticFieldLeak")
    private static AppLoader instance = null;
    public AppLoader(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;
        createDialog();
    }

    public static AppLoader getInstance(Context context, Resources resources) {
        if(instance == null) {
            instance = new AppLoader(context, resources);
        }
        return instance;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createDialog() {
       builder = new MaterialAlertDialogBuilder(context)
                .setView(R.layout.app_loader)
                .setCancelable(false)
                .setBackground(resources.getDrawable(R.color.white,null));

        alertDialog = builder.create();
    }

    public void showLoader() {
        alertDialog.show();
    }

    public void dismissLoader() {
        alertDialog.dismiss();
    }

    public MaterialAlertDialogBuilder getBuilder() {
        return builder;
    }
}
