package com.example.consumerbill.auth.domain.repository;

import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAuthTaskListener;
import com.google.firebase.auth.FirebaseAuth;

public interface IAuthRepository {
    void createAccount(String email, String password, FirebaseAuth firebaseAuth, IAuthTaskListener observer);
}
