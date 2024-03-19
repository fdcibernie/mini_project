package com.example.consumerbill.bill_info.domain.usecase;

import com.example.consumerbill.cores.HelperClass;

import java.text.DecimalFormat;

public class NumberFormat extends HelperClass<String> {

    private static NumberFormat instance = null;

    public static synchronized NumberFormat getInstance() {
        if(instance == null) {
            instance = new NumberFormat();
        }
        return instance;
    }

    @Override
    public String formatDecimal(String numberStr) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(Double.parseDouble(numberStr));
    }
}
