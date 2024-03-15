package com.example.consumerbill.billers.domain.repository;

import com.example.consumerbill.billers.data.datasource.remote.model.BillerModel;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.cores.interfaces.IApiListener;
import com.example.consumerbill.cores.volley.VolleySingleton;

import java.util.ArrayList;

public interface IBillersRepository {
    void fetchBillers(VolleySingleton volleySingleton, IApiListener<ArrayList<Billers>> apiListener);
}
