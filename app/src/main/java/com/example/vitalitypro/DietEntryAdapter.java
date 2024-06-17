package com.example.vitalitypro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DietEntryAdapter extends RecyclerView.Adapter<DietEntryAdapter.DietEntryViewHolder> {

    private Context context;
    private List<DietEntry> dietEntryList;

    public DietEntryAdapter(Context context, List<DietEntry> dietEntryList) {
        this.context = context;
        this.dietEntryList = dietEntryList;
    }

    @NonNull
    @Override
    public DietEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diet_entries_layout, parent, false);
        return new DietEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DietEntryViewHolder holder, int position) {
        DietEntry dietEntry = dietEntryList.get(position);

        holder.txtDate.setText(dietEntry.getDate());
        holder.caloriesEaten.setText("Calories eaten: "+dietEntry.getCalories_eaten());

        /*holder.dietEntryRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dietEntryList.size();
    }


    public static class DietEntryViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout dietEntryRL;
        private TextView txtDate;
        private TextView caloriesEaten;

        public DietEntryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            caloriesEaten = itemView.findViewById(R.id.caloriesEaten);
            dietEntryRL = itemView.findViewById(R.id.dietEntryRL);

        }
    }


}

