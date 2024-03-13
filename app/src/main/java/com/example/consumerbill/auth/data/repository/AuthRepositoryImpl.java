package com.example.consumerbill.auth.data.repository;

import com.example.consumerbill.auth.data.datasource.remote.AuthRemoteSrcImpl;
import com.example.consumerbill.auth.domain.IAuthRepository;
import com.example.consumerbill.cores.ApiResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepositoryImpl implements IAuthRepository {

    private static AuthRepositoryImpl INSTANCE = null;
    private final AuthRemoteSrcImpl authRemoteSrc;

    public AuthRepositoryImpl() {
        authRemoteSrc = new AuthRemoteSrcImpl();
    }

    public AuthRepositoryImpl getInstance() {
        synchronized (this) {
            if(INSTANCE == null) {
                INSTANCE = new AuthRepositoryImpl();
            }

            return INSTANCE;
        }
    }

    @Override
    public ApiResult<Void> createAccount(String email, String password, FirebaseAuth firebaseAuth) {
        return authRemoteSrc.createAccount(email,password,firebaseAuth);
    }

}
