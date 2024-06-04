package com.example.vitalitypro;

import java.util.List;

public class ExerciseResponse {
    private List<Exercise> exercises;

    public List<Exercise> getExercises() {
        return exercises;
    }

    public class Exercise {
        private String name;
        private double nf_calories;
        private int duration_min;

        public String getName() {
            return name;
        }

        public double getNfCalories() {
            return nf_calories;
        }

        public int getDurationMin() {
            return duration_min;
        }
    }
}
