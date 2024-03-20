package com.example.consumerbill.bill_info.data.datasource.remote;

import android.util.Log;

import com.example.consumerbill.bill_info.data.datasource.model.ConsumerModel;
import com.example.consumerbill.bill_info.domain.model.ConsumerBill;
import com.example.consumerbill.billers.domain.model.Billers;
import com.example.consumerbill.billers.domain.usecase.DeserializeBillerResponse;
import com.example.consumerbill.cores.ConstRef;
import com.example.consumerbill.cores.HelperClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DeserializeConsumerBill extends HelperClass<ConsumerBill> {
    private static DeserializeConsumerBill instance = null;

    public static synchronized DeserializeConsumerBill getInstance() {
        if(instance == null) {
            instance = new DeserializeConsumerBill();
        }
        return instance;
    }
    public DeserializeConsumerBill() {
    }

    @Override
    public ArrayList<ConsumerBill> deserializeJsonObjectType(JSONObject data) {
        return deserializeConsumerBill(data);
    }

    private ArrayList<ConsumerBill> deserializeConsumerBill(JSONObject data) {
        Iterator<String> iterator = data.keys();
        ArrayList<ConsumerBill> listBiller = new ArrayList<>();
        while (iterator.hasNext()) {
            try {
                String keys = iterator.next();
                JSONObject jsonObject = data.getJSONObject(keys);
                String paymentStatus = jsonObject.optString("payment_status","");
                String STATUS_PAYMENT = "paid";
                if(!paymentStatus.trim().equalsIgnoreCase(STATUS_PAYMENT)) {
                    ConsumerBill consumerBill = new ConsumerModel(
                            jsonObject.getString("account_no"),
                            jsonObject.getString("address"),
                            jsonObject.getDouble("bill_amount"),
                            jsonObject.getDouble("bill_penalty"),
                            jsonObject.getString("biller"),
                            jsonObject.optString("consumption","0"),
                            jsonObject.getString("contact_num"),
                            jsonObject.getString("due_date"),
                            jsonObject.getString("name"),
                            jsonObject.optString("payment_status","0"),
                            jsonObject.optString("present_reading","0"),
                            jsonObject.optString("prev_reading","0"),
                            keys
                    );
                    listBiller.add(consumerBill);
                }
            } catch (JSONException e) {
                Log.e("Here","Exception Json:"+e.getMessage());
            }
        }

        return listBiller;
    }
}
