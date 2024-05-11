package com.example.vitalitypro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.vitalitypro.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "Main Activity";

    ActivityMainBinding binding;
    private FrameLayout frameLayout;
    private FrameLayout halfScreenFrameLayout;
    private FloatingActionButton logButton;

    private ConstraintLayout logLayout;
    private boolean isLogLayoutVisible = false;
    private MenuItem lastClickedItem = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initBottomNavigationBar();
        initFloatingActionButton();
        openDiaryFragment();








    }

    private void initFloatingActionButton() {
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogLayoutVisible == false) {
                    isLogLayoutVisible = true;
                    animateLogLayout(true); // Animate the layout to show from bottom to top
                } else if(isLogLayoutVisible == true) {
                    isLogLayoutVisible = false;
                    animateLogLayout(false); // Animate the layout to hide from top to bottom
                }
            }
        });
    }

    private void initBottomNavigationBar() {
        binding.bottomNavView.setBackground(null);
        binding.bottomNavView.setOnNavigationItemSelectedListener(item -> {
            // Disable the last clicked item
            if (lastClickedItem != null) {
                lastClickedItem.setEnabled(true);
            }

            // Enable the clicked item
            item.setEnabled(false);

            // Store the clicked item as the last clicked item
            lastClickedItem = item;

            // Perform the desired action based on the clicked item
            if (item.getItemId() == R.id.diary) {
                openDiaryFragment();
            } else if (item.getItemId() == R.id.profile) {
                openProfileFragment();
            }

            return true; // Return true to indicate that the event has been consumed
        });
    }

    private void initViews() {
        logButton = findViewById(R.id.logButton);
        logLayout = findViewById(R.id.logLayout);
    }

    private void animateLogLayout(final boolean isVisible) {
        TranslateAnimation animate;
        if (!isVisible) {
            animate = new TranslateAnimation(0, 0, 0, logLayout.getHeight());
        } else {
            animate = new TranslateAnimation(0, 0, logLayout.getHeight(), 0);
        }
        animate.setDuration(500);
        animate.setFillAfter(true);

        // Set animation listener to handle layout visibility
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isVisible) {
                    logLayout.setVisibility(View.VISIBLE);
                } else {
                    logLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        logLayout.startAnimation(animate);
    }



    public void openDiaryFragment(){
        Fragment fragment = new DiaryFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    public void openProfileFragment(){
        Fragment fragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.frameLayout, fragment)
                .commit();
    }



    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}