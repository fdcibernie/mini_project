package com.example.consumerbill.auth.presentation;

import static com.example.consumerbill.auth.presentation.RegisterActivity.EMAIL_ID;
import static com.example.consumerbill.auth.presentation.RegisterActivity.PASSWORD_ID;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.consumerbill.R;
import com.example.consumerbill.auth.domain.usecase.ValidateEmail;
import com.example.consumerbill.auth.domain.usecase.ValidatePassword;
import com.example.consumerbill.auth.presentation.viewmodel.AuthViewModel;
import com.example.consumerbill.cores.ResponseStatus;
import com.example.consumerbill.cores.interfaces.ITextValidation;
import com.example.consumerbill.cores.views.AppLoader;
import com.example.consumerbill.databinding.ActivityLoginPageBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity implements ITextValidation {

    private ActivityLoginPageBinding layout;
    private TextView tvRegister;
    private AppLoader appLoader;
    private AuthViewModel authViewModel;
    private FirebaseAuth firebaseAuth;
    private EditText txtEmail,txtPassword;
    private Button btnLogin;
    private ValidateEmail validateEmail;
    private ValidatePassword validatePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        layout = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        //Initialize views here
        tvRegister = layout.tvRegister;
        txtEmail = layout.txtEmail.getEditText();
        txtPassword = layout.txtPassword.getEditText();
        btnLogin = layout.btnLogin;
        validateEmail = ValidateEmail.getInstance(this);
        validatePassword = ValidatePassword.getInstance(this);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        appLoader = AppLoader.getInstance(this,getResources());

        //Events
        gotoRegister();
        clickLoginButton();
        observeUserRegistration();
    }

    private void gotoRegister() {
        tvRegister.setOnClickListener(v -> {
            Intent intentRegister = new Intent(this,RegisterActivity.class);
            startActivity(intentRegister);
        });
    }

    private void clickLoginButton() {
        btnLogin.setOnClickListener(v -> {
            authViewModel.validationHaveError = false;
            String email = txtEmail.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();
            validateEmail.checkText(EMAIL_ID,email);
            validatePassword.checkText(PASSWORD_ID,password);
            if(!authViewModel.validationHaveError) {
                appLoader.showLoader();
                loginUserAsync(email,password);
            }
        });
    }

    private void loginUserAsync(String email,String password) {
        new Thread(() -> authViewModel.signIn(email,password,firebaseAuth)).start();
    }

    private void observeUserRegistration() {
        authViewModel.getAuthResponse().observe(this, apiResult -> {
            runOnUiThread(() -> {
                appLoader.dismissLoader();
            });

            if(apiResult.getApiStatus() == ResponseStatus.SUCCESS) {
                runOnUiThread(() -> {
                    showMessage("Login successfully.");
                });
            }else{
                runOnUiThread(() -> {
                    showMessage(apiResult.getErrorMessage());
                });

            }
        });
    }

    private void showMessage(String message) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setMessage(message)
                .setTitle("Response")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
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
                default:
            }
        }
    }
}