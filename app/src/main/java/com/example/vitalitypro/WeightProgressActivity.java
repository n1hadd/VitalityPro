package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WeightProgressActivity extends AppCompatActivity {

    private RecyclerView weightEntryRW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_progress);
        initViews();
        hanldeEntries();
    }

    private void hanldeEntries() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        Type typeW = new TypeToken<List<WeightEntry>>() {}.getType();

        String date = DateUtils.getCurrentDate();
        String dateFormated = formatDate(DateUtils.getCurrentDate());
        List<WeightEntry> weightEntries = gson.fromJson(sharedPreferences.getString("weight_entry", null),typeW);
        if(weightEntries==null){
            weightEntries = new ArrayList<>();
        }
        weightEntryRW.setLayoutManager(new LinearLayoutManager(this));
        WeightEntryAdapter weightEntryAdapter = new WeightEntryAdapter(this, weightEntries);
        weightEntryRW.setAdapter(weightEntryAdapter);
    }

    private void initViews() {
        weightEntryRW = findViewById(R.id.weightEntryRW);
    }

    private String formatDate(String currentDate) {
        String[] tab = currentDate.split("-");
        String year = tab[0];
        String day = tab[2];
        String month = "";

        switch(Integer.parseInt(tab[1])) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
            default:
                month = "Invalid month";
                break;
        }

        return day + " " + month + " " + year;
    }
}