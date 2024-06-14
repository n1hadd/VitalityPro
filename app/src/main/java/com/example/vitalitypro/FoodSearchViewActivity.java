package com.example.vitalitypro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.vitalitypro.Food.FoodNutrient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class FoodSearchViewActivity extends AppCompatActivity implements FoodAdapter.OnItemClickListener{
    private static final String TAG = "FoodSearchViewActivity";
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList = new ArrayList<>();
    private LoggedFoodAdapter loggedFoodAdapter;
    private List<Food> loggedFoodList = new ArrayList<>();
    private String mealType;
    private ImageView imgSearchFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search_view);
        mealType = getIntent().getStringExtra("meal_type");
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        imgSearchFood = findViewById(R.id.imgSearchFood);

        Glide.with(this)
                .asGif()
                .load(R.drawable.looking)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgSearchFood);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(foodList, mealType, this, null, getSupportFragmentManager(), this);
        recyclerView.setAdapter(foodAdapter);

        foodAdapter.setOnItemClickListener(this);


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

        call.enqueue(new Callback<FoodSearchResponse>() {
            @Override
            public void onResponse(Call<FoodSearchResponse> call, Response<FoodSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Log the raw response
                    Log.d(TAG, "Raw response: " + response.raw().toString());
                    Log.d(TAG, "Raw response: " + new Gson().toJson(response.body()));

                    foodList.clear();

                    // Filter out items without Energy value
                    for (Food food : response.body().getFoods()) {
                        boolean hasEnergy = false;
                        for (FoodNutrient nutrient : food.getFoodNutrients()) {
                            if ("Energy".equals(nutrient.getNutrientName()) && nutrient.getValue() > 0) {
                                hasEnergy = true;
                                food.setCalories(nutrient.getValue());
                                break;
                            }
                        }
                        if (hasEnergy) {
                            foodList.add(food);
                            imgSearchFood.setVisibility(View.GONE);
                        }
                    }

                    foodAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Response not successful: " + response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<FoodSearchResponse> call, Throwable t) {
                Log.d(TAG, "Failed to read data from API: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(Food food) {
        // Start a new activity or fragment to display detailed nutritive values
        Intent intent = new Intent(this, FoodDetailActivity.class);
        intent.putExtra("food", food);
        intent.putExtra("parent", "FoodSearchView");
        intent.putExtra("mealType", mealType);
        startActivity(intent);
    }

    /*private void loadLoggedItemsFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String existingFoodsJson = sharedPreferences.getString("selected_foods", "[]");
        Gson gson = new Gson();
        List<Food> selectedFoods = gson.fromJson(existingFoodsJson, new TypeToken<List<Food>>() {}.getType());

        // Update the loggedFoodList with the retrieved list of selected foods
        loggedFoodList.clear();
        loggedFoodList.addAll(selectedFoods);

        // Notify the adapter that the data set has changed
        loggedFoodAdapter.notifyDataSetChanged();
    }*/

}