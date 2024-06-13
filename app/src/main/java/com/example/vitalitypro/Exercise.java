package com.example.vitalitypro;

public class Exercise {
    private String name;
    private int duration;
    private double calories_burned;

    public Exercise(String name, int duration, double calories_burned) {
        this.name = name;
        this.duration = duration;
        this.calories_burned = calories_burned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(double calories_burned) {
        this.calories_burned = calories_burned;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", calories_burned=" + calories_burned +
                '}';
    }
}
