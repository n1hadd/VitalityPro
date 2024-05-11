package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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


    private ScrollView scrollView;
    private CardView cardView;
    private RelativeLayout cardRelativeLayout;
    private TextView txtEaten, txtRemaining, txtCaloriesCount, txtKcal, txtCaloriesRemainingCount;
    private ProgressBar progressBar, dailyGoalProgressBar;
    private TextView dailyGoal, dailyGoalCalories;
    private int currentProgress = 10;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diary, container, false);
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        initViews(rootView);
        updateProgressBar(currentProgress);
        UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(getContext());
        int dailyCalorieIntake = userDatabaseHandler.getDailyCalorieIntake(sharedPreferences.getString("username_pref_key",""));
        txtCaloriesRemainingCount.setText(String.valueOf(dailyCalorieIntake));
        dailyGoalCalories.setText(String.valueOf(dailyCalorieIntake));
        return rootView;
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

    }
}