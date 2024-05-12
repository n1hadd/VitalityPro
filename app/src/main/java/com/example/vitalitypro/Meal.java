package com.example.vitalitypro;

public class Meal {
    private String title;
    private int imageResourceId;

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
}
