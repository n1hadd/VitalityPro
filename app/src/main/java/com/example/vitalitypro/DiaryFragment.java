package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiaryFragment newInstance(String param1, String param2) {
        DiaryFragment fragment = new DiaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private static final String TAG = "Diary Fragment";
    private ScrollView scrollView;
    private CardView cardView, macronutrients;
    private RelativeLayout cardRelativeLayout;
    private ConstraintLayout macronutrientsConsLayout;
    private TextView txtEaten, txtRemaining, txtCaloriesCount, txtKcal, txtCaloriesRemainingCount, txtNutrients ;
    private ProgressBar progressBar, dailyGoalProgressBar;
    private TextView dailyGoal, dailyGoalCalories;
    private int currentProgress = 0;
    private SharedPreferences sharedPreferences;

    private PieChart pieChart;

    private ImageView imgOptions;
    private RelativeLayout relativeLayout2,relativeLayout3, relativeLayout4;
    private ProgressBar progressCarbs, progressProteins, progressFats;
    private TextView txtCarbs, txtProteins, txtFats;
    private RecyclerView mealsRecylcerView;
    private MealRecyclerViewAdapter mealRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diary, container, false);
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        initViews(rootView);
        updateProgressBar(currentProgress);
        initProgressBarsNutrients();
        initMealsRecylcerView();


        UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(getContext());
        int dailyCalorieIntake = userDatabaseHandler.getDailyCalorieIntake(sharedPreferences.getString("username_pref_key",""));
        txtCaloriesRemainingCount.setText(String.valueOf(dailyCalorieIntake));
        dailyGoalCalories.setText(String.valueOf(dailyCalorieIntake));

        return rootView;
    }

    private void initMealsRecylcerView() {
        ArrayList<Meal> meals = new ArrayList<>();
        meals.add(new Meal("Breakfast", R.drawable.breakfast));
        meals.add(new Meal("Lunch", R.drawable.lunch));
        meals.add(new Meal("Snack", R.drawable.snack));
        meals.add(new Meal("Dinner", R.drawable.dinner));


        mealRecyclerViewAdapter = new MealRecyclerViewAdapter(requireContext(), getChildFragmentManager());
        mealsRecylcerView.setAdapter(mealRecyclerViewAdapter);
        mealsRecylcerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mealRecyclerViewAdapter.setMeals(meals);

    }

    private void initProgressBarsNutrients() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(getContext());
        editor.putString("key_carbs_percentage", "0.4");
        editor.putString("key_proteins_percentage", "0.4");
        editor.putString("key_fats_percentage", "0.2");

        editor.putInt("key_carb_grams_eaten", 0);
        editor.putInt("key_protein_grams_eaten", 0);
        editor.putInt("key_fat_grams_eaten", 0);

        editor.apply();

        int carbCalories = (int)(userDatabaseHandler.getDailyCalorieIntake(sharedPreferences.getString("username_pref_key", "")) * Double.valueOf(sharedPreferences.getString("key_carbs_percentage", "")));
        int proteinCalories = (int)(userDatabaseHandler.getDailyCalorieIntake(sharedPreferences.getString("username_pref_key", "")) * Double.valueOf(sharedPreferences.getString("key_proteins_percentage", "")));
        int fatCalories = (int)(userDatabaseHandler.getDailyCalorieIntake(sharedPreferences.getString("username_pref_key", "")) * Double.valueOf(sharedPreferences.getString("key_fats_percentage", "")));

        int carbGrams = carbCalories / 4;
        int proteinGrams = proteinCalories / 4;
        int fatGrams = fatCalories / 9;

        Log.d(TAG, "Carb calories: "+carbCalories+";  Carbs in grams: "+carbGrams+"; Protein calories: "+proteinCalories+"; Proteins in grams: "+proteinGrams+"; Fats calories: "+fatCalories+"; Fats in grams: "+fatGrams);

        // set eaten carbs values, eaten carbs in bold
        int eatenCarbs = sharedPreferences.getInt("key_carb_grams_eaten", -1);
        String text = eatenCarbs + "\n/" + carbGrams;
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, String.valueOf(eatenCarbs).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, String.valueOf(eatenCarbs).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Adjust the size as per requirement
        txtCarbs.setText(spannableString);

        // For txtProteins
        int eatenProteins = sharedPreferences.getInt("key_protein_grams_eaten", -1);
        String proteinsText = eatenProteins + "\n/" + proteinGrams;
        SpannableString spannableProteins = new SpannableString(proteinsText);
        spannableProteins.setSpan(new StyleSpan(Typeface.BOLD), 0, String.valueOf(eatenProteins).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableProteins.setSpan(new RelativeSizeSpan(1.5f), 0, String.valueOf(eatenProteins).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtProteins.setText(spannableProteins);

        // For txtFats
        int eatenFats = sharedPreferences.getInt("key_fat_grams_eaten", -1);
        String fatsText = eatenFats + "\n/" + fatGrams;
        SpannableString spannableFats = new SpannableString(fatsText);
        spannableFats.setSpan(new StyleSpan(Typeface.BOLD), 0, String.valueOf(eatenFats).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableFats.setSpan(new RelativeSizeSpan(1.5f), 0, String.valueOf(eatenFats).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtFats.setText(spannableFats);

        imgOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMacroNutrientsFragment();
            }
        });

    }

    public void openMacroNutrientsFragment(){
        // Create an instance of the FourthFragment
        MacroNutrientsFragment macroNutrientsFragment = new MacroNutrientsFragment();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        // Replace the current fragment with the FourthFragment
        fragmentTransaction.replace(R.id.frameLayout, macroNutrientsFragment);

        // Add the transaction to the back stack (optional)

        // Commit the transaction
        fragmentTransaction.commit();
    }


    private void updateProgressBar(int currentProgress) {
        progressBar.setProgress(currentProgress);
    }

    private void initViews(View rootView) {
        scrollView = rootView.findViewById(R.id.scrollView);
        cardView = rootView.findViewById(R.id.cardView);
        cardRelativeLayout = rootView.findViewById(R.id.cardRelativeLayout);
        txtEaten = rootView.findViewById(R.id.txtEaten);
        txtRemaining = rootView.findViewById(R.id.txtRemaining);
        txtCaloriesCount = rootView.findViewById(R.id.txtCaloriesCount);
        txtKcal = rootView.findViewById(R.id.txtKcal);
        txtCaloriesRemainingCount = rootView.findViewById(R.id.txtCaloriesRemainingCount);
        progressBar = rootView.findViewById(R.id.progressBar);
        dailyGoalProgressBar = rootView.findViewById(R.id.dailyGoalProgressBar);
        dailyGoal = rootView.findViewById(R.id.dailyGoal);
        dailyGoalCalories = rootView.findViewById(R.id.dailyGoalCalories);
        macronutrients = rootView.findViewById(R.id.macronutrients);
        macronutrientsConsLayout = rootView.findViewById(R.id.macronutrientsConsLayout);
        imgOptions = rootView.findViewById(R.id.imgOptions);
        relativeLayout2 = rootView.findViewById(R.id.relativeLayout2);
        progressCarbs = rootView.findViewById(R.id.progressCarbs);
        txtCarbs = rootView.findViewById(R.id.txtCarbs);
        relativeLayout3 = rootView.findViewById(R.id.relativeLayout3);
        progressProteins = rootView.findViewById(R.id.progressProteins);
        txtProteins = rootView.findViewById(R.id.txtProteins);
        relativeLayout4 = rootView.findViewById(R.id.relativeLayout4);
        progressFats = rootView.findViewById(R.id.progressFats);
        txtFats = rootView.findViewById(R.id.txtFats);
        mealsRecylcerView = rootView.findViewById(R.id.mealsRecylcerView);
    }
}