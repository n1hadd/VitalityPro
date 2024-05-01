package com.example.vitalitypro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "Main Activity";

    private MaterialButton btnSeeAllIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initIngredients();
        initViews();

        btnSeeAllIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IngredientsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        btnSeeAllIngredients = findViewById(R.id.btnSeeAllIngredients);
    }

/*private void initIngredients() {
        DatabaseHandler dbHandler = new DatabaseHandler(this);
        InputStream inputStream = getResources().openRawResource(R.raw.ingredients);

        // Parse ingredients
        List<Ingredient> ingredients = Utils.parseIngredients(inputStream);

        // Check if ingredients are parsed successfully
        if (ingredients != null && !ingredients.isEmpty()) {
            // Iterate through ingredients and print their names
            for (Ingredient i : ingredients) {
                dbHandler.addIngredient(i);
            }
        } else {
            Log.d(TAG, "Cannot read ingredient.");
        }

    }*/

}