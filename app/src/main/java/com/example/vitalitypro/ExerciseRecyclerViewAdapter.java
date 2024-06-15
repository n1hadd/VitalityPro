package com.example.vitalitypro;

import android.content.Context;
import android.text.Html;
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
        // Set text with HTML formatting for bold and larger font size
        holder.txtExerciseName.setText(Html.fromHtml("<b><font size='6'>Activity:</font></b> " + exercise.getName()));
        holder.txtDuration.setText(Html.fromHtml("<b><font size='6'>Duration:</font></b> " + exercise.getDuration()+" min"));
        holder.txtCaloriesBurned.setText(Html.fromHtml("<b><font size='6'>Calories Burned:</font></b> " + exercise.getCalories_burned()+" kcal"));
    }

    @Override
    public int getItemCount() {
        if(exerciseList != null){
            return exerciseList.size();
        }
        return 0;
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
