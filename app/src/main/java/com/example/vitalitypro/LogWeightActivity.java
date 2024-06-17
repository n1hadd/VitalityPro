package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class LogWeightActivity extends AppCompatActivity {

    private TextView currWeight, txtSelectedWeight;
    private NumberPicker numberPicker;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_weight);
        initViews();
        handleNumberPick();
    }

    private void handleNumberPick() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int weight = getIntent().getIntExtra("weight",-1);


        numberPicker.setMaxValue(250);
        numberPicker.setMinValue(0);
        numberPicker.setValue(weight);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                txtSelectedWeight.setText(newVal+" kg");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newWeight = numberPicker.getValue();
                editor.putInt("weight_pref_key", newWeight);
                editor.apply();

                Intent intent = new Intent(LogWeightActivity.this, MainActivity.class);
                Toast.makeText(LogWeightActivity.this, "Weight Successfuly Changed", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        currWeight = findViewById(R.id.currWeight);
        txtSelectedWeight = findViewById(R.id.txtSelectedWeight);
        numberPicker = findViewById(R.id.numberPicker);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    public void openDiaryFragment(){
        Fragment fragment = new DiaryFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

}