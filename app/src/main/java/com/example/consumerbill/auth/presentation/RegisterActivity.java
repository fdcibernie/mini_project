package com.example.consumerbill.auth.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.consumerbill.auth.domain.usecase.ValidateEmail;
import com.example.consumerbill.auth.domain.usecase.ValidatePassword;
import com.example.consumerbill.cores.interfaces.ITextValidation;
import com.example.consumerbill.databinding.ActivityRegisterBinding;


public class RegisterActivity extends AppCompatActivity implements ITextValidation {
    public static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding layout;
    private EditText txtEmail,txtPassword,txtConfirmPassword;
    private Button btnRegister;
    private TextView tvExit;
    private ValidateEmail validateEmail;
    private ValidatePassword validatePassword;

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
            validateEmail = ValidateEmail.getInstance(this,txtEmail);
            validatePassword = ValidatePassword.getInstance(this,txtPassword);

            validateEmail.checkText(txtEmail.getText().toString().trim());
            validatePassword.checkText(txtPassword.getText().toString().trim());
            Log.e(TAG,"Register");
        });
    }

    @Override
    public void textReviewResult(View view, String message, boolean isError) {
        Log.e(TAG,"isError:"+isError);
        if(isError) {
            Log.e(TAG,"Message:"+message);
        }
    }
}