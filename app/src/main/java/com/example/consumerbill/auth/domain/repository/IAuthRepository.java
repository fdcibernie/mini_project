package com.example.consumerbill.auth.domain.repository;

import com.example.consumerbill.cores.ApiResult;
import com.google.firebase.auth.FirebaseAuth;

public interface IAuthRepository {
    ApiResult<Void> createAccount(String email, String password, FirebaseAuth firebaseAuth);
}
