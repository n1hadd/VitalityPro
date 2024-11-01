package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaryFragment extends Fragment implements FoodAdapter.OnFoodLoggedListener, FoodAdapter.OnItemClickListener, LoggedFoodAdapter.OnFoodDeletedListener, ExerciseActivity.onExerciseLogged, MainActivity.ScrollListener {

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

    private CardView cwBreakFast, cwLunch, cwSnack, cwDinner;

    /* BREAKFAST CARD VIEW */
    private RelativeLayout breakfastcollapsedRelativeLayout, breakfastexpandedRelativeLayout;
    private ProgressBar breakfastCaloriesProgress;
    private TextView txtBreakfastTitle, txtCalories, emptyLog;
    private ImageView imgMealBreakfast;
    private MaterialButton btnAddBreakfast;
    private RecyclerView loggedBreakfastRecyclerView;

    /* Lunch CARD VIEW */
    private RelativeLayout lunchcollapsedRelativeLayout, lunchexpandedRelativeLayout;
    private ProgressBar lunchCaloriesProgress;
    private TextView txtLunchTitle, txtCaloriesLunch, emptyLogLunch;
    private ImageView imgMealLunch;
    private MaterialButton btnAddLunch;
    private RecyclerView loggedLunchRecyclerView;

    /* SNACK CARD VIEW */
    private RelativeLayout snackcollapsedRelativeLayout, snackexpandedRelativeLayout;
    private ProgressBar snackCaloriesProgress;
    private TextView txtSnackTitle, txtCaloriesSnack, emptyLogSnack;
    private ImageView imgMealSnack;
    private MaterialButton btnAddSnack;
    private RecyclerView loggedSnackRecyclerView;

    /* DINNER CARD VIEW */
    private RelativeLayout dinnercollapsedRelativeLayout, dinnerexpandedRelativeLayout;
    private ProgressBar dinnerCaloriesProgress;
    private TextView txtDinnerTitle, txtCaloriesDinner, emptyLogDinner;
    private ImageView imgMealDinner;
    private MaterialButton btnAddDinner;
    private RecyclerView loggedDinnerRecyclerView;

    private LoggedFoodAdapter breakfastAdapter;
    private LoggedFoodAdapter lunchAdapter;
    private LoggedFoodAdapter snackAdapter;
    private LoggedFoodAdapter dinnerAdapter;

    private String mealType;

    private int caloriesEaten;

    private int dailyCalorieIntake;
    private int breakfastCalories;
    private int lunchCalories;
    private int snackCalories;
    private int dinnerCalories;
    private TextView txtOverWarning;

    // CARD VIEW ACTIVITY
    private CardView activityCardView;
    private RelativeLayout activityRelativeLayout, activityCollapsedRelativeLayout, activityExpandedRelativeLayout;
    private TextView txtActivity, activityCalories, emptyLogActivity;
    private ImageView imgView;
    private MaterialButton btnAddActivity;
    private RecyclerView loggedActivitiesRecyclerView;

    private TextView carbsMacros, proteinsMacros, FatsMacros;

    // water intake cw
    private CardView cwWater;
    private ImageView imgAdd1,imgAdd2,imgAdd3,imgAdd4,imgAdd5,imgAdd6,imgAdd7,imgAdd8,imgAdd9,imgAdd10,
                      glass1,glass2,glass3,glass4,glass5,glass6,glass7,glass8,glass9,glass10;

    private TextView txtWaterIntake;


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
        //initMealsRecylcerView();
        initButtonsClicked();
        initDailyCaloriesSplit();


        UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(getContext());
        int dailyCalorieIntake = sharedPreferences.getInt("daily_calorie_intake", -1);
        txtCaloriesRemainingCount.setText(String.valueOf(dailyCalorieIntake));
        dailyGoalCalories.setText(String.valueOf(userDatabaseHandler.getDailyCalorieIntake(sharedPreferences.getString("username_pref_key",""))));

        setupRecyclerView(loggedBreakfastRecyclerView);
        setupRecyclerView(loggedLunchRecyclerView);
        setupRecyclerView(loggedSnackRecyclerView);
        setupRecyclerView(loggedDinnerRecyclerView);
        setupRecyclerView(loggedActivitiesRecyclerView);

        loadDataForMealType("breakfast");
        loadDataForMealType("lunch");
        loadDataForMealType("snack");
        loadDataForMealType("dinner");

        updateMealCalories("breakfast");
        updateMealCalories("lunch");
        updateMealCalories("snack");
        updateMealCalories("dinner");

        /*updateMealDataAfterDelete("breakfast");
        updateMealDataAfterDelete("lunch");
        updateMealDataAfterDelete("snack");
        updateMealDataAfterDelete("dinner");*/
        handleExerciseRecyclerView();
        handleWaterIntake();

        return rootView;
    }


    private void initDailyCaloriesSplit() {
        sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(getContext());
        dailyCalorieIntake = userDatabaseHandler.getDailyCalorieIntake(sharedPreferences.getString("username_pref_key",""));
        breakfastCalories = (int)(dailyCalorieIntake *0.2);
        lunchCalories = (int)(dailyCalorieIntake *0.4);
        snackCalories = (int)(dailyCalorieIntake *0.1);
        dinnerCalories = (int)(dailyCalorieIntake *0.3);

        editor.putInt("breakfastCalories", breakfastCalories);
        editor.putInt("lunchCalories", lunchCalories);
        editor.putInt("snackCalories", snackCalories);
        editor.putInt("dinnerCalories", dinnerCalories);



        txtCalories.setText(0+" / "+breakfastCalories+" kcal");
        txtCaloriesLunch.setText(0+" / "+lunchCalories+" kcal");
        txtCaloriesSnack.setText(0+" / "+snackCalories+" kcal");
        txtCaloriesDinner.setText(0+" / "+dinnerCalories+" kcal");
    }

    private void initButtonsClicked() {
        btnAddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealType = "breakfast";
                startFoodSearchViewActivity("breakfast");
            }
        });

        btnAddLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealType = "lunch";
                startFoodSearchViewActivity("lunch");
            }
        });

        btnAddSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealType = "snack";
                startFoodSearchViewActivity("snack");
            }
        });

        btnAddDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealType = "dinner";
                startFoodSearchViewActivity("dinner");
            }
        });

        btnAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExerciseActivity.class);
                intent.putExtra("daily_calorie_intake", sharedPreferences.getInt("daily_calorie_intake",-1));
                startActivity(intent);
            }
        });
    }

    /*private void initMealsRecylcerView() {
        ArrayList<Meal> meals = new ArrayList<>();
        meals.add(new Meal("Breakfast", R.drawable.breakfast));
        meals.add(new Meal("Lunch", R.drawable.lunch));
        meals.add(new Meal("Snack", R.drawable.snack));
        meals.add(new Meal("Dinner", R.drawable.dinner));


        mealRecyclerViewAdapter = new MealRecyclerViewAdapter(requireContext(), getChildFragmentManager());
        mealsRecylcerView.setAdapter(mealRecyclerViewAdapter);
        mealsRecylcerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mealRecyclerViewAdapter.setMeals(meals);

    }*/

    private int carbGrams;
    private int proteinGrams;
    private int fatGrams;

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

        /*int goalCalories = sharedPreferences.getInt("daily_calorie_intake", -1);
        Log.d("GOAL CALORIES", "GOAL CALORIES: "+goalCalories);*/

        int carbCalories = (int)(sharedPreferences.getInt("daily_calorie_intake", -1) * Double.valueOf(sharedPreferences.getString("key_carbs_percentage", "")));
        int proteinCalories = (int)(sharedPreferences.getInt("daily_calorie_intake", -1) * Double.valueOf(sharedPreferences.getString("key_proteins_percentage", "")));
        int fatCalories = (int)(sharedPreferences.getInt("daily_calorie_intake", -1) * Double.valueOf(sharedPreferences.getString("key_fats_percentage", "")));

        carbGrams = carbCalories / 4;
        proteinGrams = proteinCalories / 4;
        fatGrams = fatCalories / 9;

        editor.putInt("carb_grams", carbGrams);
        editor.putInt("protein_grams", proteinGrams);
        editor.putInt("fat_grams", fatGrams);
        editor.apply();


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



    }

    /*public void openMacroNutrientsFragment(){
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
    }*/


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
        relativeLayout2 = rootView.findViewById(R.id.relativeLayout2);
        progressCarbs = rootView.findViewById(R.id.progressCarbs);
        txtCarbs = rootView.findViewById(R.id.txtCarbs);
        relativeLayout3 = rootView.findViewById(R.id.relativeLayout3);
        progressProteins = rootView.findViewById(R.id.progressProteins);
        txtProteins = rootView.findViewById(R.id.txtProteins);
        relativeLayout4 = rootView.findViewById(R.id.relativeLayout4);
        progressFats = rootView.findViewById(R.id.progressFats);
        txtFats = rootView.findViewById(R.id.txtFats);


        // MEAL CARDVIEWS
        breakfastcollapsedRelativeLayout = rootView.findViewById(R.id.breakfastcollapsedRelativeLayout);
        breakfastexpandedRelativeLayout = rootView.findViewById(R.id.breakfastexpandedRelativeLayout);
        breakfastCaloriesProgress = rootView.findViewById(R.id.breakfastCaloriesProgress);
        txtBreakfastTitle = rootView.findViewById(R.id.txtBreakfastTitle);
        txtCalories = rootView.findViewById(R.id.txtCalories);
        emptyLog = rootView.findViewById(R.id.emptyLog);
        imgMealBreakfast = rootView.findViewById(R.id.imgMealBreakfast);
        btnAddBreakfast = rootView.findViewById(R.id.btnAddBreakfast);
        loggedBreakfastRecyclerView = rootView.findViewById(R.id.loggedBreakfastRecyclerView);


        lunchcollapsedRelativeLayout = rootView.findViewById(R.id.lunchcollapsedRelativeLayout);
        lunchexpandedRelativeLayout = rootView.findViewById(R.id.lunchexpandedRelativeLayout);
        lunchCaloriesProgress = rootView.findViewById(R.id.lunchCaloriesProgress);
        txtLunchTitle = rootView.findViewById(R.id.txtLunchTitle);
        txtCaloriesLunch = rootView.findViewById(R.id.txtCaloriesLunch);
        emptyLogLunch = rootView.findViewById(R.id.emptyLogLunch);
        imgMealLunch = rootView.findViewById(R.id.imgMealLunch);
        btnAddLunch = rootView.findViewById(R.id.btnAddLunch);
        loggedLunchRecyclerView = rootView.findViewById(R.id.loggedLunchRecyclerView);

        snackcollapsedRelativeLayout = rootView.findViewById(R.id.snackcollapsedRelativeLayout);
        snackexpandedRelativeLayout = rootView.findViewById(R.id.snackexpandedRelativeLayout);
        snackCaloriesProgress = rootView.findViewById(R.id.snackCaloriesProgress);
        txtSnackTitle = rootView.findViewById(R.id.txtSnackTitle);
        txtCaloriesSnack = rootView.findViewById(R.id.txtCaloriesSnack);
        emptyLogSnack = rootView.findViewById(R.id.emptyLogSnack);
        imgMealSnack = rootView.findViewById(R.id.imgMealSnack);
        btnAddSnack = rootView.findViewById(R.id.btnAddSnack);
        loggedSnackRecyclerView = rootView.findViewById(R.id.loggedSnackRecyclerView);

        dinnercollapsedRelativeLayout = rootView.findViewById(R.id.dinnercollapsedRelativeLayout);
        dinnerexpandedRelativeLayout = rootView.findViewById(R.id.dinnerexpandedRelativeLayout);
        dinnerCaloriesProgress = rootView.findViewById(R.id.dinnerCaloriesProgress);
        txtDinnerTitle = rootView.findViewById(R.id.txtDinnerTitle);
        txtCaloriesDinner = rootView.findViewById(R.id.txtCaloriesDinner);
        emptyLogDinner = rootView.findViewById(R.id.emptyLogDinner);
        imgMealDinner = rootView.findViewById(R.id.imgMealDinner);
        btnAddDinner = rootView.findViewById(R.id.btnAddDinner);
        loggedDinnerRecyclerView = rootView.findViewById(R.id.loggedDinnerRecyclerView);
        txtOverWarning = rootView.findViewById(R.id.txtOverWarning);

        // activity card view
        activityCardView = rootView.findViewById(R.id.activityCardView);
        activityRelativeLayout = rootView.findViewById(R.id.activityRelativeLayout);
        activityCollapsedRelativeLayout = rootView.findViewById(R.id.activityCollapsedRelativeLayout);
        txtActivity = rootView.findViewById(R.id.txtActivity);
        activityCalories = rootView.findViewById(R.id.activityCalories);
        emptyLogActivity = rootView.findViewById(R.id.emptyLogActivity);
        imgView = rootView.findViewById(R.id.imgView);
        btnAddActivity = rootView.findViewById(R.id.btnAddActivity);
        activityExpandedRelativeLayout = rootView.findViewById(R.id.activityExpandedRelativeLayout);
        loggedActivitiesRecyclerView = rootView.findViewById(R.id.loggedActivitiesRecyclerView);


        // water cardview
        cwWater = rootView.findViewById(R.id.cwWater);

        imgAdd1 = rootView.findViewById(R.id.imgAdd1);
        imgAdd2 = rootView.findViewById(R.id.imgAdd2);
        imgAdd3 = rootView.findViewById(R.id.imgAdd3);
        imgAdd4 = rootView.findViewById(R.id.imgAdd4);
        imgAdd5 = rootView.findViewById(R.id.imgAdd5);
        imgAdd6 = rootView.findViewById(R.id.imgAdd6);
        imgAdd7 = rootView.findViewById(R.id.imgAdd7);
        imgAdd8 = rootView.findViewById(R.id.imgAdd8);
        imgAdd9 = rootView.findViewById(R.id.imgAdd9);
        imgAdd10 = rootView.findViewById(R.id.imgAdd10);
        glass1 = rootView.findViewById(R.id.glass1);
        glass2 = rootView.findViewById(R.id.glass2);
        glass3 = rootView.findViewById(R.id.glass3);
        glass4 = rootView.findViewById(R.id.glass4);
        glass5 = rootView.findViewById(R.id.glass5);
        glass6 = rootView.findViewById(R.id.glass6);
        glass7 = rootView.findViewById(R.id.glass7);
        glass8 = rootView.findViewById(R.id.glass8);
        glass9 = rootView.findViewById(R.id.glass9);
        glass10 = rootView.findViewById(R.id.glass10);
        txtWaterIntake = rootView.findViewById(R.id.txtWaterIntake);
    }


    // start FoodSearchViewActivity
    private void startFoodSearchViewActivity(String mealType) {
        Intent intent = new Intent(getActivity(), FoodSearchViewActivity.class);
        intent.putExtra("meal_type", mealType);
        startActivity(intent);
    }

    @Override
    public void onFoodLogged(String mealType) {
        loadDataForMealType(mealType);
        updateMealCalories(mealType);
    }

    private int calculateDailyEatenCalories() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String breakfastFood = sharedPreferences.getString("breakfast", "[]");
        String lunchFood = sharedPreferences.getString("lunch", "[]");
        String snackFood = sharedPreferences.getString("snack", "[]");
        String dinnerFood = sharedPreferences.getString("dinner", "[]");
        Type type = new TypeToken<List<Food>>() {
        }.getType();

        List<Food> breakfast = gson.fromJson(breakfastFood, type);
        List<Food> lunch = gson.fromJson(lunchFood, type);
        List<Food> snack = gson.fromJson(snackFood, type);
        List<Food> dinner = gson.fromJson(dinnerFood, type);

        int breakfastCal = 0;
        int lunchCal = 0;
        int snackCal = 0;
        int dinnerCal = 0;

        if(breakfast != null && !breakfast.isEmpty()){
            for(Food food : breakfast){
                breakfastCal += food.getCalories();
            }
        }

        if(lunch != null && !lunch.isEmpty()){
            for(Food food : lunch){
                lunchCal += food.getCalories();
            }
        }

        if(snack != null && !snack.isEmpty()){
            for(Food food : snack){
                snackCal += food.getCalories();
            }
        }

        if(dinner != null && !dinner.isEmpty()){
            for(Food food : dinner){
                dinnerCal += food.getCalories();
            }
        }

        int totalCal = breakfastCal + lunchCal + snackCal + dinnerCal;
        return totalCal;
    }

    private void updateMealCalories(String mealType) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String existingFoodsJson = sharedPreferences.getString(mealType, "[]");
        Type type = new TypeToken<List<Food>>() {
        }.getType();
        List<Food> selectedFoods = gson.fromJson(existingFoodsJson, type);

        if (selectedFoods != null && !selectedFoods.isEmpty()) {
            int totalCalories = 0;
            for (Food food : selectedFoods) {
                totalCalories += food.getCalories();
            }
            caloriesEaten = totalCalories;

            switch (mealType) {
                case "breakfast":
                    txtCalories.setText(caloriesEaten + " / " + breakfastCalories + " kcal");
                    breakfastCaloriesProgress.setMax(breakfastCalories);
                    breakfastCaloriesProgress.setProgress(caloriesEaten);
                    break;
                case "lunch":
                    txtCaloriesLunch.setText(caloriesEaten + " / " + lunchCalories + " kcal");
                    lunchCaloriesProgress.setMax(lunchCalories);
                    lunchCaloriesProgress.setProgress(caloriesEaten);
                    break;
                case "snack":
                    txtCaloriesSnack.setText(caloriesEaten + " / " + snackCalories + " kcal");
                    snackCaloriesProgress.setMax(snackCalories);
                    snackCaloriesProgress.setProgress(caloriesEaten);
                    break;
                case "dinner":
                    txtCaloriesDinner.setText(caloriesEaten + " / " + dinnerCalories + " kcal");
                    dinnerCaloriesProgress.setMax(dinnerCalories);
                    dinnerCaloriesProgress.setProgress(caloriesEaten);
                    break;
            }

            int total = calculateDailyEatenCalories();
            String username = sharedPreferences.getString("username_pref_key", "");
            UserDatabaseHandler databaseHandler = new UserDatabaseHandler(getContext());
            databaseHandler.updateEatenCalories(username, total);

            // MAIN PROGRESS BAR PROGRESS HANDLING
            txtCaloriesCount.setText(String.valueOf(total));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("calories_eaten", total);
            editor.commit();

            txtCaloriesRemainingCount.setText(String.valueOf(sharedPreferences.getInt("daily_calorie_intake",-1)));
            int goalIntake = sharedPreferences.getInt("daily_calorie_intake", -1);
            progressBar.setMax(goalIntake);
            progressBar.setProgress(total, true);
            if (total > goalIntake){
                cardRelativeLayout.setBackgroundColor(Color.parseColor("#f73b31"));
                txtRemaining.setTextColor(Color.parseColor("#000000"));
                txtCaloriesRemainingCount.setTextColor(Color.parseColor("#000000"));
                dailyGoalCalories.setTextColor(Color.parseColor("#000000"));
                dailyGoal.setTextColor(Color.parseColor("#000000"));
                txtOverWarning.setVisibility(View.VISIBLE);
                txtOverWarning.setText(total-goalIntake+" calories over daily goal");
            }
            else if(total == goalIntake){
                cardRelativeLayout.setBackgroundColor(Color.parseColor("#05d3bc"));
                txtRemaining.setTextColor(Color.parseColor("#008c82"));
                txtCaloriesRemainingCount.setTextColor(Color.parseColor("#008c82"));
                dailyGoalCalories.setTextColor(Color.parseColor("#008c82"));
                dailyGoal.setTextColor(Color.parseColor("#008c82"));
                //txtOverWarning.setVisibility(View.VISIBLE);
                txtOverWarning.setText("You have met your daily goal.");
            }
            else{
                cardRelativeLayout.setBackgroundColor(Color.parseColor("#05d3bc"));
                txtRemaining.setTextColor(Color.parseColor("#008c82"));
                txtCaloriesRemainingCount.setTextColor(Color.parseColor("#008c82"));
                dailyGoalCalories.setTextColor(Color.parseColor("#008c82"));
                dailyGoal.setTextColor(Color.parseColor("#008c82"));
                //txtOverWarning.setVisibility(View.GONE);
            }

            progressCarbs.setMax(carbGrams);
            progressProteins.setMax(proteinGrams);
            progressFats.setMax(fatGrams);

            int[] breakfastNutrients = getBreakfastMacronutrients();
            int[] lunchNutrients = getLunchMacronutrients();
            int[] snackNutrients = getSnackMacronutrients();
            int[] dinnerNutrients = getDinnerMacronutrients();

            int totalCarbs = breakfastNutrients[0] + lunchNutrients[0] + snackNutrients[0] + dinnerNutrients[0] ;
            int totalProteins = breakfastNutrients[1] + lunchNutrients[1] + snackNutrients[1] + dinnerNutrients[1] ;
            int totalFats = breakfastNutrients[2] + lunchNutrients[2] + snackNutrients[2] + dinnerNutrients[2] ;

            progressCarbs.setProgress(totalCarbs);
            progressProteins.setProgress(totalProteins);
            progressFats.setProgress(totalFats);

            //txtCarbs.setText(totalCarbs+"\n/"+ carbGrams);


            String text = totalCarbs + "\n/" + carbGrams;
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, String.valueOf(totalCarbs).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(1.2f), 0, String.valueOf(totalCarbs).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Adjust the size as per requirement
            txtCarbs.setText(spannableString);

            //txtProteins.setText(totalProteins+"\n/"+ proteinGrams);

            String textP = totalProteins + "\n/" + proteinGrams;
            SpannableString spannableStringP = new SpannableString(textP);
            spannableStringP.setSpan(new StyleSpan(Typeface.BOLD), 0, String.valueOf(totalProteins).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringP.setSpan(new RelativeSizeSpan(1.2f), 0, String.valueOf(totalProteins).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Adjust the size as per requirement
            txtProteins.setText(spannableStringP);


            //txtFats.setText(totalFats+"\n/"+ fatGrams);

            String textF = totalFats + "\n/" + fatGrams;
            SpannableString spannableStringF = new SpannableString(textF);
            spannableStringF.setSpan(new StyleSpan(Typeface.BOLD), 0, String.valueOf(totalFats).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringF.setSpan(new RelativeSizeSpan(1.2f), 0, String.valueOf(totalFats).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Adjust the size as per requirement
            txtFats.setText(spannableStringF);

        }
    }

    public int[] getBreakfastMacronutrients() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String breakfastFood = sharedPreferences.getString("breakfast", "[]");

        Type type = new TypeToken<List<Food>>() {
        }.getType();

        List<Food> breakfast = gson.fromJson(breakfastFood, type);


        if (breakfast != null && !breakfast.isEmpty()) {
            int carbs = 0;
            int proteins = 0;
            int fats = 0;
            for (Food food : breakfast) {
                if (food != null) {
                    for (Food.FoodNutrient nutrient : food.getFoodNutrients()) {
                        switch (nutrient.getNutrientName()) {
                            case "Carbohydrate, by difference":
                                carbs += (int) (nutrient.getValue());
                                break;
                            case "Protein":
                                proteins += (int) (nutrient.getValue());
                                break;
                            case "Total lipid (fat)":
                                fats += (int) (nutrient.getValue());
                                break;
                        }
                    }
                }
            }
            int[] nutrients = new int[3];
            nutrients[0] = carbs;
            nutrients[1] = proteins;
            nutrients[2] = fats;
            return nutrients;
        }
        return new int[]{0, 0, 0};
    }

    public int[] getLunchMacronutrients() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String lunchFood = sharedPreferences.getString("lunch", "[]");

        Type type = new TypeToken<List<Food>>() {
        }.getType();

        List<Food> lunch = gson.fromJson(lunchFood, type);


        if (lunch != null && !lunch.isEmpty()) {
            int carbs = 0;
            int proteins = 0;
            int fats = 0;
            for (Food food : lunch) {
                if (food != null) {
                    for (Food.FoodNutrient nutrient : food.getFoodNutrients()) {
                        switch (nutrient.getNutrientName()) {
                            case "Carbohydrate, by difference":
                                carbs += (int)Math.floor(nutrient.getValue());
                                break;
                            case "Protein":
                                proteins += (int)Math.floor(nutrient.getValue());
                                break;
                            case "Total lipid (fat)":
                                fats += (int)Math.floor(nutrient.getValue());
                                break;
                        }
                    }
                }
            }
            int[] nutrients = new int[3];
            nutrients[0] = carbs;
            nutrients[1] = proteins;
            nutrients[2] = fats;
            return nutrients;
        }
        return new int[]{0, 0, 0};
    }

    public int[] getSnackMacronutrients() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String snackFood = sharedPreferences.getString("snack", "[]");

        Type type = new TypeToken<List<Food>>() {
        }.getType();

        List<Food> snack = gson.fromJson(snackFood, type);


        if (snack != null && !snack.isEmpty()) {
            int carbs = 0;
            int proteins = 0;
            int fats = 0;
            for (Food food : snack) {
                if (food != null) {
                    for (Food.FoodNutrient nutrient : food.getFoodNutrients()) {
                        switch (nutrient.getNutrientName()) {
                            case "Carbohydrate, by difference":
                                carbs += (int)Math.floor(nutrient.getValue());
                                break;
                            case "Protein":
                                proteins += (int)Math.floor(nutrient.getValue());
                                break;
                            case "Total lipid (fat)":
                                fats += (int)Math.floor(nutrient.getValue());
                                break;
                        }
                    }
                }
            }
            int[] nutrients = new int[3];
            nutrients[0] = carbs;
            nutrients[1] = proteins;
            nutrients[2] = fats;
            return nutrients;
        }
        return new int[]{0, 0, 0};
    }

    public int[] getDinnerMacronutrients() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String dinnerFood = sharedPreferences.getString("dinner", "[]");

        Type type = new TypeToken<List<Food>>() {
        }.getType();

        List<Food> dinner = gson.fromJson(dinnerFood, type);


        if (dinner != null && !dinner.isEmpty()) {
            int carbs = 0;
            int proteins = 0;
            int fats = 0;
            for (Food food : dinner) {
                if (food != null) {
                    for (Food.FoodNutrient nutrient : food.getFoodNutrients()) {
                        switch (nutrient.getNutrientName()) {
                            case "Carbohydrate, by difference":
                                carbs += (int)Math.floor(nutrient.getValue());
                                break;
                            case "Protein":
                                proteins += (int)Math.floor(nutrient.getValue());
                                break;
                            case "Total lipid (fat)":
                                fats += (int) Math.floor(nutrient.getValue());
                                break;
                        }
                    }
                }
            }
            int[] nutrients = new int[3];
            nutrients[0] = carbs;
            nutrients[1] = proteins;
            nutrients[2] = fats;
            return nutrients;
        }
        return new int[]{0, 0, 0};
    }




    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void loadDataForMealType(String mealType) {
        Log.d(TAG, "MealType: "+mealType);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String existingFoodsJson = sharedPreferences.getString(mealType, null);
        Type type = new TypeToken<List<Food>>() {}.getType();
        List<Food> selectedFoods = gson.fromJson(existingFoodsJson, type);

        if (selectedFoods != null) {
            switch (mealType) {
                case "breakfast":
                    breakfastAdapter = new LoggedFoodAdapter(selectedFoods, mealType, getContext(), this, this);
                    breakfastAdapter.setOnItemClickListener(this::onItemClick);
                    loggedBreakfastRecyclerView.setAdapter(breakfastAdapter);
                    breakfastexpandedRelativeLayout.setVisibility(View.VISIBLE);
                    emptyLog.setVisibility(View.GONE);
                    break;
                case "lunch":
                    lunchAdapter = new LoggedFoodAdapter(selectedFoods, mealType, getContext(), this, this);
                    lunchAdapter.setOnItemClickListener(this::onItemClick);
                    loggedLunchRecyclerView.setAdapter(lunchAdapter);
                    lunchexpandedRelativeLayout.setVisibility(View.VISIBLE);
                    emptyLogLunch.setVisibility(View.GONE);
                    break;
                case "snack":
                    snackAdapter = new LoggedFoodAdapter(selectedFoods, mealType, getContext(), this, this);
                    snackAdapter.setOnItemClickListener(this::onItemClick);
                    loggedSnackRecyclerView.setAdapter(snackAdapter);
                    snackexpandedRelativeLayout.setVisibility(View.VISIBLE);
                    emptyLogSnack.setVisibility(View.GONE);
                    break;
                case "dinner":
                    dinnerAdapter = new LoggedFoodAdapter(selectedFoods, mealType, getContext(), this, this);
                    dinnerAdapter.setOnItemClickListener(this::onItemClick);
                    loggedDinnerRecyclerView.setAdapter(dinnerAdapter);
                    dinnerexpandedRelativeLayout.setVisibility(View.VISIBLE);
                    emptyLogDinner.setVisibility(View.GONE);
                    break;
            }
        } else {
            switch (mealType) {
                case "breakfast":
                    breakfastexpandedRelativeLayout.setVisibility(View.GONE);
                    emptyLog.setVisibility(View.VISIBLE);
                    break;
                case "lunch":
                    lunchexpandedRelativeLayout.setVisibility(View.GONE);
                    emptyLogLunch.setVisibility(View.VISIBLE);
                    break;
                case "snack":
                    snackexpandedRelativeLayout.setVisibility(View.GONE);
                    emptyLogSnack.setVisibility(View.VISIBLE);
                    break;
                case "dinner":
                    dinnerexpandedRelativeLayout.setVisibility(View.GONE);
                    emptyLogDinner.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }

    @Override
    public void onItemClick(Food food) {
        Intent intent = new Intent(getContext(), FoodDetailActivity.class);
        intent.putExtra("food", food);
        intent.putExtra("parent", "diary");
        intent.putExtra("mealType", mealType);
        startActivity(intent);
    }

    @Override
    public void onFoodDeleted(String mealType) {
        loadDataForMealType(mealType);
        updateMealCalories(mealType);
    }

    @Override
    public void onExerciseLogged() {
        handleExerciseRecyclerView();

    }

    private int caloriesBurned;
    private void handleExerciseRecyclerView() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String existingExercises = sharedPreferences.getString("exercises", null);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Type type = new TypeToken<List<Exercise>>(){}.getType();

        List<Exercise> exercises = gson.fromJson(existingExercises, type);

        ExerciseRecyclerViewAdapter exerciseAdapter = new ExerciseRecyclerViewAdapter(getContext(), exercises);
        loggedActivitiesRecyclerView.setAdapter(exerciseAdapter);
        activityExpandedRelativeLayout.setVisibility(View.VISIBLE);
        emptyLogActivity.setVisibility(View.GONE);

        caloriesBurned = 0;
        if(exercises !=null){
            for(Exercise e : exercises){
                caloriesBurned += (int) e.getCalories_burned();
            }
        }
        activityCalories.setText(caloriesBurned+" kcal");


    }
    private double waterIntakeGoal;
    private double waterDrunk;

    private void handleWaterIntake() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        waterIntakeGoal = sharedPreferences.getFloat("water_goal_intake",-1);
        waterDrunk = sharedPreferences.getFloat("water_drunk",-1);

        txtWaterIntake.setText(waterDrunk + " / " + String.format("%.2f", waterIntakeGoal) + " l");


        imgAdd1.setVisibility(sharedPreferences.getInt("imgAdd1_visibility", View.VISIBLE));
        glass1.setVisibility(sharedPreferences.getInt("glass1_visibility", View.GONE));

        imgAdd2.setVisibility(sharedPreferences.getInt("imgAdd2_visibility", View.GONE));
        glass2.setVisibility(sharedPreferences.getInt("glass2_visibility", View.GONE));

        imgAdd3.setVisibility(sharedPreferences.getInt("imgAdd3_visibility", View.GONE));
        glass3.setVisibility(sharedPreferences.getInt("glass3_visibility", View.GONE));

        imgAdd4.setVisibility(sharedPreferences.getInt("imgAdd4_visibility", View.GONE));
        glass4.setVisibility(sharedPreferences.getInt("glass4_visibility", View.GONE));

        imgAdd5.setVisibility(sharedPreferences.getInt("imgAdd5_visibility", View.GONE));
        glass5.setVisibility(sharedPreferences.getInt("glass5_visibility", View.GONE));

        imgAdd6.setVisibility(sharedPreferences.getInt("imgAdd6_visibility", View.GONE));
        glass6.setVisibility(sharedPreferences.getInt("glass6_visibility", View.GONE));

        imgAdd7.setVisibility(sharedPreferences.getInt("imgAdd7_visibility", View.GONE));
        glass7.setVisibility(sharedPreferences.getInt("glass7_visibility", View.GONE));

        imgAdd8.setVisibility(sharedPreferences.getInt("imgAdd8_visibility", View.GONE));
        glass8.setVisibility(sharedPreferences.getInt("glass8_visibility", View.GONE));

        imgAdd9.setVisibility(sharedPreferences.getInt("imgAdd9_visibility", View.GONE));
        glass9.setVisibility(sharedPreferences.getInt("glass9_visibility", View.GONE));

        imgAdd10.setVisibility(sharedPreferences.getInt("imgAdd10_visibility", View.GONE));
        glass10.setVisibility(sharedPreferences.getInt("glass10_visibility", View.GONE));

        imgAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd1.setVisibility(View.GONE);
                glass1.setVisibility(View.VISIBLE);
                imgAdd2.setVisibility(View.VISIBLE);
                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd1_visibility", View.GONE);
                editor.putInt("glass1_visibility", View.VISIBLE);
                editor.putInt("imgAdd2_visibility", View.VISIBLE);
                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });


        imgAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd2.setVisibility(View.GONE);
                glass2.setVisibility(View.VISIBLE);
                imgAdd3.setVisibility(View.VISIBLE);
                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd2_visibility", View.GONE);
                editor.putInt("glass2_visibility", View.VISIBLE);
                editor.putInt("imgAdd3_visibility", View.VISIBLE);
                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });

        imgAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd3.setVisibility(View.GONE);
                glass3.setVisibility(View.VISIBLE);
                imgAdd4.setVisibility(View.VISIBLE);
                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd3_visibility", View.GONE);
                editor.putInt("glass3_visibility", View.VISIBLE);
                editor.putInt("imgAdd4_visibility", View.VISIBLE);
                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });

        imgAdd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd4.setVisibility(View.GONE);
                glass4.setVisibility(View.VISIBLE);
                imgAdd5.setVisibility(View.VISIBLE);
                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd4_visibility", View.GONE);
                editor.putInt("glass4_visibility", View.VISIBLE);
                editor.putInt("imgAdd5_visibility", View.VISIBLE);
                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });

        imgAdd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd5.setVisibility(View.GONE);
                glass5.setVisibility(View.VISIBLE);
                imgAdd6.setVisibility(View.VISIBLE);
                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd5_visibility", View.GONE);
                editor.putInt("glass5_visibility", View.VISIBLE);
                editor.putInt("imgAdd6_visibility", View.VISIBLE);
                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });

        imgAdd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd6.setVisibility(View.GONE);
                glass6.setVisibility(View.VISIBLE);
                imgAdd7.setVisibility(View.VISIBLE);
                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd6_visibility", View.GONE);
                editor.putInt("glass6_visibility", View.VISIBLE);
                editor.putInt("imgAdd7_visibility", View.VISIBLE);
                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });

        imgAdd7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd7.setVisibility(View.GONE);
                glass7.setVisibility(View.VISIBLE);
                imgAdd8.setVisibility(View.VISIBLE);
                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd7_visibility", View.GONE);
                editor.putInt("glass7_visibility", View.VISIBLE);
                editor.putInt("imgAdd8_visibility", View.VISIBLE);
                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });
        imgAdd8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd8.setVisibility(View.GONE);
                glass8.setVisibility(View.VISIBLE);
                imgAdd9.setVisibility(View.VISIBLE);
                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd8_visibility", View.GONE);
                editor.putInt("glass8_visibility", View.VISIBLE);
                editor.putInt("imgAdd9_visibility", View.VISIBLE);
                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });

        imgAdd9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd9.setVisibility(View.GONE);
                glass9.setVisibility(View.VISIBLE);
                imgAdd10.setVisibility(View.VISIBLE);
                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd9_visibility", View.GONE);
                editor.putInt("glass9_visibility", View.VISIBLE);
                editor.putInt("imgAdd10_visibility", View.VISIBLE);
                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });

        imgAdd10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAdd10.setVisibility(View.GONE);
                glass10.setVisibility(View.VISIBLE);

                glass1.setVisibility(View.GONE);
                glass2.setVisibility(View.GONE);
                glass3.setVisibility(View.GONE);
                glass4.setVisibility(View.GONE);
                glass5.setVisibility(View.GONE);
                glass6.setVisibility(View.GONE);
                glass7.setVisibility(View.GONE);
                glass8.setVisibility(View.GONE);
                glass9.setVisibility(View.GONE);
                glass10.setVisibility(View.GONE);
                imgAdd1.setVisibility(View.VISIBLE);


                waterDrunk += 0.25;
                editor.putFloat("water_drunk", (float) waterDrunk);
                editor.putInt("imgAdd10_visibility", View.GONE);
                editor.putInt("glass10_visibility", View.VISIBLE);
                editor.putInt("imgAdd1_visibility", View.VISIBLE);

                editor.putInt("glass1_visibility", View.GONE);
                editor.putInt("glass2_visibility", View.GONE);
                editor.putInt("glass3_visibility", View.GONE);
                editor.putInt("glass4_visibility", View.GONE);
                editor.putInt("glass5_visibility", View.GONE);
                editor.putInt("glass6_visibility", View.GONE);
                editor.putInt("glass7_visibility", View.GONE);
                editor.putInt("glass8_visibility", View.GONE);
                editor.putInt("glass9_visibility", View.GONE);
                editor.putInt("glass10_visibility", View.GONE);

                editor.apply(); // Use apply() for asynchronous saving
                txtWaterIntake.setText(String.format("%.2f / %.2f l", waterDrunk, waterIntakeGoal));
            }
        });

    }


    @Override
    public void onScrollToWater() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                int y = cwWater.getTop();

                scrollView.smoothScrollTo(0, y);
            }
        });
    }
}