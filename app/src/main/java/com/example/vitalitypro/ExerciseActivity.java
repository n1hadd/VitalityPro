package com.example.vitalitypro;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExerciseActivity extends AppCompatActivity {

    private EditText activityEditText;
    private EditText minutesEditText;
    private Button calculateButton;
    private TextView resultsTextView;
    private NutritionixApi nutritionixApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        activityEditText = findViewById(R.id.activityEditText);
        minutesEditText = findViewById(R.id.minutesEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resultsTextView = findViewById(R.id.resultsTextView);

        Retrofit retrofit = NutritionixApiClient.getClient();
        nutritionixApi = retrofit.create(NutritionixApi.class);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activity = activityEditText.getText().toString();
                String minutes = minutesEditText.getText().toString();
                if (!activity.isEmpty() && !minutes.isEmpty()) {
                    String query = activity + " for " + minutes + " minutes";
                    fetchExerciseInfo(query);
                }
            }
        });

    }

    private void fetchExerciseInfo(String query) {
        ExerciseRequest request = new ExerciseRequest(query);
        Call<ExerciseResponse> call = nutritionixApi.getExerciseInfo(request);

        call.enqueue(new Callback<ExerciseResponse>() {
            @Override
            public void onResponse(Call<ExerciseResponse> call, Response<ExerciseResponse> response) {
                if (response.isSuccessful()) {
                    ExerciseResponse exerciseResponse = response.body();
                    if (exerciseResponse != null && !exerciseResponse.getExercises().isEmpty()) {
                        ExerciseResponse.Exercise exercise = exerciseResponse.getExercises().get(0);
                        String resultText = "Activity: " + exercise.getName() + "\n" +
                                "Duration: " + exercise.getDurationMin() + " minutes\n" +
                                "Calories Burned: " + exercise.getNfCalories() + " kcal";
                        resultsTextView.setText(resultText);
                    } else {
                        resultsTextView.setText("No results found.");
                    }
                } else {
                    Log.e("API_ERROR", "Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ExerciseResponse> call, Throwable t) {
                Log.e("API_FAILURE", "Failure: " + t.getMessage());
            }
        });
    }

}