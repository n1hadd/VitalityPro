package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList;
    private List<Food> loggedItems;


    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
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
                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Gson gson = new Gson();
                String foodJson = gson.toJson(food);

                // Retrieve the current list of selected foods
                String existingFoodsJson = sharedPreferences.getString("selected_foods", "[]");
                List<Food> selectedFoods = gson.fromJson(existingFoodsJson, new TypeToken<List<Food>>() {}.getType());

                // Add the new food item
                selectedFoods.add(food);

                // Save the updated list back to SharedPreferences
                String updatedFoodsJson = gson.toJson(selectedFoods);
                editor.putString("selected_foods", updatedFoodsJson);
                editor.apply();
                Toast.makeText(v.getContext(), "Selected food logged.", Toast.LENGTH_SHORT).show();

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


}
