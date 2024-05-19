package com.example.vitalitypro;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Food implements Parcelable {
    @SerializedName("description")
    private String description;
    @SerializedName("servingSize")
    private double servingSize;
    @SerializedName("servingSizeUnit")
    private String servingUnit;
    private double calories;

    @SerializedName("foodNutrients")
    private List<FoodNutrient> foodNutrients;

    public Food(String description, double servingSize, String servingUnit) {
        this.description = description;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getServingSize() {
        return servingSize;
    }

    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public List<FoodNutrient> getFoodNutrients() {
        return foodNutrients;
    }

    public void setFoodNutrients(List<FoodNutrient> foodNutrients) {
        this.foodNutrients = foodNutrients;
    }

    protected Food(Parcel in) {
        description = in.readString();
        servingSize = in.readDouble();
        servingUnit = in.readString();
        calories = in.readDouble();
        foodNutrients = in.createTypedArrayList(FoodNutrient.CREATOR);
    }

    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeDouble(servingSize);
        dest.writeString(servingUnit);
        dest.writeDouble(calories);
        dest.writeTypedList(foodNutrients);
    }

    public static class FoodNutrient implements Parcelable{
        @SerializedName("nutrientName")
        private String nutrientName;
        @SerializedName("value")
        private double value;

        public String getNutrientName() {
            return nutrientName;
        }

        public void setNutrientName(String nutrientName) {
            this.nutrientName = nutrientName;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        protected FoodNutrient(Parcel in) {
            nutrientName = in.readString();
            value = in.readDouble();
        }

        public static final Creator<FoodNutrient> CREATOR = new Creator<FoodNutrient>() {
            @Override
            public FoodNutrient createFromParcel(Parcel in) {
                return new FoodNutrient(in);
            }

            @Override
            public FoodNutrient[] newArray(int size) {
                return new FoodNutrient[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(nutrientName);
            dest.writeDouble(value);
        }
    }

    // Method to calculate calories from food nutrients
    public void calculateCalories() {
        if (foodNutrients != null) {
            for (FoodNutrient nutrient : foodNutrients) {
                if ("Energy".equals(nutrient.getNutrientName())) {
                    this.calories = nutrient.getValue();
                    return;
                }
            }
        }
        // If "Energy" nutrient is not found, set calories to 0 or handle it accordingly
        this.calories = 0; // Or throw an exception
    }
}
