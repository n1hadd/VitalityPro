package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.vitalitypro.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FoodAdapter.OnFoodLoggedListener {

    public final static String TAG = "Main Activity";

    ActivityMainBinding binding;
    private FrameLayout frameLayout;



    private FrameLayout halfScreenFrameLayout;
    private FloatingActionButton logButton;

    private ConstraintLayout logLayout;
    private MenuItem lastClickedItem = null;

    // QUICK LOG LAYOUT
    private ImageButton imgBtnActivity, imgBtnWeight, imgBtnWater, imgBtnBreakfast, imgBtnLunch, imgBtnSnack, imgBtnDinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initBottomNavigationBar();
        handleQuickLog();
        initFloatingActionButton();
        handleDayChange();
        openDiaryFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        DiaryFragment diaryFragment = (DiaryFragment) fragmentManager.findFragmentById(R.id.frameLayout);

        if (diaryFragment != null) {
            scrollListener = diaryFragment;
        } else {
            diaryFragment = new DiaryFragment();
            fragmentManager.beginTransaction().add(R.id.frameLayout, diaryFragment).commit();
            fragmentManager.executePendingTransactions();
            scrollListener = diaryFragment;
        }
    }



    private void handleQuickLog() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        imgBtnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
                intent.putExtra("daily_calorie_intake", sharedPreferences.getInt("daily_calorie_intake",-1));
                startActivity(intent);
            }
        });

        imgBtnWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogWeightActivity.class);
                intent.putExtra("weight", sharedPreferences.getInt("weight_pref_key", 0));
                startActivity(intent);

            }
        });

        imgBtnWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scrollListener!=null){
                    scrollListener.onScrollToWater();
                }
            }
        });

        imgBtnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodSearchViewActivity.class);
                intent.putExtra("meal_type", "breakfast");
                startActivity(intent);
            }
        });

        imgBtnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodSearchViewActivity.class);
                intent.putExtra("meal_type", "lunch");
                startActivity(intent);
            }
        });

        imgBtnSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodSearchViewActivity.class);
                intent.putExtra("meal_type", "snack");
                startActivity(intent);
            }
        });

        imgBtnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodSearchViewActivity.class);
                intent.putExtra("meal_type", "dinner");
                startActivity(intent);
            }
        });
    }


    private void initBottomNavigationBar() {
        binding.bottomNavView.setBackground(null);
        binding.bottomNavView.setOnNavigationItemSelectedListener(item -> {
            // Disable the last clicked item
            if (lastClickedItem != null) {
                lastClickedItem.setEnabled(true);
            }

            // Enable the clicked item
            item.setEnabled(false);


            // Store the clicked item as the last clicked item
            lastClickedItem = item;

            // Perform the desired action based on the clicked item
            if (item.getItemId() == R.id.diary) {
                openDiaryFragment();
            } else if (item.getItemId() == R.id.profile) {
                openProfileFragment();
            }

            return true; // Return true to indicate that the event has been consumed
        });
    }

    private void initViews() {
        logButton = findViewById(R.id.logButton);
        logLayout = findViewById(R.id.logLayout);

        // QUCIK LOG LAYOUT
        imgBtnActivity = findViewById(R.id.imgBtnActivity);
        imgBtnWeight = findViewById(R.id.imgBtnWeight);
        imgBtnWater = findViewById(R.id.imgBtnWater);
        imgBtnBreakfast = findViewById(R.id.imgBtnBreakfast);
        imgBtnLunch = findViewById(R.id.imgBtnLunch);
        imgBtnSnack = findViewById(R.id.imgBtnSnack);
        imgBtnDinner = findViewById(R.id.imgBtnDinner);




        logLayout.setVisibility(View.INVISIBLE);

        // Ensure logLayout is properly measured before starting any animation
        logLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                logLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // Now that the layout has been measured, set the initial state correctly
                isLogLayoutVisible = false;
            }
        });
    }


    private boolean isLogLayoutVisible = false;

    private void initFloatingActionButton() {
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogLayoutVisible) {
                    isLogLayoutVisible = true;
                    animateLogLayout(isLogLayoutVisible); // Animate the layout to show from bottom to top
                    imgBtnActivity.setClickable(true);
                    imgBtnWeight.setClickable(true);
                    imgBtnWater.setClickable(true);
                    imgBtnBreakfast.setClickable(true);
                    imgBtnLunch.setClickable(true);
                    imgBtnSnack.setClickable(true);
                    imgBtnDinner.setClickable(true);
                } else {
                    isLogLayoutVisible = false;
                    animateLogLayout(isLogLayoutVisible); // Animate the layout to hide from top to bottom
                    imgBtnActivity.setClickable(false);
                    imgBtnWeight.setClickable(false);
                    imgBtnWater.setClickable(false);
                    imgBtnBreakfast.setClickable(false);
                    imgBtnLunch.setClickable(false);
                    imgBtnSnack.setClickable(false);
                    imgBtnDinner.setClickable(false);
                }
            }
        });
    }

    private void animateLogLayout(final boolean isVisible) {
        TranslateAnimation animate;
        if (!isVisible) {
            animate = new TranslateAnimation(0, 0, 0, logLayout.getHeight());
        } else {
            animate = new TranslateAnimation(0, 0, logLayout.getHeight(), 0);
        }
        animate.setDuration(500);
        animate.setFillAfter(true);

        // Set animation listener to handle layout visibility
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isVisible) {
                    logLayout.setVisibility(View.INVISIBLE);
                } else {
                    logLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        logLayout.startAnimation(animate);
    }




    public void openDiaryFragment(){
        Fragment fragment = new DiaryFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    public void openProfileFragment(){
        Fragment fragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.frameLayout, fragment)
                .commit();
    }



    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onFoodLogged(String mealType) {

    }
    private ScrollListener scrollListener;
    public interface ScrollListener{
        void onScrollToWater();
    }
    private FragmentManager fragmentManager;
    /*private void switchToDiaryFragment() {
        fragmentManager = getSupportFragmentManager();


        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayout);
        if (!(currentFragment instanceof DiaryFragment)) {
            fragmentManager.beginTransaction()
                    .hide(currentFragment)
                    .show(diaryFragment)
                    .commit();
        }
    }*/

    private void handleDayChange() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Food>>() {}.getType();
        Type typeE = new TypeToken<List<Exercise>>() {}.getType();
        Type typeD = new TypeToken<List<DietEntry>>() {}.getType();
        Type typeW = new TypeToken<List<WeightEntry>>() {}.getType();
        String currentDate = "2024-06-22";//DateUtils.getCurrentDate();//"2024-06-23"; ////"2024-06-19";//
        String lastDate = sharedPreferences.getString("date", null);
        if(lastDate != null){
            if(currentDate.equals(lastDate)){

            }
            else{
                // store all logged meals for previous day (lastdate)
                List<Food> breakfast = gson.fromJson(sharedPreferences.getString("breakfast", null),type);
                List<Food> lunch = gson.fromJson(sharedPreferences.getString("lunch", null),type);
                List<Food> snack = gson.fromJson(sharedPreferences.getString("snack", null),type);
                List<Food> dinner = gson.fromJson(sharedPreferences.getString("dinner", null),type);
                FoodDatabaseHelper foodDatabaseHelper = new FoodDatabaseHelper(this);
                foodDatabaseHelper.saveFoodLog(lastDate, breakfast, lunch, snack, dinner);

                // handle diet enteries
                String formated_date = formatDate(lastDate);
                int calories_eaten = calculateDailyEatenCalories(breakfast, lunch, snack, dinner);

                List<DietEntry> dietEntries = gson.fromJson(sharedPreferences.getString("diet_entry", null), typeD);
                if(dietEntries == null){
                    dietEntries = new ArrayList<>();
                }
                dietEntries.add(new DietEntry(formated_date, calories_eaten));
                Log.d("MainActivity", "Diet enteries: "+gson.toJson(dietEntries));
                editor.putString("diet_entry", gson.toJson(dietEntries));
                editor.commit();
                //

                /*HANDLE WEIGHT ENTERIES*/
                int weight_entry = sharedPreferences.getInt("weight_pref_key", -1);
                List<WeightEntry> weightEntries = gson.fromJson(sharedPreferences.getString("weight_entry", null), typeW);
                if(weightEntries == null){
                    weightEntries = new ArrayList<>();
                }
                weightEntries.add(new WeightEntry(formated_date, weight_entry));
                editor.putString("weight_entry", gson.toJson(weightEntries));
                editor.commit();


                // store all activities
                List<Exercise> exercises = gson.fromJson(sharedPreferences.getString("exercises", null), typeE);
                ExerciseDatabaseHelper exerciseDatabaseHelper = new ExerciseDatabaseHelper(this);
                exerciseDatabaseHelper.saveExerciseLog(lastDate, exercises);
                // store waterintake and weight for last date
                HealthDatabaseHelper healthDatabaseHelper = new HealthDatabaseHelper(this);
                double water_drinked = sharedPreferences.getFloat("water_drunk", -1);
                int lastWeight = sharedPreferences.getInt("weight_pref_key", -1);
                if(water_drinked != -1 && lastWeight != -1){
                    healthDatabaseHelper.saveHealthLog(lastDate, water_drinked, lastWeight);
                }
                //TODO: SET IS_SIGNEDUP TO TRUE
                //TODO: SET ALL DEFAULT VALUES

                //get all default values
                UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(this);
                String username = sharedPreferences.getString("username_pref_key", null);
                int dailyCalorieIntake = userDatabaseHandler.getDailyCalorieIntake(username);
                int proteinGrams = sharedPreferences.getInt("protein_grams", -1);
                int carbGrams = sharedPreferences.getInt("carb_grams", -1);
                int fatGrams = sharedPreferences.getInt("fat_grams", -1);

                int breakfastCalories = sharedPreferences.getInt("breakfastCalories", -1);
                int lunchCalories = sharedPreferences.getInt("lunchCalories", -1);
                int snackCalories = sharedPreferences.getInt("snackCalories", -1);
                int dinnerCalories = sharedPreferences.getInt("dinnerCalories", -1);

                double water_goal_intake = sharedPreferences.getFloat("water_goal_intake", -1);
                double water_drunk = 0.0;

                String user_goal = sharedPreferences.getString("user_goal", null);
                int weight = sharedPreferences.getInt("weight_pref_key", -1);
                int goal_weight = sharedPreferences.getInt("goal_weight_pref_key", -1);

                List<DietEntry> diet_entries2 = gson.fromJson(sharedPreferences.getString("diet_entry", null), typeD);
                List<WeightEntry> weightEntries2= gson.fromJson(sharedPreferences.getString("weight_entry", null), typeW);

                lastDate = currentDate;

                editor.clear();

                editor.putString("diet_entry", gson.toJson(diet_entries2));
                editor.putString("weight_entry", gson.toJson(weightEntries2));

                // put all default values in shared preference
                editor.putString("date", currentDate);
                editor.putBoolean("isSignedUp", true);

                editor.putString("key_carbs_percentage", "0.4");
                editor.putString("key_proteins_percentage", "0.4");
                editor.putString("key_fats_percentage", "0.2");

                editor.putInt("key_carb_grams_eaten", 0);
                editor.putInt("key_protein_grams_eaten", 0);
                editor.putInt("key_fat_grams_eaten", 0);

                editor.putInt("daily_calorie_intake", dailyCalorieIntake);
                editor.putInt("proteinGrams", proteinGrams);
                editor.putInt("carbGrams", carbGrams);
                editor.putInt("fatGrams", fatGrams);

                editor.putInt("breakfastCalories", breakfastCalories);
                editor.putInt("lunchCalories", lunchCalories);
                editor.putInt("snackCalories", snackCalories);
                editor.putInt("dinnerCalories", dinnerCalories);

                editor.putFloat("water_goal_intake", (float) water_goal_intake);
                editor.putFloat("water_drunk", (float) water_drunk);

                editor.putString("user_goal", user_goal);
                editor.putString("username_pref_key", username);
                editor.putInt("weight_pref_key", weight);
                editor.putInt("goal_weight_pref_key", goal_weight);
                editor.apply();


            }
        }

    }

    private int calculateDailyEatenCalories(List<Food> breakfast, List<Food> lunch, List<Food> snack, List<Food> dinner ) {
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