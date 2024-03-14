package com.example.consumerbill.auth.domain.usecase;

import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.example.consumerbill.cores.ValidationClass;
import com.example.consumerbill.cores.interfaces.ITextValidation;

public class ValidateEmail extends ValidationClass {
    private static ValidateEmail instance = null;
    private ITextValidation callback;
    private final View view;

    public ValidateEmail(ITextValidation callback,View view) {
        super();
        this.view = view;
        this.callback = callback;
    }

    public static ValidateEmail getInstance(ITextValidation callback,View view) {
        if(instance == null) {
            instance = new ValidateEmail(callback,view);
        }
        return instance;
    }

    @Override
    public void checkText(String text) {
        if(text.isEmpty()) {
            callback.textReviewResult(view,"Please provide email address.",true);
            return;
        }

        //String emailPattern = "[]&{}<>?/:;'~";
        //email.contains(emailPattern)
        Log.e("RegisterActivity","email: "+ text);
        if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            callback.textReviewResult(view,"Email is invalid.",true);
            return;
        }

        callback.textReviewResult(view,"Good",false);
    }
}
