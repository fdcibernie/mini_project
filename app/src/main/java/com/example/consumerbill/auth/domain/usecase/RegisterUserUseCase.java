package com.example.consumerbill.auth.domain.usecase;

import android.util.Log;

import com.example.consumerbill.auth.data.repository.AuthRepositoryImpl;
import com.example.consumerbill.auth.domain.repository.IAuthRepository;
import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAuthTaskListener;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUserUseCase {
    private final AuthRepositoryImpl repository;
    private static RegisterUserUseCase instance = null;

    public RegisterUserUseCase() {
        repository = AuthRepositoryImpl.getInstance();
    }

    public static RegisterUserUseCase getInstance() {
        if(instance == null) {
            instance = new RegisterUserUseCase();
        }
        return instance;
    }

    public void createAccount(String email, String password, FirebaseAuth firebaseAuth, IAuthTaskListener observer) {
         repository.createAccount(email, password, firebaseAuth,observer);
    }
}
