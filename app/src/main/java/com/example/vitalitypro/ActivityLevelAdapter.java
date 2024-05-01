package com.example.vitalitypro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityLevelAdapter extends RecyclerView.Adapter<ActivityLevelAdapter.ViewHolder> {

    private List<ActivityLevel> activityLevels;
    private int selectedItemPosition = RecyclerView.NO_POSITION;

    private int selectedItem = -1; // Track the currently selected item

    public ActivityLevelAdapter(List<ActivityLevel> activityLevels) {
        this.activityLevels = activityLevels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_level_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActivityLevel activityLevel = activityLevels.get(position);
        holder.txtTitle.setText(activityLevel.getTitle());
        holder.txtDescription.setText(activityLevel.getDescription());

        // Set the item as selected if it matches the selected position
        holder.itemView.setSelected(selectedItemPosition == position);
    }

    @Override
    public int getItemCount() {
        return activityLevels.size();
    }

    public void setActivityLevels(List<ActivityLevel> activityLevels) {
        this.activityLevels = activityLevels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }



}

