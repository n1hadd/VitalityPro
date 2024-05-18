package com.example.vitalitypro;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Food {
    @SerializedName("description")
    private String description;

    @SerializedName("foodNutrients")
    private List<FoodNutrient> foodNutrients;

    // Getters and Setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FoodNutrient> getFoodNutrients() {
        return foodNutrients;
    }

    public void setFoodNutrients(List<FoodNutrient> foodNutrients) {
        this.foodNutrients = foodNutrients;
    }
}
