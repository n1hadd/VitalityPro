package com.example.vitalitypro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LoggedFoodAdapter extends RecyclerView.Adapter<LoggedFoodAdapter.LoggedFoodViewHolder> {

    private List<Food> foodList = new ArrayList<>();
    private String mealType;
    private Context context;
    private FoodAdapter.OnFoodLoggedListener logged_listener;


    public LoggedFoodAdapter(List<Food> foodList, String mealType, Context context, FoodAdapter.OnFoodLoggedListener logged_listener) {
        this.foodList = foodList;
        this.mealType = mealType;
        this.context = context;
        this.logged_listener = logged_listener;
    }

    @NonNull
    @Override
    public LoggedFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new LoggedFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoggedFoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.txtFoodName.setText(food.getDescription());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setFoods(List<Food> foods) {
        this.foodList = foods;
        notifyDataSetChanged();
    }

    public class LoggedFoodViewHolder extends RecyclerView.ViewHolder {
        TextView txtFoodName;
        ImageView imgRemove;

        public LoggedFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            imgRemove = itemView.findViewById(R.id.imgRemove);
        }
    }


}
