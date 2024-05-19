package com.example.vitalitypro;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodSearchResponse {
    @SerializedName("foods")
    private List<Food> foods;

    // Getters and Setters
    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}


