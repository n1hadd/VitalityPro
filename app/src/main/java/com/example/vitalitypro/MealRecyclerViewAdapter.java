package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MealRecyclerViewAdapter extends RecyclerView.Adapter<MealRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MealRecylcerViewAdapter";
    private List<Meal> meals;
    private Context context;
    private FragmentManager fragmentManager;

    private SharedPreferences sharedPreferences;
    public MealRecyclerViewAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_custom_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Started");
        Meal mealItem = meals.get(position);
        sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(context);
        int dailyCalorieIntake = userDatabaseHandler.getDailyCalorieIntake(sharedPreferences.getString("username_pref_key",""));
        int breakfastCalories = (int)(dailyCalorieIntake *0.2);
        int lunchCalories = (int)(dailyCalorieIntake *0.4);
        int snackCalories = (int)(dailyCalorieIntake *0.1);
        int dinnerCalories = (int)(dailyCalorieIntake *0.3);
        int caloriesEaten = userDatabaseHandler.getTotalCaloriesEatenByUsername(sharedPreferences.getString("username_pref_key",""));

        // Set the title
        holder.txtMealTitle.setText(mealItem.getTitle());
        // Set the image
        holder.imgMeal.setImageResource(mealItem.getImageResourceId());

        // Determine the meal type of the current item
        String mealType = mealItem.getTitle(); // Assuming you have a getType() method in your Meal class

        // Set calories based on the meal type
        int calories = 0;
        switch (mealType) {
            case "Breakfast":
                calories = breakfastCalories;
                break;
            case "Lunch":
                calories = lunchCalories;
                break;
            case "Snack":
                calories = snackCalories;
                break;
            case "Dinner":
                calories = dinnerCalories;
                break;
            default:
                // Handle unknown meal type
                break;
        }

        holder.txtCalories.setText(caloriesEaten+" / "+String.valueOf(calories)+" kcal");
        holder.btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodSearchViewActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private RelativeLayout collapsedRelativeLayout;
        private ProgressBar mealCaloriesProgress;
        private TextView txtMealTitle, emptyLog, txtCalories;
        private ImageView imgMeal;
        private MaterialButton btnAddMeal;
        private RelativeLayout expandedRelativeLayout;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            collapsedRelativeLayout = itemView.findViewById(R.id.collapsedRelativeLayout);
            mealCaloriesProgress = itemView.findViewById(R.id.mealCaloriesProgress);
            txtMealTitle = itemView.findViewById(R.id.txtMealTitle);
            emptyLog = itemView.findViewById(R.id.emptyLog);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            btnAddMeal = itemView.findViewById(R.id.btnAddMeal);
            expandedRelativeLayout = itemView.findViewById(R.id.expandedRelativeLayout);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            txtCalories = itemView.findViewById(R.id.txtCalories);
        }

    }

}
