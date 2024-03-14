package com.example.consumerbill.auth.presentation.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.consumerbill.auth.domain.usecase.RegisterUserUseCase;
import com.example.consumerbill.cores.ApiResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthViewModel extends ViewModel {
    public boolean validationHaveError = false;
    private final RegisterUserUseCase registerUserUseCase = RegisterUserUseCase.getInstance();
    private final MutableLiveData<ApiResult<Void>> userRegisterLiveData = new MutableLiveData<>();

    public LiveData<ApiResult<Void>> getCreateUserResponse() {
        return userRegisterLiveData;
    }

    public void registerUser(String email, String password, FirebaseAuth firebaseAuth) {
        registerUserUseCase.createAccount(email, password, firebaseAuth, userRegisterLiveData::postValue);
    }
}
