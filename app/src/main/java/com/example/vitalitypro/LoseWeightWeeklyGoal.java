package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoseWeightWeeklyGoal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoseWeightWeeklyGoal extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoseWeightWeeklyGoal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoseWeightWeeklyGoal.
     */
    // TODO: Rename and change types and number of parameters
    public static LoseWeightWeeklyGoal newInstance(String param1, String param2) {
        LoseWeightWeeklyGoal fragment = new LoseWeightWeeklyGoal();
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


    private ConstraintLayout constraintLayout;
    private TextView txtTitle;
    private MaterialButtonToggleGroup toggleGroupWeeklyGoal;
    private MaterialButton btnLose02, btnLose05, btnLose08, btnLose1, btnNext;

    private ProgressBar progressBar;
    private int currentProgress = 90;
    private static final String WEEKLY_LOSE_GOAL = "users_weekly_lose_goal";
    private int buttonsChecked = 0;

    private static final String TAG = "LoseWeightWeeklyGoalFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lose_weight_weekly_goal, container, false);
        initViews(rootView);
        initProgressBar();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);

        // Get SharedPreferences editor to make changes
        SharedPreferences.Editor editor = sharedPreferences.edit();

        /*// Store a string value
        editor.putString(PREF_KEY, "example_value");

        // Commit the changes
        editor.apply();*/


        toggleGroupWeeklyGoal.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int checkedId, boolean isChecked) {
                String selectedButtonId = getResources().getResourceEntryName(checkedId);
                if(isChecked) {
                    switch (selectedButtonId) {
                        case "btnLose02":
                            editor.putString(WEEKLY_LOSE_GOAL, "Lose 0,2kg");
                            buttonsChecked = 1;
                            break;
                        case "btnLose05":
                            editor.putString(WEEKLY_LOSE_GOAL, "Lose 0,5kg");
                            buttonsChecked = 1;
                            break;
                        case "btnLose08":
                            editor.putString(WEEKLY_LOSE_GOAL, "Lose 0,8kg");
                            buttonsChecked = 1;
                            break;
                        case "btnLose1":
                            editor.putString(WEEKLY_LOSE_GOAL, "Lose 1kg");
                            buttonsChecked = 1;
                            break;
                    }
                }
                else{
                    buttonsChecked = 0;
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonsChecked == 0){
                    Toast.makeText(requireActivity(), getString(R.string.plsSelectOneOption), Toast.LENGTH_SHORT).show();
                }
                else{
                    editor.apply();
                    Log.d(TAG, "Selected: "+sharedPreferences.getString(WEEKLY_LOSE_GOAL, ""));
                    // open new fragment
                    openRegistrationFragment();
                }
            }
        });





        return rootView;
    }

    private void initViews(View rootView) {
        constraintLayout = rootView.findViewById(R.id.constraintLayout);

        txtTitle = rootView.findViewById(R.id.txtTitle);

        toggleGroupWeeklyGoal = rootView.findViewById(R.id.toggleGroupWeeklyGoal);
        btnLose02 = rootView.findViewById(R.id.btnLose02);
        btnLose05 = rootView.findViewById(R.id.btnLose05);
        btnLose08 = rootView.findViewById(R.id.btnLose08);
        btnLose1 = rootView.findViewById(R.id.btnLose1);
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

    private void openRegistrationFragment() {
        // Create an instance of the FifthFragment
        RegistrationFragment registrationFragment = new RegistrationFragment();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);

        // Replace the current fragment with the FifthFragment
        fragmentTransaction.replace(R.id.frameLayout, registrationFragment);

        // Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();

    }

}