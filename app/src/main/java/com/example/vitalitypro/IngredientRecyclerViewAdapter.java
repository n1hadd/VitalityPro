package com.example.vitalitypro;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class IngredientRecyclerViewAdapter extends  RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder>{
    public static final String TAG = "IngredientRecyclerViewAdaptet";

    private List<Ingredient> ingredients = new ArrayList<>();

    private Context mContext;
    public IngredientRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Started");
        holder.txtIngredientName.setText(ingredients.get(position).getName());
        holder.txtIngredientCalories.setText(String.valueOf(ingredients.get(position).getCalories()));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private RelativeLayout relativeLayout;
        private TextView txtIngredientName;
        private TextView txtIngredientCalories;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            txtIngredientName = itemView.findViewById(R.id.txtIngredientName);
            txtIngredientCalories = itemView.findViewById(R.id.txtIngredientCalories);
        }
    }
}
