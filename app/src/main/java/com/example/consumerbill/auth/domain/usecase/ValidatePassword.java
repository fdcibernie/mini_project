package com.example.consumerbill.auth.domain.usecase;

import android.view.View;

import com.example.consumerbill.cores.ValidationClass;
import com.example.consumerbill.cores.interfaces.ITextValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatePassword extends ValidationClass {
    private View view;
    private ITextValidation callback;
    private static ValidatePassword instance = null;

    public static ValidatePassword getInstance(ITextValidation callback,View view) {
        if (instance == null) {
            instance = new ValidatePassword(callback, view);
        }

        return instance;
    }

    public ValidatePassword(ITextValidation callback,View view) {
        super();
        this.view = view;
        this.callback = callback;
    }

    @Override
    public void checkText(String text) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(text);

        if(text.isEmpty()) {
            callback.textReviewResult(view,"Please provide password.",true);
            return;
        }

        if(text.length() < 8) {
            callback.textReviewResult(view,"Password should be 8 characters.",true);
            return;
        }

        if(!matcher.matches()) {
            callback.textReviewResult(view,"Password should contain at least 1 number, capital letter, and special characters.",true);
            return;
        }

        callback.textReviewResult(view,"Good",false);
    }
}
