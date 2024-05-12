package com.example.vitalitypro;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MealRecyclerViewAdapter extends RecyclerView.Adapter<MealRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MealRecylcerViewAdapter";
    private List<Meal> meals;
    private Context context;

    public MealRecyclerViewAdapter(Context context) {
        this.context = context;
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
        holder.txtMealTitle.setText(meals.get(position).getTitle());
        Glide.with(context)
                .asBitmap()
                .load(meals.get(position).getImgUrl())
                .into(holder.imgMeal);
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
        private TextView txtMealTitle, emptyLog;
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
        }
    }


}
