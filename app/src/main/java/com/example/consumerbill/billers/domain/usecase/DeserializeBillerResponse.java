package com.example.consumerbill.billers.domain.usecase;
import android.util.Log;

import com.example.consumerbill.R;
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
                String billerCode = jsonObject.getString("biller_code");
                Billers billers = new Billers(
                        billerCode,
                        jsonObject.getString("biller_complete_name"),
                        jsonObject.getString("biller_name"),
                        jsonObject.getString("biller_status"),
                        jsonObject.getString("biller_type"),
                        getImageFromDrawable(billerCode)
                );
                listBiller.add(billers);
            } catch (JSONException e) {
                Log.e("Here","Exception Json:"+e.getMessage());
            }
        }

        return listBiller;
    }

    private int getImageFromDrawable(String code) {
        switch (code) {
            case "RISE":
                return R.drawable.rise;
            case "CNV":
                return R.drawable.cnv;
            case "GLB":
                return R.drawable.glb;
            case "PLDT":
                return R.drawable.pldt;
            case "MNWD":
                return R.drawable.mnwd;
            case "CCWD":
                return R.drawable.ccwd;
            case "ISAWAD":
                return R.drawable.isawad;
            case "MCWD":
                return R.drawable.mcwd;
            case "CB2":
                return R.drawable.cb2;
            case "NRC2":
                return R.drawable.nrc2;
            case "CB1":
                return R.drawable.cb1;
            case "VECO":
                return R.drawable.veco;
            default:
                return R.drawable.layout_bg_color;
        }
    }
}
