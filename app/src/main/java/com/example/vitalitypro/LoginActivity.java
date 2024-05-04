package com.example.vitalitypro;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int currentProgress = 20;
    private MaterialButton btnContinue;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBar.setProgress(currentProgress);
        progressBar.setMax(100);

        frameLayout = findViewById(R.id.frameLayout);
        Fragment fragment = new FirstFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public ProgressBar getProgressBar(){
        return progressBar;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayout);

        if (currentFragment instanceof SecondFragment) {
            progressBar.setVisibility(View.GONE); // Set the visibility of progressBar to GONE
        }


        super.onBackPressed();
    }

}
