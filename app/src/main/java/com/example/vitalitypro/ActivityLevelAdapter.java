package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedPreferences;
    private Context context;
    private OnItemClickListener listener;

    public ActivityLevelAdapter(Context context, List<ActivityLevel> activityLevels) {
        this.context = context;
        this.activityLevels = activityLevels;
        this.sharedPreferences = context.getSharedPreferences("activity_level_prefs", Context.MODE_PRIVATE);
    }

    public int getSelectedItemPosition() {
        return sharedPreferences.getInt("selected_activity_level_position", RecyclerView.NO_POSITION);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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

        // Read the stored position from SharedPreferences
        int selectedPosition = sharedPreferences.getInt("selected_activity_level_position", RecyclerView.NO_POSITION);

        // Set the appearance based on the selection state
        if (position == selectedPosition) {
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
            // Save the selected position in SharedPreferences
            sharedPreferences.edit().putInt("selected_activity_level_position", position).apply();

            // Notify the listener
            if (listener != null) {
                listener.onItemClick(position);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
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


