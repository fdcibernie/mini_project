package com.example.consumerbill.auth.presentation.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.consumerbill.auth.domain.usecase.LoginUseCase;
import com.example.consumerbill.auth.domain.usecase.RegisterUserUseCase;
import com.example.consumerbill.cores.ApiResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthViewModel extends ViewModel {
    public boolean validationHaveError = false;
    private final RegisterUserUseCase registerUserUseCase = RegisterUserUseCase.getInstance();
    private final LoginUseCase loginUseCase = LoginUseCase.getInstance();
    private final MutableLiveData<ApiResult<Void>> authLiveData = new MutableLiveData<>();

    public LiveData<ApiResult<Void>> getAuthResponse() {
        return authLiveData;
    }

    public void registerUser(String email, String password, FirebaseAuth firebaseAuth) {
        registerUserUseCase.createAccount(email, password, firebaseAuth, authLiveData::postValue);
    }
    public void signIn(String email, String password, FirebaseAuth firebaseAuth) {
        loginUseCase.signIn(email,password,firebaseAuth,authLiveData::postValue);
    }
}
