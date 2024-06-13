package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExerciseActivity extends AppCompatActivity {

    private EditText activityEditText;
    private EditText minutesEditText;
    private Button calculateButton, btnLogActivity;
    private CardView cwActivity;
    private RelativeLayout activityRelativeLayout;
    private TextView txtExerciseName, txtDuration, txtCaloriesBurned, txtNoResults;
    private NutritionixApi nutritionixApi;

    private String activity, minutes, query;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        initViews();


        Retrofit retrofit = NutritionixApiClient.getClient();
        nutritionixApi = retrofit.create(NutritionixApi.class);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity = activityEditText.getText().toString();
                minutes = minutesEditText.getText().toString();
                if (!activity.isEmpty() && !minutes.isEmpty()) {
                    query = activity + " for " + minutes + " minutes";
                    fetchExerciseInfo(query);
                }
            }
        });



        btnLogActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityEditText.getText().toString().isEmpty() || txtDuration.getText().toString().isEmpty()){
                    Toast.makeText(ExerciseActivity.this, "Please enter the required values.", Toast.LENGTH_SHORT).show();
                }
                else{
                    ExerciseRequest request = new ExerciseRequest(query);
                    Call<ExerciseResponse> call = nutritionixApi.getExerciseInfo(request);

                    call.enqueue(new Callback<ExerciseResponse>() {
                        @Override
                        public void onResponse(Call<ExerciseResponse> call, Response<ExerciseResponse> response) {
                            if (response.isSuccessful()) {
                                ExerciseResponse exerciseResponse = response.body();
                                if (exerciseResponse != null && !exerciseResponse.getExercises().isEmpty()) {
                                    ExerciseResponse.Exercise exercise = exerciseResponse.getExercises().get(0);

                                    SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    Gson gson = new Gson();
                                    String existingExercises = sharedPreferences.getString("exercises", null);

                                    Type type = new TypeToken<List<Exercise>>() {}.getType();
                                    List<Exercise> loggedExercises = gson.fromJson(existingExercises, type);

                                    if(loggedExercises == null){
                                        loggedExercises = new ArrayList<Exercise>();
                                    }

                                    loggedExercises.add(new Exercise(exercise.getName(), exercise.getDurationMin(), exercise.getNfCalories()));
                                    String updatedExercises = gson.toJson(loggedExercises);
                                    editor.putString("exercises", updatedExercises);
                                    editor.apply();

                                    Toast.makeText(btnLogActivity.getContext(), "Exercise "+exercise.getName()+" logged.", Toast.LENGTH_SHORT).show();

                                    if(onExerciseLogged_listener != null){
                                        onExerciseLogged_listener.onExerciseLogged();
                                    }

                                    Intent intent = new Intent(btnLogActivity.getContext(), MainActivity.class);
                                    btnLogActivity.getContext().startActivity(intent);

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
        });

    }

    private void initViews() {
        activityEditText = findViewById(R.id.activityEditText);
        minutesEditText = findViewById(R.id.minutesEditText);
        calculateButton = findViewById(R.id.calculateButton);

        btnLogActivity = findViewById(R.id.btnLogActivity);
        cwActivity = findViewById(R.id.cwActivity);
        activityRelativeLayout = findViewById(R.id.activityRelativeLayout);
        txtExerciseName = findViewById(R.id.txtExerciseName);
        txtDuration = findViewById(R.id.txtDuration);
        txtCaloriesBurned = findViewById(R.id.txtCaloriesBurned);
        txtNoResults = findViewById(R.id.txtNoResults);
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

                        txtExerciseName.setText("Activity: "+exercise.getName());
                        txtDuration.setText("Duration: "+exercise.getDurationMin()+" min");
                        txtCaloriesBurned.setText("Calories Burned: "+exercise.getNfCalories()+" kcal");

                    } else {
                        txtNoResults.setVisibility(View.VISIBLE);
                        txtNoResults.setText("No results found.");
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
    private onExerciseLogged onExerciseLogged_listener;
    public interface onExerciseLogged{
        void onExerciseLogged();
    }

}