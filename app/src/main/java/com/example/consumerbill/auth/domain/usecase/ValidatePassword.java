package com.example.consumerbill.auth.domain.usecase;

import android.view.View;

import com.example.consumerbill.cores.ValidationClass;
import com.example.consumerbill.cores.interfaces.ITextValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatePassword extends ValidationClass {
    private static ValidatePassword instance = null;
    private ITextValidation callback;

    public static ValidatePassword getInstance(ITextValidation callback) {
        if (instance == null) {
            instance = new ValidatePassword(callback);
        }

        return instance;
    }

    public ValidatePassword(ITextValidation callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void checkText(int id,String text) {
        String passwordPattern = "^(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(text);

        if(text.isEmpty()) {
            callback.textReviewResult(id,"Please provide password.",true);
            return;
        }

        if(text.length() < 8) {
            callback.textReviewResult(id,"Password should be 8 characters.",true);
            return;
        }

        if(!matcher.matches()) {
            callback.textReviewResult(id,"Password should contain at least 1 number, capital letter, and special characters.",true);
            return;
        }

        callback.textReviewResult(id,"Good",false);
    }
}
