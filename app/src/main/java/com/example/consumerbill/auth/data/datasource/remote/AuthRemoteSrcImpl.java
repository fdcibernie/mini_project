package com.example.consumerbill.auth.data.datasource.remote;
import android.util.Log;

import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.interfaces.IAuthTaskListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class AuthRemoteSrcImpl implements  IAuthRemoteSrc{
    private String TAG = "AuthRemoteSrcImpl";

    private static AuthRemoteSrcImpl INSTANCE = null;

    public AuthRemoteSrcImpl() {}
    public static AuthRemoteSrcImpl getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AuthRemoteSrcImpl();
        }

        return INSTANCE;
    }

    @Override
    public void createAccount(String email, String password, FirebaseAuth firebaseAuth, IAuthTaskListener observer) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if ((task.isSuccessful())) {
                    Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification();
                    observer.onCompleteTask(new ApiResult<>(null, ResponseStatus.SUCCESS, "User successfully registered"));
                } else {
                    observer.onCompleteTask(new ApiResult<>(null, ResponseStatus.ERROR, "Failed to register, please try again"));
                }
            });
        } catch (Exception e) {
            observer.onCompleteTask(new ApiResult<>(null, ResponseStatus.ERROR,"Something went wrong, please try again."));
        }
    }
}
