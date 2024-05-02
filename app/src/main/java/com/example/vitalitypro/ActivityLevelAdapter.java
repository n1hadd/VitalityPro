package com.example.vitalitypro;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityLevelAdapter extends RecyclerView.Adapter<ActivityLevelAdapter.ViewHolder> {

    private List<ActivityLevel> activityLevels;
    private int selectedItemPosition = RecyclerView.NO_POSITION;
    private OnItemClickListener listener;
    public ActivityLevelAdapter(List<ActivityLevel> activityLevels) {
        this.activityLevels = activityLevels;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
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

        // Set the appearance based on the selection state
        if (position == selectedItemPosition) {
            // Selected state
            holder.txtTitle.setTypeface(holder.txtTitle.getTypeface(), Typeface.BOLD);
            holder.relativeLayout.setBackgroundResource(R.drawable.activity_level_selected_item);
            holder.txtTitle.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.btnPressed));
        } else {
            // Default state
            holder.txtTitle.setTypeface(null, Typeface.NORMAL);
            holder.relativeLayout.setBackgroundResource(R.drawable.activity_level_cardview_border);
            holder.txtTitle.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        }

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            // Toggle the selection state
            if (selectedItemPosition == position) {
                // Deselect the item
                selectedItemPosition = RecyclerView.NO_POSITION;
            } else {
                // Select the item
                selectedItemPosition = position;
            }
            // Notify the listener
            if (listener != null) {
                listener.onItemClick(selectedItemPosition);
            }
            // Refresh the view to reflect the selection change
            notifyDataSetChanged();
        });
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

        private TextView txtTitle;
        private TextView txtDescription;
        private RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutAL);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }



}

