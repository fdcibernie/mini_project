package com.example.consumerbill.billers.domain.usecase;
import android.util.Log;

import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.HelperClass;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.interfaces.IApiListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class DeserializeBillerResponse extends HelperClass<Billers> {
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
    public ArrayList<Billers> deserializeJsonObjectType(JSONObject data) {
        return deserializeBillers(data) ;
    }

    private ArrayList<Billers> deserializeBillers(JSONObject data) {
        Iterator<String> iterator = data.keys();
        ArrayList<Billers> listBiller = new ArrayList<>();
        while (iterator.hasNext()) {
            try {
                JSONObject jsonObject = data.getJSONObject(iterator.next());
                Billers billers = new Billers(
                        jsonObject.getString("biller_code"),
                        jsonObject.getString("biller_complete_name"),
                        jsonObject.getString("biller_name"),
                        jsonObject.getString("biller_status"),
                        jsonObject.getString("biller_type")
                );
                listBiller.add(billers);
            } catch (JSONException e) {
                Log.e("Here","Exception Json:"+e.getMessage());
            }
        }

        return listBiller;
    }
}
