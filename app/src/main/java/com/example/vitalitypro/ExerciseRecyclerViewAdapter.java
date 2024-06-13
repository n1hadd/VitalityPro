package com.example.vitalitypro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ExerciseViewHolder> {

    private Context context;
    private List<Exercise> exerciseList;

    public ExerciseRecyclerViewAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercise_rw_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.txtExerciseName.setText("Activity: "+exercise.getName());
        holder.txtDuration.setText("Duration: "+exercise.getDuration());
        holder.txtCaloriesBurned.setText("Calories Burned: "+exercise.getCalories_burned());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private TextView txtExerciseName;
        private TextView txtDuration;
        private TextView txtCaloriesBurned;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtExerciseName = itemView.findViewById(R.id.txtExerciseName);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtCaloriesBurned = itemView.findViewById(R.id.txtCaloriesBurned);

        }
    }
}
