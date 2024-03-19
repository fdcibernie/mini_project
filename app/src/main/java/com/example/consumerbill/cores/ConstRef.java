package com.example.consumerbill.cores;

import com.google.android.gms.wallet.WalletConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConstRef {
    public static String IS_LOGIN = "isLogin";
    public static String FIREBASE_DOMAIN = "https://consumer-bill-default-rtdb.asia-southeast1.firebasedatabase.app";
    public static final int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;
    public static final String DIRECT_TOKENIZATION_PUBLIC_KEY = "REPLACE_ME";
    public static final String COUNTRY_CODE = "PH";
    public static final String CURRENCY_CODE = "PHP";
    public static final List<String> SHIPPING_SUPPORTED_COUNTRIES = Arrays.asList("US", "GB","PH");
    public static final HashMap<String, String> DIRECT_TOKENIZATION_PARAMETERS =
            new HashMap<String, String>() {{
                put("protocolVersion", "ECv2");
                put("publicKey", DIRECT_TOKENIZATION_PUBLIC_KEY);
            }};
    public static final List<String> SUPPORTED_NETWORKS = Arrays.asList(
            "AMEX",
            "DISCOVER",
            "JCB",
            "MASTERCARD",
            "VISA");
    public static final List<String> SUPPORTED_METHODS = Arrays.asList(
            "PAN_ONLY",
            "CRYPTOGRAM_3DS");
}
