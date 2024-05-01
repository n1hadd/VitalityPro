package com.example.vitalitypro;

public class ActivityLevel {
    private String title;
    private String description;

    public ActivityLevel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ActivityLevel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDiscription(String description) {
        this.description = description;
    }
}
