package com.example.vitalitypro;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList;

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodName.setText(food.getDescription());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
        }
    }
}
