package com.example.consumerbill.auth.data.repository;

import android.util.Log;

import com.example.consumerbill.auth.data.datasource.remote.AuthRemoteSrcImpl;
import com.example.consumerbill.auth.domain.repository.IAuthRepository;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAuthTaskListener;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepositoryImpl implements IAuthRepository {

    private static AuthRepositoryImpl INSTANCE = null;
    private final AuthRemoteSrcImpl authRemoteSrc;

    public AuthRepositoryImpl() {
        authRemoteSrc = AuthRemoteSrcImpl.getInstance();
    }

    public static AuthRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AuthRepositoryImpl();
        }

        return INSTANCE;
    }

    @Override
    public void createAccount(String email, String password, FirebaseAuth firebaseAuth, IAuthTaskListener observer) {
        authRemoteSrc.createAccount(email,password,firebaseAuth,observer);
    }
}
