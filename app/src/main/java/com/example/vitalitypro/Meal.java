package com.example.vitalitypro;

public class Meal {
    private String title;
    private String imgUrl;

    public Meal(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
