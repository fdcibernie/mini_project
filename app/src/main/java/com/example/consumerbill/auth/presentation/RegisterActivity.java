package com.example.consumerbill.auth.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.consumerbill.auth.domain.usecase.ValidateEmail;
import com.example.consumerbill.auth.domain.usecase.ValidatePassword;
import com.example.consumerbill.auth.presentation.viewmodel.AuthViewModel;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.interfaces.ITextValidation;
import com.example.consumerbill.cores.views.AppLoader;
import com.example.consumerbill.databinding.ActivityRegisterBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity implements ITextValidation {
    private static final int EMAIL_ID = 0;
    private static final int PASSWORD_ID = 1;
    private static final int CONFIRM_PASSWORD_ID = 2;
    public static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding layout;
    private EditText txtEmail,txtPassword,txtConfirmPassword;
    private Button btnRegister;
    private TextView tvExit;
    private ValidateEmail validateEmail;
    private ValidatePassword validatePassword,validateConfirmPassword;
    private AppLoader appLoader;
    private AuthViewModel authViewModel;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        layout = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());

        //Initialize button attributes
        txtEmail = layout.txtRegisterEmail.getEditText();
        txtPassword = layout.txtPassword.getEditText();
        txtConfirmPassword = layout.txtConfirmPassword.getEditText();
        tvExit = layout.tvExit;
        validateEmail = ValidateEmail.getInstance(this);
        validatePassword = ValidatePassword.getInstance(this);
        validateConfirmPassword = ValidatePassword.getInstance(this);
        appLoader = AppLoader.getInstance(this,getResources());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        observeUserRegistration();

        //EVENT
        registerButton();
        backToLogin();


    }

    private void backToLogin() {
        tvExit.setOnClickListener(v -> {
            this.finish();
        });
    }

    private void registerButton(){
        layout.btnRegister.setOnClickListener(v -> {
            authViewModel.validationHaveError = false;
            String email = txtEmail.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();
            String cPassword = txtConfirmPassword.getText().toString().trim();
            validateEmail.checkText(EMAIL_ID,email);
            validatePassword.checkText(PASSWORD_ID,password);
            validateConfirmPassword.checkText(CONFIRM_PASSWORD_ID,cPassword);
            if(password.equals(cPassword)) {
                if(!authViewModel.validationHaveError) {
                    appLoader.showLoader();
                    registerUserAsync(email,password);
                }
            }else{
                Toast.makeText(this,"Password did not match.",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerUserAsync(String email,String password) {
        new Thread(() -> authViewModel.registerUser(email,password,firebaseAuth)).start();
    }

    private void observeUserRegistration() {
        authViewModel.getCreateUserResponse().observe(this, apiResult -> {
            runOnUiThread(() -> {
                appLoader.dismissLoader();
            });

            if(apiResult.getApiStatus() == ResponseStatus.SUCCESS) {
                runOnUiThread(() -> {
                    clearTextField();
                    showMessage("Successfully user created.");
                });
            }else{
                runOnUiThread(() -> {
                    clearTextField();
                    showMessage(apiResult.getErrorMessage());
                });

            }
        });
    }

    private void clearTextField() {
        txtPassword.setText("");
        txtEmail.setText("");
        txtConfirmPassword.setText("");
        authViewModel.validationHaveError = false;
    }

    private void showMessage(String message) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setMessage(message)
                .setTitle("Response")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void textReviewResult(int id, String message, boolean isError) {
        if(isError) {
            authViewModel.validationHaveError = true;
            switch (id) {
                case EMAIL_ID:
                    txtEmail.setError(message);
                    break;
                case PASSWORD_ID:
                    txtPassword.setError(message);
                    break;
                case CONFIRM_PASSWORD_ID:
                    txtConfirmPassword.setError(message);
                    break;
                default:
            }
        }
    }
}