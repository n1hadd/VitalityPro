package com.example.vitalitypro;

import java.util.List;

public class Meal {
    private String title;
    private int imageResourceId;
    private List<Food> selectedFoods;

    public Meal(String title, int imageResourceId) {
        this.title = title;
        this.imageResourceId = imageResourceId;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
    public List<Food> getSelectedFoods() {
        return selectedFoods;
    }

    public void setSelectedFoods(List<Food> selectedFoods) {
        this.selectedFoods = selectedFoods;
    }
}
