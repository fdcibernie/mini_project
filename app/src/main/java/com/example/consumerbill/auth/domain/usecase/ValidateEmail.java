package com.example.consumerbill.auth.domain.usecase;

import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.example.consumerbill.cores.ValidationClass;
import com.example.consumerbill.cores.interfaces.ITextValidation;

public class ValidateEmail extends ValidationClass {
    private static ValidateEmail instance = null;
    private ITextValidation callback;

    public ValidateEmail(ITextValidation callback) {
        super();
        this.callback = callback;
    }

    public static synchronized ValidateEmail getInstance(ITextValidation callback) {
        if(instance == null) {
            instance = new ValidateEmail(callback);
        }
        return instance;
    }

    @Override
    public void checkText(int id,String text) {
        if(text.isEmpty()) {
            callback.textReviewResult(id,"Please provide email address.",true);
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            callback.textReviewResult(id,"Email is invalid.",true);
            return;
        }

        callback.textReviewResult(id,"Good",false);
    }
}
