package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
                openProfileFragment();

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
}