package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class FoodUtils {

    private static final String FOOD_PREF_KEY = "selected_food";

    public static void saveSelectedFood(Context context, Food food) {
        Gson gson = new Gson();
        String foodJson = gson.toJson(food);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(FOOD_PREF_KEY, foodJson).apply();
    }

    public static Food getSelectedFood(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String foodJson = preferences.getString(FOOD_PREF_KEY, "");
        Gson gson = new Gson();
        return gson.fromJson(foodJson, Food.class);
    }
}
