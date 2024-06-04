package com.example.vitalitypro;


// Create a new file named ApiClient.java
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NutritionixApiClient {
    private static final String BASE_URL = "https://trackapi.nutritionix.com/v2/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}


