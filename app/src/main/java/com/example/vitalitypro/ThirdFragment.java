package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
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

    public static final String TAG = "Third Fragment";
    private ConstraintLayout parent;
    private ProgressBar progressBar;
    private int currentProgress = 40;
    private RecyclerView recyclerView;
    private ActivityLevelAdapter adapter;

    private MaterialButton btnNext;
    private String selectedValue;
    private static final String ACTIVITY_LEVEL = "user_activity_level";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_third, container, false);
        initProgressBar();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);

        // Get SharedPreferences editor to make changes
        SharedPreferences.Editor editor = sharedPreferences.edit();


        recyclerView = rootView.findViewById(R.id.recyclerView);


        List<ActivityLevel> activityLevels = new ArrayList<>();
        String[] levels = getResources().getStringArray(R.array.activity_levels);
        String[] descriptions = getResources().getStringArray(R.array.activity_descriptions);

        for (int i = 0; i < levels.length; i++) {
            activityLevels.add(new ActivityLevel(levels[i], descriptions[i]));
        }

        adapter = new ActivityLevelAdapter(getContext(), activityLevels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setActivityLevels(activityLevels);

        btnNext = rootView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItemPosition = adapter.getSelectedItemPosition();
                if (selectedItemPosition == RecyclerView.NO_POSITION) {
                    // No item selected, show a toast message
                    Toast.makeText(getContext(), getString(R.string.plsSelectOneOption), Toast.LENGTH_SHORT).show();
                } else {
                    // Item selected, store the selected value
                    String selectedActivityLevel = activityLevels.get(selectedItemPosition).getTitle();
                    editor.putString(ACTIVITY_LEVEL, selectedActivityLevel);
                    editor.apply();


                    // Store the selected value in a static variable in ThirdFragment
                    Log.d(TAG,"Selected: "+sharedPreferences.getString(ACTIVITY_LEVEL, ""));

                    // Perform any other actions
                    openFourthFragment();
                }
            }
        });


        return rootView;
    }

    private void openFourthFragment() {
        // Create an instance of the FourthFragment
        FourthFragment fourthFragment = new FourthFragment();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        // Replace the current fragment with the FourthFragment
        fragmentTransaction.replace(R.id.frameLayout, fourthFragment);

        // Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();

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
}