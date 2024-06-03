package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity implements FoodAdapter.OnFoodLoggedListener {
    private final static String TAG = "FoodDetailActivity";
    private TextView foodTitle, txtKcal, txtCarb, txtProtein,
            txtFats, carbsVal, fibersVal, sugarsVal, proteinVal, fatsVal,
            sat_fatsVal, polyunsat_fatsVal, monounsat_fatsVal, trans_fatsVal,
            cholesterolVal, sodiumVal, potassiumVal, vitamin_aVal, vitamin_cVal,
            calciumVal, ironVal;
    private MaterialButton btnAddMeal;
    private Food food;
    private FoodAdapter.OnFoodLoggedListener logged_listener;
    private String parent;
    private String mealType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        initViews();

        food = getIntent().getParcelableExtra("food");
        parent = getIntent().getStringExtra("parent");
        mealType = getIntent().getStringExtra("mealType");

        btnAddMeal.setText("Add meal to "+mealType);

        if(parent.equals("diary")){
            btnAddMeal.setVisibility(View.GONE);
        }
        if(food != null){
            foodTitle.setText(food.getDescription());
            txtKcal.setText(String.valueOf(food.getCalories()));
            if (food.getFoodNutrients() != null) {
                for (Food.FoodNutrient nutrient : food.getFoodNutrients()) {
                    switch (nutrient.getNutrientName()) {
                        case "Carbohydrate, by difference":
                            txtCarb.setText(String.valueOf(nutrient.getValue()));
                            carbsVal.setText(String.valueOf(nutrient.getValue())+" g");
                            break;
                        case "Protein":
                            txtProtein.setText(String.valueOf(nutrient.getValue()));
                            proteinVal.setText(String.valueOf(nutrient.getValue())+" g");
                            break;
                        case "Total lipid (fat)":
                            txtFats.setText(String.valueOf(nutrient.getValue()));
                            fatsVal.setText(String.valueOf(nutrient.getValue())+" g");
                            break;
                        case "Fiber, total dietary":
                            fibersVal.setText(String.valueOf(nutrient.getValue())+" g");
                            break;
                        case "Sugars, total including NLEA":
                            sugarsVal.setText(String.valueOf(nutrient.getValue())+" g");
                            break;
                        case "Fatty acids, total saturated":
                            sat_fatsVal.setText(String.valueOf(nutrient.getValue())+" g");
                            break;
                        case "Fatty acids, total polyunsaturated":
                            polyunsat_fatsVal.setText(String.valueOf(nutrient.getValue())+" g");
                            break;
                        case "Fatty acids, total monounsaturated":
                            monounsat_fatsVal.setText(String.valueOf(nutrient.getValue()) +" g");
                            break;
                        case "Fatty acids, total trans":
                            trans_fatsVal.setText(String.valueOf(nutrient.getValue())+" g");
                            break;
                        case "Cholesterol":
                            cholesterolVal.setText(String.valueOf(nutrient.getValue())+" mg");
                            break;
                        case "Sodium, Na":
                            sodiumVal.setText(String.valueOf(nutrient.getValue())+" mg");
                            break;
                        case "Potassium, K":
                            potassiumVal.setText(String.valueOf(nutrient.getValue())+" mg");
                            break;
                        case "Vitamin A, RAE":
                            vitamin_aVal.setText(String.valueOf(nutrient.getValue())+" mg");
                            break;
                        case "Vitamin C, total ascorbic acid":
                            vitamin_cVal.setText(String.valueOf(nutrient.getValue())+" mg");
                            break;
                        case "Calcium, Ca":
                            calciumVal.setText(String.valueOf(nutrient.getValue())+" mg");
                            break;
                        case "Iron, Fe":
                            ironVal.setText(String.valueOf(nutrient.getValue())+" mg");
                            break;
                        // Add other nutrient cases as needed
                    }
                }
            }
        }
        handleBtnAddMeal();

    }

    private void initViews() {
        txtKcal = findViewById(R.id.txtKcal);
        foodTitle = findViewById(R.id.foodTitle);
        txtCarb = findViewById(R.id.txtCarb);
        txtProtein = findViewById(R.id.txtProtein);
        txtFats = findViewById(R.id.txtFats);
        carbsVal = findViewById(R.id.carbsVal);
        fibersVal = findViewById(R.id.fibersVal);
        sugarsVal = findViewById(R.id.sugarsVal);
        proteinVal = findViewById(R.id.proteinVal);
        fatsVal = findViewById(R.id.fatsVal);
        sat_fatsVal = findViewById(R.id.sat_fatsVal);
        polyunsat_fatsVal = findViewById(R.id.polyunsat_fatsVal);
        monounsat_fatsVal = findViewById(R.id.monounsat_fatsVal);
        trans_fatsVal = findViewById(R.id.trans_fatsVal);
        cholesterolVal = findViewById(R.id.cholesterolVal);
        sodiumVal = findViewById(R.id.sodiumVal);
        potassiumVal = findViewById(R.id.potassiumVal);
        vitamin_aVal = findViewById(R.id.vitamin_aVal);
        vitamin_cVal = findViewById(R.id.vitamin_cVal);
        calciumVal = findViewById(R.id.calciumVal);
        ironVal = findViewById(R.id.ironVal);
        btnAddMeal = findViewById(R.id.btnAddMeal);
    }

    private void handleBtnAddMeal(){
        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Logging to: "+mealType);

                SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();

                // Retrieve the current list of foods for this mealType
                String existingFoodsJson = sharedPreferences.getString(mealType, "[]");
                Type type = new TypeToken<List<Food>>() {}.getType();
                List<Food> selectedFoods = gson.fromJson(existingFoodsJson, type);

                if (selectedFoods == null) {
                    selectedFoods = new ArrayList<>();
                }

                // Add the new food item
                selectedFoods.add(food);

                // Save the updated list back to SharedPreferences
                String updatedFoodsJson = gson.toJson(selectedFoods);
                editor.putString(mealType, updatedFoodsJson);
                editor.apply();

                Toast.makeText(btnAddMeal.getContext(), "Food added to " + mealType, Toast.LENGTH_SHORT).show();

                if(logged_listener != null){
                    logged_listener.onFoodLogged(mealType);
                }

                Intent intent = new Intent(btnAddMeal.getContext(), MainActivity.class);
                btnAddMeal.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onFoodLogged(String mealType) {
        DiaryFragment diaryFragment = new DiaryFragment();
        diaryFragment.loadDataForMealType(mealType);
    }
}