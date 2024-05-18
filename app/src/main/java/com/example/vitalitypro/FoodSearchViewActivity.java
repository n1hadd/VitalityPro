package com.example.vitalitypro;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class FoodSearchViewActivity extends AppCompatActivity {
    private static final String TAG = "FoodSearchViewActivity";
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search_view);

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(foodAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFood(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void searchFood(String query) {
        FoodDataService service = ApiClient.getApiClient().create(FoodDataService.class);
        Call<FoodSearchResponse> call = service.searchFoods(query, "sRPk5sopXGK9QYdQ3rGlBX2uZmAm9jIWMFnYbboq");

        Log.d(TAG, "Request URL: " + call.request().url());

        call.enqueue(new Callback<FoodSearchResponse>() {
            @Override
            public void onResponse(Call<FoodSearchResponse> call, Response<FoodSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Response: " + response.body());
                    foodList.clear();
                    foodList.addAll(response.body().getFoods());
                    foodAdapter.notifyDataSetChanged();
                } else {
                    try {
                        Log.d(TAG, "Response failed: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodSearchResponse> call, Throwable t) {
                Log.d(TAG, "Failed to read data from API: " + t.getMessage());
            }
        });
    }



}