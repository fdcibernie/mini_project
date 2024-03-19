package com.example.consumerbill;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.consumerbill.auth.presentation.LoginPage;
import com.example.consumerbill.billers.presentation.BillersFragment;
import com.example.consumerbill.cores.views.AppLoader;
import com.example.consumerbill.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.A;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding layout;
    public AppLoader appLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        layout = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());

        appLoader = new AppLoader(this, getResources());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                )
                .setReorderingAllowed(true)
                .replace(R.id.main_container, BillersFragment.class, null)
                .addToBackStack(null);
        fragmentTransaction.commit();



        layout.btnSignOut.setOnClickListener(v -> {
            appLoader.showLoader();
            FirebaseAuth.getInstance().signOut();
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() ->  {
                appLoader.dismissLoader();
                Intent intentToLogin = new Intent(this, LoginPage.class);
                startActivity(intentToLogin);
                finish();
            }, 1000);
        });
    }


}