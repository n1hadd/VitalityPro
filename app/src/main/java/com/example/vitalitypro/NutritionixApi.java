package com.example.vitalitypro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NutritionixApi {
    @Headers({
            "x-app-id: c3202842",
            "x-app-key: 6e10e387fda650c03fe294c1a78de109",
            "x-remote-user-id: 0"
    })
    @POST("natural/exercise")
    Call<ExerciseResponse> getExerciseInfo(@Body ExerciseRequest request);
}