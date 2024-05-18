package com.example.vitalitypro;

import com.google.gson.annotations.SerializedName;

public class FoodNutrient {
    @SerializedName("nutrientName")
    private String nutrientName;

    @SerializedName("value")
    private float value;

    // Getters and Setters

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
