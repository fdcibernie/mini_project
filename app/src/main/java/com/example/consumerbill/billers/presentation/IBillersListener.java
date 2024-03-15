package com.example.consumerbill.billers.presentation;

import com.example.consumerbill.billers.domain.model.Billers;

public interface IBillersListener {
    void onSelectedBiller(Billers biller);
}
