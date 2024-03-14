package com.example.consumerbill.auth.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.consumerbill.R;
import com.example.consumerbill.databinding.ActivityLoginPageBinding;

public class LoginPage extends AppCompatActivity {

    private ActivityLoginPageBinding layout;
    private TextView tvRegister;
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

        //Events
        gotoRegister();
    }

    private void gotoRegister() {
        tvRegister.setOnClickListener(v -> {
            Intent intentRegister = new Intent(this,RegisterActivity.class);
            startActivity(intentRegister);
        });
    }
}