package com.example.consumerbill.auth.data.datasource.remote;
import android.util.Log;

import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.ResponseStatus;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class AuthRemoteSrcImpl implements  IAuthRemoteSrc{
    private String TAG = "AuthRemoteSrcImpl";

    private static AuthRemoteSrcImpl INSTANCE = null;

    public AuthRemoteSrcImpl() {}
    public AuthRemoteSrcImpl getInstance() {
        synchronized(this){
            if(INSTANCE == null) {
                INSTANCE = new AuthRemoteSrcImpl();
            }

            return INSTANCE;
        }
    }

    @Override
    public ApiResult<Void> createAccount(String email, String password, FirebaseAuth firebaseAuth) {
        ApiResult<Void> apiResult;
        final boolean[] isCreated = {false};
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                isCreated[0] = task.isSuccessful();
                Log.e(TAG, "Register User: " + isCreated[0]);

                if (isCreated[0]) {
                    Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification();
                }
            });

            apiResult = (isCreated[0]) ? new ApiResult<>(null,ResponseStatus.SUCCESS,"User successfully registered") :
                    new ApiResult<>(null,ResponseStatus.ERROR,"Failed to register, please try again");
        } catch (Exception e) {
            apiResult = new ApiResult<>(
                    null, ResponseStatus.ERROR,"Something went wrong, please try again."
            );
        }

        return apiResult;
    }
}
