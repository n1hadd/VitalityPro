package com.example.vitalitypro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final static String TAG = "FoodAdapter";
    private List<Food> foodList;
    private List<Food> loggedItems;
    private String mealType;
    private Context context;
    private OnFoodLoggedListener logged_listener;
    private FragmentManager fragmentManager;
    private Activity activity;



    public FoodAdapter(List<Food> foodList, String mealType, Context context, OnFoodLoggedListener logged_listener,
                       FragmentManager fragmentManager, Activity activity) {
        this.foodList = foodList;
        this.mealType = mealType;
        this.context = context;
        this.logged_listener = logged_listener;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_recycler_view, parent, false);
        return new FoodViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.txtIngredientName.setText(food.getDescription());

        String servingText = food.getServingSize() > 0 ?
                String.format("Serving: %.2f %s", food.getServingSize(), food.getServingUnit()) :
                "Serving size not available";

        holder.txtGrams.setText(servingText);

        String caloriesText = food.getCalories() > 0 ?
                String.format("Calories: %.2f", food.getCalories()) :
                "Calories not available";

        holder.txtIngredientCalories.setText(caloriesText);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(food);
                }
            }
        });

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Logging to: "+mealType);

                SharedPreferences sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();

                // Retrieve the current list of foods for this mealType
                String existingFoodsJson = sharedPreferences.getString(mealType, "[]");
                Type type = new TypeToken<List<Food>>() {}.getType();
                List<Food> selectedFoods = gson.fromJson(existingFoodsJson, type);

                if (selectedFoods == null) {
                    selectedFoods = new ArrayList<>();
                }

                // Add the new food item
                selectedFoods.add(food);

                // Save the updated list back to SharedPreferences
                String updatedFoodsJson = gson.toJson(selectedFoods);
                editor.putString(mealType, updatedFoodsJson);
                editor.apply();

                Toast.makeText(context, "Food added to " + mealType, Toast.LENGTH_SHORT).show();

                if(logged_listener != null){
                    logged_listener.onFoodLogged(mealType);
                }

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);

            }
        });

    }




    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView txtIngredientName, txtGrams, txtIngredientCalories;
        ImageView imgAdd;
        RelativeLayout relativeLayout;

        public FoodViewHolder(View itemView) {
            super(itemView);
            txtIngredientName = itemView.findViewById(R.id.txtIngredientName);
            txtGrams = itemView.findViewById(R.id.txtGrams);
            txtIngredientCalories = itemView.findViewById(R.id.txtIngredientCalories);
            imgAdd = itemView.findViewById(R.id.imgAdd);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Food food);
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnFoodLoggedListener {
        void onFoodLogged(String mealType);
    }

    /*public void openDiaryFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Optional: Set custom animations
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_right, R.anim.slide_out_left
        );

        // Replace the current fragment with the DiaryFragment
        fragmentTransaction.replace(R.id.searchviewFrameLayout, new DiaryFragment());
        // Commit the transaction
        fragmentTransaction.commit();
        activity.finish();

    }*/

}
