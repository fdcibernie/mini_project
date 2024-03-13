package com.example.consumerbill.auth.data.datasource.remote;

import com.example.consumerbill.cores.ApiResult;
import com.google.firebase.auth.FirebaseAuth;

public interface IAuthRemoteSrc {
    ApiResult<Void> createAccount(String email, String password, FirebaseAuth firebaseAuth);
}
