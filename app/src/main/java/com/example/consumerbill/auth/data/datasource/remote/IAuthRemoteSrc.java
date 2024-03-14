package com.example.consumerbill.auth.data.datasource.remote;

import com.example.consumerbill.cores.ApiResult;
import com.example.consumerbill.cores.interfaces.IAuthTaskListener;
import com.google.firebase.auth.FirebaseAuth;

public interface IAuthRemoteSrc {
    void createAccount(String email, String password, FirebaseAuth firebaseAuth, IAuthTaskListener observer);
}
