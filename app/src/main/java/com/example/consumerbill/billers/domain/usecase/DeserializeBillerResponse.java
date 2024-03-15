package com.example.consumerbill.billers.domain.usecase;
import android.util.Log;

import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.HelperClass;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.interfaces.IApiListener;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DeserializeBillerResponse extends HelperClass {
    private static DeserializeBillerResponse instance = null;
    public static synchronized DeserializeBillerResponse getInstance() {
        if(instance == null) {
            instance = new DeserializeBillerResponse();
        }
        return instance;
    }
    public DeserializeBillerResponse() {
    }

    @Override
    public void deserializeJsonObject(JSONObject data, IApiListener<ArrayList<Billers>> apiListener) {
        super.deserializeJsonObject(data, apiListener);
        Iterator<String> iterator = data.keys();
        ArrayList<Billers> listBiller = new ArrayList<>();
        while (iterator.hasNext()) {
            try {
                JSONObject jsonObject = data.getJSONObject(iterator.next());
                //{"biller_code":"VECO","biller_complete_name":"Visayan Electric Cooperative","biller_name":"VECO","biller_status":"active","biller_type":"Electricity"}
                Billers billers = new Billers(
                        jsonObject.getString("biller_code"),
                        jsonObject.getString("biller_complete_name"),
                        jsonObject.getString("biller_name"),
                        jsonObject.getString("biller_status"),
                        jsonObject.getString("biller_type")
                        );

                Log.e("Here","biller:"+jsonObject.getString("biller_code"));
                listBiller.add(billers);
            } catch (JSONException e) {
                Log.e("Here","Exception Json:"+e.getMessage());
            }
        }

        apiListener.onApiResponse(new ApiResult<>(listBiller, ResponseStatus.SUCCESS,""));
    }
}
