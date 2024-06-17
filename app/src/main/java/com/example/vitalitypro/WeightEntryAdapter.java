package com.example.vitalitypro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeightEntryAdapter extends RecyclerView.Adapter<WeightEntryAdapter.DietEntryViewHolder> {

    private Context context;
    private List<WeightEntry> weightEntryList;

    public WeightEntryAdapter(Context context, List<WeightEntry> weightEntryList) {
        this.context = context;
        this.weightEntryList = weightEntryList;
    }

    @NonNull
    @Override
    public DietEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weight_entry_item, parent, false);
        return new DietEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DietEntryViewHolder holder, int position) {
        WeightEntry weightEntry = weightEntryList.get(position);

        holder.txtDate.setText(weightEntry.getDate());
        holder.txtWeight.setText(weightEntry.getWeight()+" kg");

        /*holder.dietEntryRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return weightEntryList.size();
    }


    public static class DietEntryViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDate;
        private TextView txtWeight;

        public DietEntryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            txtWeight = itemView.findViewById(R.id.txtWeight);

        }
    }


}

