package com.example.vitalitypro;

public class Ingredient {
    private int id;
    private String name;
    private int serving_size;
    private int calories;
    private double carbs;
    private double protein;
    private double fat;
    private double fiber;
    private double sugar;
    private double saturated_fats;
    private double polyunsaturated_fats;
    private double monounsaturated_fats;
    private double trans_fats;
    private double cholesterol;
    private double sodium;
    private double potassium;
    private double vitamin_a;
    private double vitamin_c;
    private double iron;


    public Ingredient(int id, String name, int serving_size, int calories, double carbs, double protein, double fat,
                      double fiber, double sugar, double saturated_fats, double polyunsaturated_fats, double monounsaturated_fats,
                      double trans_fats, double cholesterol, double sodium, double potassium, double vitamin_a, double vitamin_c,
                      double iron) {
        this.id = id;
        this.name = name;
        this.serving_size = serving_size;
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.fiber = fiber;
        this.sugar = sugar;
        this.saturated_fats = saturated_fats;
        this.polyunsaturated_fats = polyunsaturated_fats;
        this.monounsaturated_fats = monounsaturated_fats;
        this.trans_fats = trans_fats;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.potassium = potassium;
        this.vitamin_a = vitamin_a;
        this.vitamin_c = vitamin_c;
        this.iron = iron;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", serving_size=" + serving_size +
                ", calories=" + calories +
                ", carbs=" + carbs +
                ", protein=" + protein +
                ", fat=" + fat +
                ", fiber=" + fiber +
                ", sugar=" + sugar +
                ", saturated_fats=" + saturated_fats +
                ", polyunsaturated_fats=" + polyunsaturated_fats +
                ", monounsaturated_fats=" + monounsaturated_fats +
                ", trans_fats=" + trans_fats +
                ", cholesterol=" + cholesterol +
                ", sodium=" + sodium +
                ", potassium=" + potassium +
                ", vitamin_a=" + vitamin_a +
                ", vitamin_c=" + vitamin_c +
                ", iron=" + iron +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServing_size() {
        return serving_size;
    }

    public void setServing_size(int serving_size) {
        this.serving_size = serving_size;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getSaturated_fats() {
        return saturated_fats;
    }

    public void setSaturated_fats(double saturated_fats) {
        this.saturated_fats = saturated_fats;
    }

    public double getPolyunsaturated_fats() {
        return polyunsaturated_fats;
    }

    public void setPolyunsaturated_fats(double polyunsaturated_fats) {
        this.polyunsaturated_fats = polyunsaturated_fats;
    }

    public double getMonounsaturated_fats() {
        return monounsaturated_fats;
    }

    public void setMonounsaturated_fats(double monounsaturated_fats) {
        this.monounsaturated_fats = monounsaturated_fats;
    }

    public double getTrans_fats() {
        return trans_fats;
    }

    public void setTrans_fats(double trans_fats) {
        this.trans_fats = trans_fats;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getVitamin_a() {
        return vitamin_a;
    }

    public void setVitamin_a(double vitamin_a) {
        this.vitamin_a = vitamin_a;
    }

    public double getVitamin_c() {
        return vitamin_c;
    }

    public void setVitamin_c(double vitamin_c) {
        this.vitamin_c = vitamin_c;
    }

    public double getIron() {
        return iron;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }
}
