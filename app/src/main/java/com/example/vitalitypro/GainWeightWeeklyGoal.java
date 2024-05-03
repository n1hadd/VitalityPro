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
 * Use the {@link GainWeightWeeklyGoal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GainWeightWeeklyGoal extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GainWeightWeeklyGoal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GainWeightWeeklyGoal.
     */
    // TODO: Rename and change types and number of parameters
    public static GainWeightWeeklyGoal newInstance(String param1, String param2) {
        GainWeightWeeklyGoal fragment = new GainWeightWeeklyGoal();
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
    private MaterialButton btnGain02, btnGain05, btnNext;
    private ProgressBar progressBar;
    private int currentProgress = 90;
    private int buttonsChecked = 0;

    private static final String WEEKLY_GAIN_GOAL = "users_weekly_gain_goal";
    private static final String TAG = "GainWeightWeeklyGoalFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gain_weight_weekly_goal, container, false);

        initViews(rootView);
        initProgressBar();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);

        // Get SharedPreferences editor to make changes
        SharedPreferences.Editor editor = sharedPreferences.edit();


        toggleGroupWeeklyGoal.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int checkedId, boolean isChecked) {
                String selectedButtonId = getResources().getResourceEntryName(checkedId);
                if(isChecked) {
                    switch (selectedButtonId) {
                        case "btnGain02":
                            editor.putString(WEEKLY_GAIN_GOAL, "Gain 0,2kg");
                            buttonsChecked = 1;
                            break;
                        case "btnGain05":
                            editor.putString(WEEKLY_GAIN_GOAL, "Gain 0,5kg");
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
                    Log.d(TAG, "Selected: "+sharedPreferences.getString(WEEKLY_GAIN_GOAL, ""));
                    // open new fragment
                    openRegistrationFragment();
                }
            }
        });

        return rootView;
    }

    private void initViews(View view) {
        constraintLayout = view.findViewById(R.id.constraintLayout);

        txtTitle = view.findViewById(R.id.txtTitle);

        toggleGroupWeeklyGoal = view.findViewById(R.id.toggleGroupWeeklyGoal);
        btnGain02 = view.findViewById(R.id.btnGain02);
        btnGain05 = view.findViewById(R.id.btnGain05);
        btnNext = view.findViewById(R.id.btnNext);
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