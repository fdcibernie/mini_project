package com.example.consumerbill.auth.domain.usecase;

import com.example.consumerbill.auth.data.repository.AuthRepositoryImpl;
import com.example.consumerbill.cores.interfaces.IAuthTaskListener;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUseCase {
    private final AuthRepositoryImpl repository;
    private static LoginUseCase instance = null;

    public LoginUseCase() {
        repository = AuthRepositoryImpl.getInstance();
    }

    public static LoginUseCase getInstance() {
        if(instance == null) {
            instance = new LoginUseCase();
        }
        return instance;
    }
    public void signIn(String email, String password, FirebaseAuth firebaseAuth, IAuthTaskListener observer) {
        repository.signIn(email, password, firebaseAuth, observer);
    }
}
