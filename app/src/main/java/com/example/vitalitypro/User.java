package com.example.vitalitypro;

public class User {
    private String username;
    private String password;
    private String goal;
    private String activityLevel;
    private int weight;
    private int goalWeight;
    private int height;
    private int age;
    private String gender;
    private double weightChangeGoal; // For both losing and gaining weight
    private int caloriesEaten;
    private int dailyCalorieIntake;

    public User(String username, String password, String goal, String activityLevel,
                int weight, int goalWeight, int height, int age, String gender, double weightChangeGoal, int caloriesEaten, int dailyCalorieIntake) {
        this.username = username;
        this.password = password;
        this.goal = goal;
        this.activityLevel = activityLevel;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.weightChangeGoal = weightChangeGoal;
        this.caloriesEaten = caloriesEaten;
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public User(String username, String password, String goal, String activityLevel,
                int weight, int height, int age, String gender, int caloriesEaten, int dailyCalorieIntake) {
        this.username = username;
        this.password = password;
        this.goal = goal;
        this.activityLevel = activityLevel;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.caloriesEaten = caloriesEaten;
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(int goalWeight) {
        this.goalWeight = goalWeight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeightChangeGoal() {
        return weightChangeGoal;
    }

    public void setWeightChangeGoal(double weightChangeGoal) {
        this.weightChangeGoal = weightChangeGoal;
    }

    public int getDailyCalorieIntake() {
        return dailyCalorieIntake;
    }

    public void setDailyCalorieIntake(int dailyCalorieIntake) {
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public int getCaloriesEaten() {
        return caloriesEaten;
    }

    public void setCaloriesEaten(int caloriesEaten) {
        this.caloriesEaten = caloriesEaten;
    }
}
