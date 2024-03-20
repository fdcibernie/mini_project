package com.example.consumerbill.cores.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MessageDialog {
    @SuppressLint("StaticFieldLeak")
    private static MessageDialog instance = null;
    private Context context;
    //private final AlertDialog alertDialog;
    private final MaterialAlertDialogBuilder builder;

    public MessageDialog(Context context,String message,String tittle) {
         builder = new MaterialAlertDialogBuilder(context)
                .setMessage(message)
                .setTitle(tittle);
        //alertDialog = builder.create();
    }

    public static MessageDialog getInstance(Context context,String message,String tittle) {
        if(instance == null) {
            instance = new MessageDialog(context,message,tittle);
        }

        return instance;
    }

//    public void show() {
//        alertDialog.show();
//    }
//
//    public void setPositiveButton(DialogInterface.OnClickListener listener){
//        builder.setPositiveButton("OK", (dialog, which) -> {
//            dialog.dismiss();
//            listener.onClick(dialog,which);
//        });
//    }

    public MaterialAlertDialogBuilder getBuilder() {
        return builder;
    }

}
