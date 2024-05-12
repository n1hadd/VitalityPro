package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isSignedUp = sharedPreferences.getBoolean("isSignedUp", false);
        if(isSignedUp){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else{
            editor.clear();
            editor.apply();
            Fragment fragment = new SignUpLoginFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            /*NutritionDataLoader nutritionDataLoader = new NutritionDataLoader(this);
            nutritionDataLoader.loadNutritionDataIfNeeded();*/
        }

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayout);
        super.onBackPressed();
    }
}