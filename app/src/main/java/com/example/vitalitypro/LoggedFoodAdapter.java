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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoggedFoodAdapter extends RecyclerView.Adapter<LoggedFoodAdapter.LoggedFoodViewHolder> {

    private final static String TAG = "LoggedFoodAdapter";
    private List<Food> foodList = new ArrayList<>();
    private String mealType;
    private Context context;
    private FoodAdapter.OnFoodLoggedListener logged_listener;
    private OnItemClickListener listener;

    private OnFoodDeletedListener deleted_listener;


    public LoggedFoodAdapter(List<Food> foodList, String mealType, Context context, FoodAdapter.OnFoodLoggedListener logged_listener, OnFoodDeletedListener deleted_listener) {
        this.foodList = foodList;
        this.mealType = mealType;
        this.context = context;
        this.logged_listener = logged_listener;
        this.deleted_listener = deleted_listener;
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

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(food);
                }
            }
        });

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, TAG+" Started!"+" Deleting from: "+mealType);

                SharedPreferences sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();

                String existingFoodsJson = sharedPreferences.getString(mealType, null);
                Type type = new TypeToken<List<Food>>() {}.getType();
                List<Food> selectedFoods = gson.fromJson(existingFoodsJson, type);

                if (selectedFoods == null) {
                    selectedFoods = new ArrayList<>();
                }

                selectedFoods.remove(position);
                Log.d(TAG, "Food "+food.getDescription()+" deleted!");

                String updatedFoodsJson = gson.toJson(selectedFoods);
                editor.remove(mealType);
                editor.putString(mealType, updatedFoodsJson);
                editor.apply();


                if(deleted_listener != null){
                    deleted_listener.onFoodDeleted(mealType);
                }
                else{
                    Log.d(TAG, "deleted_listener is null!");
                }


                Toast.makeText(context, "Food removed from "+mealType, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            }
        });


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
        RelativeLayout relativeLayout;

        public LoggedFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            imgRemove = itemView.findViewById(R.id.imgRemove);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Food food);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnFoodDeletedListener {
        void onFoodDeleted(String mealType);
    }
}
