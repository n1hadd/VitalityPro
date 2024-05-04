package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FifthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FifthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FifthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FifthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FifthFragment newInstance(String param1, String param2) {
        FifthFragment fragment = new FifthFragment();
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

    private final String TAG = "Fifth Fragment";
    private ConstraintLayout constraintLayout;
    private TextView txtTitle, txtHeight, txtWeight, txtGoalWeight;
    private TextInputLayout textInputLayoutHeight, textInputLayoutWeight, textInputLayoutGoalWeight;
    private TextInputEditText textInputEditTextHeight, textInputEditTextWeight, textInputEditTextGoalWeight;
    private MaterialButton btnNext;

    private ProgressBar progressBar;
    private int currentProgress = 80;
    private String userGoal;

    private static final String HEIGHT_PREF_KEY = "height_pref_key";
    private static final String WEIGHT_PREF_KEY = "weight_pref_key";
    private static final String GOAL_WEIGHT_PREF_KEY = "goal_weight_pref_key";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fifth, container, false);
        initViews(rootView);
        initProgressBar();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        userGoal = sharedPreferences.getString("user_goal","");
        if(!userGoal.equals("Maintain weight")){
            txtGoalWeight.setVisibility(View.VISIBLE);
            textInputLayoutGoalWeight.setVisibility(View.VISIBLE);
            textInputEditTextGoalWeight.setVisibility(View.VISIBLE);
        }
        else{
            txtGoalWeight.setVisibility(View.GONE);
            textInputLayoutGoalWeight.setVisibility(View.GONE);
            textInputEditTextGoalWeight.setVisibility(View.GONE);
        }
        initEditTextHeight();
        initEditTextWeight();
        initEditTextGoalWeight();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = textInputEditTextHeight.getText().toString();
                if(height.isEmpty()){
                    Toast.makeText(getContext(), "Please enter your height", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    if(textInputLayoutHeight.getError() != null){
                        Toast.makeText(getContext(), "Please enter a valid height.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editor.putInt(HEIGHT_PREF_KEY, Integer.parseInt(height));
                }
                String weight = textInputEditTextWeight.getText().toString();
                if(weight.isEmpty()){
                    Toast.makeText(getContext(), "Please enter your weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    if(textInputLayoutWeight.getError() != null){
                        Toast.makeText(getContext(), "Please enter a valid weight.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editor.putInt(WEIGHT_PREF_KEY, Integer.parseInt(weight));
                }

                if(textInputEditTextGoalWeight.getVisibility() == View.VISIBLE){
                    String goalWeight = textInputEditTextGoalWeight.getText().toString();
                    if(goalWeight.isEmpty()){
                        Toast.makeText(getContext(), "Please enter your goal weight", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        if(textInputLayoutGoalWeight.getError() !=null){
                            Toast.makeText(getContext(), "Please enter a valid goal weight.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt(GOAL_WEIGHT_PREF_KEY, Integer.parseInt(goalWeight));
                    }
                }
                editor.apply();
                Log.d(TAG, "Height: " + sharedPreferences.getInt(HEIGHT_PREF_KEY, -1) + "; Weight: " +
                        sharedPreferences.getInt(WEIGHT_PREF_KEY, -1) + "; Goal Weight: " +
                        sharedPreferences.getInt(GOAL_WEIGHT_PREF_KEY, -1));

                if(userGoal.equals("Lose weight")){
                    openLoseWeightWeeklyGoal();
                }
                else if(userGoal.equals("Gain weight")){
                    openGainWeightWeeklyGoal();
                }
                else{
                    openRegistrationFragment();
                }

            }
        });



        return rootView;
    }

    private void initEditTextGoalWeight() {
        textInputEditTextGoalWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String goalWeightText = s.toString().trim();


                if(userGoal.equals("Lose weight")){
                    if(TextUtils.isEmpty(goalWeightText)){
                        textInputLayoutGoalWeight.setError(getString(R.string.login_goals_edit_text_valid_value));
                    }
                    else{
                        int goalWeight = Integer.parseInt(goalWeightText);
                        if(goalWeight>=weight){
                            textInputLayoutGoalWeight.setError(getString(R.string.goalLoseWeightMessage));
                        }
                        else{
                            if((weight-goalWeight) >= 12 ){
                                textInputLayoutGoalWeight.setHelperText("Based on your other answers, we recommend you a goal of "+(weight-12)+" kg or higher.");
                            }
                            else{
                                textInputLayoutGoalWeight.setHelperText(null);
                            }
                            textInputLayoutGoalWeight.setError(null);
                        }
                    }
                }
                if(userGoal.equals("Gain weight")){
                    if(TextUtils.isEmpty(goalWeightText)){
                        textInputLayoutGoalWeight.setError(getString(R.string.login_goals_edit_text_valid_value));
                    }
                    else{
                        int goalWeight = Integer.parseInt(goalWeightText);
                        if(goalWeight<=weight){
                            textInputLayoutGoalWeight.setError(getString(R.string.goalGainWeightMessage));
                        }
                        else{
                            textInputLayoutGoalWeight.setError(null);
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initEditTextHeight() {
        textInputEditTextHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String heightText = s.toString().trim();
                if (TextUtils.isEmpty(heightText)) {
                    // If it's empty, show a generic error message
                    textInputLayoutHeight.setError(getString(R.string.login_goals_edit_text_valid_value));
                }
                else{
                    int height = Integer.parseInt(heightText);
                    if(height < 100 || height > 300){
                        textInputLayoutHeight.setError(getString(R.string.login_goals_edit_text_valid_value));
                    }
                    else{
                        textInputLayoutHeight.setError(null);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private int weight;
    private void initEditTextWeight() {
        textInputEditTextWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String weightText = s.toString().trim();
                if (TextUtils.isEmpty(weightText)) {
                    // If it's empty, show a generic error message
                    textInputLayoutWeight.setError(getString(R.string.login_goals_edit_text_valid_value));
                }
                else{
                    weight = Integer.parseInt(weightText);
                    if(weight < 40 || weight > 300){
                        textInputLayoutWeight.setError(getString(R.string.login_goals_edit_text_valid_value));
                    }
                    else{
                        textInputLayoutWeight.setError(null);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initViews(View rootView) {
        constraintLayout = rootView.findViewById(R.id.constraintLayout);

        txtTitle = rootView.findViewById(R.id.txtTitle);
        txtHeight = rootView.findViewById(R.id.txtHeight);
        txtWeight = rootView.findViewById(R.id.txtWeight);
        txtGoalWeight = rootView.findViewById(R.id.txtGoalWeight);

        textInputLayoutHeight = rootView.findViewById(R.id.textInputLayoutHeight);
        textInputLayoutWeight = rootView.findViewById(R.id.textInputLayoutWeight);
        textInputLayoutGoalWeight = rootView.findViewById(R.id.textInputLayoutGoalWeight);

        textInputEditTextHeight = rootView.findViewById(R.id.textInputEditTextHeight);
        textInputEditTextWeight = rootView.findViewById(R.id.textInputEditTextWeight);
        textInputEditTextGoalWeight = rootView.findViewById(R.id.textInputEditTextGoalWeight);
        btnNext = rootView.findViewById(R.id.btnNext);
    }

    private void initProgressBar() {
        LoginActivity loginActivity = (LoginActivity) getActivity();
        if(loginActivity != null){
            progressBar = loginActivity.getProgressBar();
            if(progressBar!=null){
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(currentProgress);
            }
        }
    }

    private void openLoseWeightWeeklyGoal() {
        // Create an instance of the SixthFragment
        LoseWeightWeeklyGoal loseWeightWeeklyGoal = new LoseWeightWeeklyGoal();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Specify the enter and exit animations for the fragment transaction
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

        // Replace the current fragment with the SixthFragment
        fragmentTransaction.replace(R.id.frameLayout, loseWeightWeeklyGoal);

        // Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }

    private void openGainWeightWeeklyGoal() {
        // Create an instance of the SixthFragment
        GainWeightWeeklyGoal gainWeightWeeklyGoal = new GainWeightWeeklyGoal();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Specify the enter and exit animations for the fragment transaction
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

        // Replace the current fragment with the SixthFragment
        fragmentTransaction.replace(R.id.frameLayout, gainWeightWeeklyGoal);

        // Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }


    private void openRegistrationFragment() {
        // Create an instance of the SixthFragment
        RegistrationFragment registrationFragment = new RegistrationFragment();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Specify the enter and exit animations for the fragment transaction
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

        // Replace the current fragment with the SixthFragment
        fragmentTransaction.replace(R.id.frameLayout, registrationFragment);

        // Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }

}