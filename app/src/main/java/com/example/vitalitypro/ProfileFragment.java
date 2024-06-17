package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    private TextView txtUsername, txtGoal, currentWeight, goalWeight;
    private ImageView imgLogWeight;
    private RelativeLayout rlWeightProgress, rlDietOverview, rlActivityOverview, rlHydrationOverview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(rootView);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // set username
        String username = sharedPreferences.getString("username_pref_key", null);
        if(username!=null){
            if (username.length() > 0 && !Character.isUpperCase(username.charAt(0))) {
                username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
                txtUsername.setText(username);
            }
        }

        // set user's goal
        String userGoal = sharedPreferences.getString("user_goal", null);
        if(userGoal != null){
            txtGoal.setText(userGoal);
        }

        // set current weight
        int currWeight = sharedPreferences.getInt("weight_pref_key", -1);
        if(currWeight != -1){
            currentWeight.setText(currWeight+" kg");
        }

        int gWeight = sharedPreferences.getInt("goal_weight_pref_key", -1);
        if(gWeight != -1){
            goalWeight.setText(gWeight+" kg");
        }

        //open log weight activity
        imgLogWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LogWeightActivity.class);
                int weight = sharedPreferences.getInt("weight_pref_key", -1);
                intent.putExtra("weight", weight);
                startActivity(intent);
            }
        });

        //open weight progress activity
        rlWeightProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeightProgressActivity.class);
                startActivity(intent);
            }
        });

        // open DietOverview activity
        rlDietOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DietOverviewActivity.class);
                startActivity(intent);
            }
        });

        rlActivityOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityOverview.class);
                startActivity(intent);
            }
        });

        rlHydrationOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HydrationOverview.class);
                startActivity(intent);
            }
        });

        return rootView;

    }

    private void initViews(View rootView) {
        txtUsername = rootView.findViewById(R.id.txtUsername);
        txtGoal = rootView.findViewById(R.id.txtGoal);
        currentWeight = rootView.findViewById(R.id.currentWeight);
        goalWeight = rootView.findViewById(R.id.goalWeight);
        imgLogWeight = rootView.findViewById(R.id.imgLogWeight);
        rlWeightProgress = rootView.findViewById(R.id.rlWeightProgress);
        rlDietOverview = rootView.findViewById(R.id.rlDietOverview);
        rlActivityOverview = rootView.findViewById(R.id.rlActivityOverview);
        rlHydrationOverview = rootView.findViewById(R.id.rlHydrationOverview);


    }

    /*public void onScrollToWater() {
        openDiaryFragment();
    }

    public void openDiaryFragment() {
        Fragment fragment = new DiaryFragment();
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)  // Optional: Add transaction to back stack
                    .commit();
        }
    }*/
}