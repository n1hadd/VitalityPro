package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DietOverviewActivity extends AppCompatActivity {
    private final static String TAG = "DietOverviewActivity";
    private RecyclerView dietEntryRW;
    private TextView caloriesEaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_overview);
        initViews();
        caloriesEatenToday();
        hanldeEntries();
    }

    private void caloriesEatenToday() {
        UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(this);
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String username = sharedPreferences.getString("username_pref_key", null);
        if(username!=null){
            int eaten_calories = sharedPreferences.getInt("calories_eaten", 0);
            caloriesEaten.setText("Calories eaten: "+eaten_calories);
        }

    }

    private void hanldeEntries() {
        FoodDatabaseHelper foodDatabaseHelper = new FoodDatabaseHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Type typeD = new TypeToken<List<DietEntry>>() {}.getType();

        String date = DateUtils.getCurrentDate();
        String dateFormated = formatDate(DateUtils.getCurrentDate());
        List<Food> breakfast_list = foodDatabaseHelper.getBreakfastForDate(date);
        List<Food> lunch_list = foodDatabaseHelper.getLunchForDate(date);
        List<Food> snack_list = foodDatabaseHelper.getSnackForDate(date);
        List<Food> dinner_list = foodDatabaseHelper.getDinnerForDate(date);
        int caloriesEaten = calculateDailyEatenCalories(breakfast_list, lunch_list, snack_list, dinner_list);
        Log.d(TAG, "Calories eaten: "+caloriesEaten);

        List<DietEntry> dietEntries = gson.fromJson(sharedPreferences.getString("diet_entry", null),typeD);
        if(dietEntries==null){
            dietEntries = new ArrayList<>();
        }
        Log.d("DietOverviewActivity Started!", "Enteries "+gson.toJson(dietEntries));

        dietEntryRW.setLayoutManager(new LinearLayoutManager(this));
        DietEntryAdapter dietEntryAdapter = new DietEntryAdapter(this, dietEntries);
        dietEntryRW.setAdapter(dietEntryAdapter);

    }

    // currentDate  format : 2024-06-17
    private String formatDate(String currentDate) {
        String[] tab = currentDate.split("-");
        String year = tab[0];
        String day = tab[2];
        String month = "";

        switch(Integer.parseInt(tab[1])) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
            default:
                month = "Invalid month";
                break;
        }

        return day + " " + month + " " + year;
    }

    private void initViews() {
        dietEntryRW = findViewById(R.id.dietEntryRW);
        caloriesEaten = findViewById(R.id.caloriesEaten);
    }

    private int calculateDailyEatenCalories(List<Food> breakfast, List<Food> lunch, List<Food> snack, List<Food> dinner ) {
        int breakfastCal = 0;
        int lunchCal = 0;
        int snackCal = 0;
        int dinnerCal = 0;

        if(breakfast != null && !breakfast.isEmpty()){
            for(Food food : breakfast){
                breakfastCal += food.getCalories();
            }
        }

        if(lunch != null && !lunch.isEmpty()){
            for(Food food : lunch){
                lunchCal += food.getCalories();
            }
        }

        if(snack != null && !snack.isEmpty()){
            for(Food food : snack){
                snackCal += food.getCalories();
            }
        }

        if(dinner != null && !dinner.isEmpty()){
            for(Food food : dinner){
                dinnerCal += food.getCalories();
            }
        }

        int totalCal = breakfastCal + lunchCal + snackCal + dinnerCal;
        return totalCal;
    }
}