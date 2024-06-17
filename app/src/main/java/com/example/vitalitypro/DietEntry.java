package com.example.vitalitypro;

public class DietEntry {
    private String date;
    private int calories_eaten;

    public DietEntry(String date, int calories_eaten) {
        this.date = date;
        this.calories_eaten = calories_eaten;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCalories_eaten() {
        return calories_eaten;
    }

    public void setCalories_eaten(int calories_eaten) {
        this.calories_eaten = calories_eaten;
    }

    @Override
    public String toString() {
        return "DietEntry{" +
                "date='" + date + '\'' +
                ", calories_eaten=" + calories_eaten +
                '}';
    }
}
