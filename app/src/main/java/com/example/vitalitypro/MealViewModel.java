package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MealViewModel extends ViewModel {
    private MutableLiveData<List<Food>> breakfastFoods = new MutableLiveData<>();
    private MutableLiveData<List<Food>> lunchFoods = new MutableLiveData<>();
    private MutableLiveData<List<Food>> snackFoods = new MutableLiveData<>();
    private MutableLiveData<List<Food>> dinnerFoods = new MutableLiveData<>();

    public LiveData<List<Food>> getBreakfastFoods() {
        return breakfastFoods;
    }

    public LiveData<List<Food>> getLunchFoods() {
        return lunchFoods;
    }

    public LiveData<List<Food>> getSnackFoods() {
        return snackFoods;
    }

    public LiveData<List<Food>> getDinnerFoods() {
        return dinnerFoods;
    }

    public void loadFoods(Context context, String mealType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String existingFoodsJson = sharedPreferences.getString(mealType, "[]");
        Type type = new TypeToken<List<Food>>() {}.getType();
        List<Food> selectedFoods = gson.fromJson(existingFoodsJson, type);

        if (selectedFoods != null) {
            switch (mealType) {
                case "breakfast":
                    breakfastFoods.setValue(selectedFoods);
                    break;
                case "lunch":
                    lunchFoods.setValue(selectedFoods);
                    break;
                case "snack":
                    snackFoods.setValue(selectedFoods);
                    break;
                case "dinner":
                    dinnerFoods.setValue(selectedFoods);
                    break;
            }
        }
    }
}
