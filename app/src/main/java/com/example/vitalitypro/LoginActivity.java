package com.example.vitalitypro;

import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {


    private MaterialButton btnContinue;
    private FrameLayout frameLayout;

    private Toolbar toolbar;
    private ImageView imgBack;
    private TextView txtToolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        frameLayout = findViewById(R.id.frameLayout);
        toolbar = findViewById(R.id.toolbar);
        imgBack = findViewById(R.id.imgBack);
        txtToolbarTitle = findViewById(R.id.txtToolbarTitle);


        Fragment fragment = new FirstFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    public ImageView getImgBack() {
        return imgBack;
    }

    public TextView getTxtToolbarTitle() {
        return txtToolbarTitle;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayout);


        super.onBackPressed();
    }


}
