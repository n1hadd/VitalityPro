package com.example.vitalitypro;

public class WeightEntry {
    private String date;
    private int weight;

    public WeightEntry(String date, int weight) {
        this.date = date;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "WeightEntry{" +
                "date='" + date + '\'' +
                ", weight=" + weight +
                '}';
    }
}
