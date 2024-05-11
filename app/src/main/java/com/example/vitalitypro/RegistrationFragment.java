package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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

    private static final String TAG = "RegistrationFragment";

    private static final String USERNAME_PREF_KEY = "username_pref_key";
    private static final String PASSWORD_PREF_KEY = "password_pref_key";


    private ConstraintLayout constraintLayout;
    private TextView txtTitle, txtError;
    private TextInputLayout usernameInputLayout, passwordInputLayout;
    private TextInputEditText usernameEditText, passwordEditText;
    private MaterialButton btnNext;

    private ProgressBar progressBar;
    private int currentProgress = 95;

    private SharedPreferences sharedPreferences;

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-";

    private int weight;
    private int height;
    private int age;
    private String gender;
    private String activityLevel;
    private double weightChangeGoalLose;
    private double weightChangeGoalGain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_registration, container, false);
        initViews(rootView);
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        initToolBar(rootView);
        initGetUserName();
        initGetPassword();
        getUserData();
        initOnClickBtnNext();

        return rootView;
    }

    private void addUserToDatabase(User user) {
        UserDatabaseHandler dbHandler = new UserDatabaseHandler(requireContext());
        long result = dbHandler.addUser(user);

        if (result != -1) {
            // User added successfully
            Toast.makeText(requireContext(), "User added successfully!", Toast.LENGTH_SHORT).show();
        } else {
            // Failed to add user
            Toast.makeText(requireContext(), "Failed to add user!", Toast.LENGTH_SHORT).show();
        }
    }




    private void getUserData() {
        weight = sharedPreferences.getInt("weight_pref_key", -1);
        height = sharedPreferences.getInt("height_pref_key", -1);
        age = sharedPreferences.getInt("age_pref_key",-1);
        gender = sharedPreferences.getString("gender_pref_key", "");
        activityLevel = sharedPreferences.getString("user_activity_level", "");
    }

    private void initOnClickBtnNext() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if username is empty
                if (usernameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your username.", Toast.LENGTH_SHORT).show();
                    return; // Exit method early if username is empty
                } else if (usernameInputLayout.getError() != null) {
                    Toast.makeText(getContext(), "Your username is not valid.", Toast.LENGTH_SHORT).show();
                    return; // Exit method early if username is invalid
                }

                // Check if password is empty
                if (passwordEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your password.", Toast.LENGTH_SHORT).show();
                    return; // Exit method early if password is empty
                } else if (passwordInputLayout.getError() != null) {
                    Toast.makeText(getContext(), "Your password is not valid.", Toast.LENGTH_SHORT).show();
                    return; // Exit method early if password is invalid
                }

                // Save credentials
                saveCredentials(usernameEditText.getText().toString(), passwordEditText.getText().toString());

                // Log user information
                Log.d(TAG, "User goal: " + sharedPreferences.getString("user_goal", ""));
                Log.d(TAG, "User Activity level: " + sharedPreferences.getString("user_activity_level", ""));
                Log.d(TAG, "Gender: " + sharedPreferences.getString("gender_pref_key", "") + " Age: " + sharedPreferences.getInt("age_pref_key", -1) + " Country: " + sharedPreferences.getString("country_pref_key", ""));
                Log.d(TAG, "Height: " + sharedPreferences.getInt("height_pref_key", -1) + "; Weight: " +
                        sharedPreferences.getInt("weight_pref_key", -1) + "; Goal Weight: " +
                        sharedPreferences.getInt("goal_weight_pref_key", -1));
                Log.d(TAG, "Selected: " + sharedPreferences.getString("users_weekly_lose_goal", ""));
                Log.d(TAG, "Selected: " + sharedPreferences.getString("users_weekly_gain_goal", ""));
                Log.d(TAG, "Username: " + sharedPreferences.getString("username_pref_key", "") + ", Password: " + sharedPreferences.getString("password_pref_key", ""));

                // Calculate daily calorie intake based on user's goal
                int dailyCalorieIntake;
                String goal = sharedPreferences.getString("user_goal", "");
                switch (goal.toLowerCase()) {
                    case "lose weight":
                        weightChangeGoalLose = Double.parseDouble(sharedPreferences.getString("users_weekly_lose_goal",""));
                        dailyCalorieIntake = calculateDailyCalorieIntake(weight, height, age, gender, activityLevel, weightChangeGoalLose);
                        break;
                    case "gain weight":
                        weightChangeGoalGain = Double.parseDouble(sharedPreferences.getString("users_weekly_gain_goal",""));
                        dailyCalorieIntake = calculateDailyCalorieIntake(weight, height, age, gender, activityLevel, weightChangeGoalGain);
                        break;
                    default:
                        dailyCalorieIntake = calculateDailyCalorieIntakeForMaintenance(weight, height, age, gender, activityLevel);
                        break;
                }

                // Log daily calorie intake
                Log.d(TAG, "Daily calorie intake: " + dailyCalorieIntake + " kcal");

                // Create user object based on the calculated calorie intake
                User user;
                if (goal.equalsIgnoreCase("lose weight") || goal.equalsIgnoreCase("gain weight")) {
                    user = new User(
                            sharedPreferences.getString(USERNAME_PREF_KEY, ""),
                            sharedPreferences.getString(PASSWORD_PREF_KEY, ""),
                            goal,
                            sharedPreferences.getString("user_activity_level", ""),
                            sharedPreferences.getInt("weight_pref_key", -1),
                            sharedPreferences.getInt("goal_weight_pref_key", -1),
                            sharedPreferences.getInt("height_pref_key", -1),
                            sharedPreferences.getInt("age_pref_key", -1),
                            sharedPreferences.getString("gender_pref_key", ""),
                            goal.equalsIgnoreCase("lose weight") ? weightChangeGoalLose : weightChangeGoalGain,
                            0,
                            dailyCalorieIntake
                    );
                } else {
                    user = new User(
                            sharedPreferences.getString(USERNAME_PREF_KEY, ""),
                            sharedPreferences.getString(PASSWORD_PREF_KEY, ""),
                            goal,
                            sharedPreferences.getString("user_activity_level", ""),
                            sharedPreferences.getInt("weight_pref_key", -1),
                            sharedPreferences.getInt("height_pref_key", -1),
                            sharedPreferences.getInt("age_pref_key", -1),
                            sharedPreferences.getString("gender_pref_key", ""),
                            0,
                            dailyCalorieIntake
                    );
                }

                // Add user to the database
                addUserToDatabase(user);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isSignedUp", true);
                editor.apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });
    }






    private int calculateDailyCalorieIntake(int weight, int height, int age, String gender, String activityLevel, double weightChangeGoal) {
        // Calculate Basal Metabolic Rate (BMR)
        double bmr;
        if (gender.equalsIgnoreCase("male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        // Adjust BMR based on activity level
        double activityMultiplier;
        switch (activityLevel.toLowerCase()) {
            case "not very active":
                activityMultiplier = 1.2;
                break;
            case "lightly active":
                activityMultiplier = 1.375;
                break;
            case "active":
                activityMultiplier = 1.55;
                break;
            case "very active":
                activityMultiplier = 1.725;
                break;
            default:
                activityMultiplier = 1.2; // Default to Not Very Active if activity level is not recognized
                break;
        }

        // Calculate daily calorie intake
        int dailyCalorieIntake = (int) (bmr * activityMultiplier);

        // Adjust daily calorie intake based on user's weight change goal
        // For example, to lose weight, subtract calories, to gain weight, add calories
        int adjustedCalorieIntake = (int) (dailyCalorieIntake + (weightChangeGoal * 1000)); // 1 kg = 1000 calories

        return adjustedCalorieIntake;
    }

    public static int calculateDailyCalorieIntakeForMaintenance(int weight, int height, int age, String gender, String activityLevel) {
        // Calculate Basal Metabolic Rate (BMR)
        double bmr;
        if (gender.equalsIgnoreCase("male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        // Adjust BMR based on activity level
        double activityMultiplier;
        switch (activityLevel.toLowerCase()) {
            case "not very active":
                activityMultiplier = 1.2;
                break;
            case "lightly active":
                activityMultiplier = 1.375;
                break;
            case "active":
                activityMultiplier = 1.55;
                break;
            case "very active":
                activityMultiplier = 1.725;
                break;
            default:
                activityMultiplier = 1.2; // Default to Not Very Active if activity level is not recognized
                break;
        }

        // Calculate daily calorie intake for weight maintenance
        int dailyCalorieIntake = (int) (bmr * activityMultiplier);

        return dailyCalorieIntake;
    }




    private void initGetUserName() {
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username = s.toString().trim();
                if (TextUtils.isEmpty(username)) {
                    // If it's empty, show a generic error message
                    usernameInputLayout.setError(getString(R.string.login_goals_edit_text_valid_value));
                } else {
                    if (username.length() < MIN_LENGTH || username.length() > MAX_LENGTH) {
                        usernameInputLayout.setError("Username must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters long.");
                    } else {
                        boolean containsInvalidChars = false;
                        for (char ch : username.toCharArray()) {
                            if (ALLOWED_CHARACTERS.indexOf(ch) == -1) {
                                containsInvalidChars = true;
                                break;
                            }
                        }
                        if (containsInvalidChars) {
                            usernameInputLayout.setError("Username contains invalid characters.");
                        } else {
                            UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(getContext());
                            boolean usernameExists = userDatabaseHandler.isUsernameExists(username);
                            if(usernameExists){
                                usernameInputLayout.setError("Username already exists.");
                            }
                            else{
                                usernameInputLayout.setError(null);
                            }

                        }
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private static final int PW_MIN_LENGTH = 8;
    private static final int MIN_UPPERCASE = 1;
    private static final int MIN_LOWERCASE = 1;
    private static final int MIN_DIGITS = 1;
    private static final int MIN_SPECIAL_CHARACTERS = 1;

    public static boolean isStrongPassword(String password) {
        // Check if password meets minimum length requirement
        if (password.length() < PW_MIN_LENGTH) {
            return false;
        }

        // Check if password contains at least one uppercase letter
        if (password.chars().filter(Character::isUpperCase).count() < MIN_UPPERCASE) {
            return false;
        }

        // Check if password contains at least one lowercase letter
        if (password.chars().filter(Character::isLowerCase).count() < MIN_LOWERCASE) {
            return false;
        }

        // Check if password contains at least one digit
        if (password.chars().filter(Character::isDigit).count() < MIN_DIGITS) {
            return false;
        }

        // Check if password contains at least one special character
        if (password.chars().filter(c -> !Character.isLetterOrDigit(c)).count() < MIN_SPECIAL_CHARACTERS) {
            return false;
        }

        // If all criteria are met, the password is considered strong
        return true;
    }
    private boolean passwordVisible = false;

    private void initGetPassword() {
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString().trim();
                if (TextUtils.isEmpty(password)) {
                    // If it's empty, show a generic error message
                    passwordInputLayout.setError(getString(R.string.login_goals_edit_text_valid_value));
                }
                else{
                    if(!isStrongPassword(password)){
                        txtError.setVisibility(View.VISIBLE);
                        passwordInputLayout.setError(getString(R.string.weakPassword));
                    }
                    else{
                        txtError.setVisibility(View.GONE);
                        passwordInputLayout.setError(null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                passwordVisible = !passwordVisible;

                // Set the password visibility in the TextInputEditText
                passwordEditText.setTransformationMethod(passwordVisible ? null : PasswordTransformationMethod.getInstance());

                // Change the end icon drawable based on the password visibility state
                int endIconDrawable = passwordVisible ? R.drawable.ic_password_hide : R.drawable.ic_password_visible;
                passwordInputLayout.setEndIconDrawable(endIconDrawable);

                // Update the content description of the end icon
                String contentDescription = passwordVisible ? "Hide password" : "Show password";
                passwordInputLayout.setEndIconContentDescription(contentDescription);
            }
        });

    }

    private void saveCredentials(String username, String password) {
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_PREF_KEY, username);
        editor.putString(PASSWORD_PREF_KEY, password);
        editor.apply();
    }


    private void initViews(View rootView) {
        constraintLayout = rootView.findViewById(R.id.constraintLayout);
        txtTitle = rootView.findViewById(R.id.txtTitle);
        usernameInputLayout = rootView.findViewById(R.id.usernameInputLayout);
        passwordInputLayout = rootView.findViewById(R.id.passwordInputLayout);
        usernameEditText = rootView.findViewById(R.id.usernameEditText);
        passwordEditText = rootView.findViewById(R.id.passwordEditText);
        btnNext = rootView.findViewById(R.id.btnNext);
        txtError = rootView.findViewById(R.id.txtError);
    }

    /*private void initProgressBar() {
        LoginActivity loginActivity = (LoginActivity) getActivity();
        if(loginActivity != null){
            progressBar = loginActivity.getProgressBar();
            if(progressBar!=null){
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(currentProgress);
            }
        }
    }*/

    private void initToolBar(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        // Find the nested layout within the Toolbar
        View nestedLayout = toolbar.findViewById(R.id.nestedLayout);

        // Find the txtTitle TextView within the nested layout
        TextView txtToolbarTitle = nestedLayout.findViewById(R.id.txtToolbarTitle);

        // Set the new text for txtTitle
        txtToolbarTitle.setText("Sign up");

        ImageView imgBack = nestedLayout.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPreviusFragment();
            }
        });
    }

    private void openPreviusFragment() {
        if(sharedPreferences.getString("user_goal", "").equals("Lose weight")){
            // Create an instance of the SecondFragment
            LoseWeightWeeklyGoal loseWeightWeeklyGoal = new LoseWeightWeeklyGoal();

            // Get the FragmentManager and start a FragmentTransaction
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
            // Replace the current fragment with the SecondFragment
            fragmentTransaction.replace(R.id.frameLayout, loseWeightWeeklyGoal);

            // Add the transaction to the back stack (optional)
            fragmentTransaction.addToBackStack(null);

            // Commit the transaction
            fragmentTransaction.commit();
        }
        else if(sharedPreferences.getString("user_goal", "").equals("Gain weight")){
            GainWeightWeeklyGoal gainWeightWeeklyGoal = new GainWeightWeeklyGoal();

            // Get the FragmentManager and start a FragmentTransaction
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
            // Replace the current fragment with the SecondFragment
            fragmentTransaction.replace(R.id.frameLayout, gainWeightWeeklyGoal);

            // Add the transaction to the back stack (optional)
            fragmentTransaction.addToBackStack(null);

            // Commit the transaction
            fragmentTransaction.commit();
        }
        else{
            FifthFragment fifthFragment = new FifthFragment();

            // Get the FragmentManager and start a FragmentTransaction
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
            // Replace the current fragment with the SecondFragment
            fragmentTransaction.replace(R.id.frameLayout, fifthFragment);

            // Add the transaction to the back stack (optional)
            fragmentTransaction.addToBackStack(null);

            // Commit the transaction
            fragmentTransaction.commit();
        }
    }


}