package com.example.vitalitypro;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fifth, container, false);
        initViews(rootView);
        initProgressBar();

        if(!SecondFragment.option1.equals("Maintain weight")){
            txtGoalWeight.setVisibility(View.VISIBLE);
            textInputLayoutGoalWeight.setVisibility(View.VISIBLE);
            textInputEditTextGoalWeight.setVisibility(View.VISIBLE);
        }
        initEditTextHeight();
        initEditTextWeight();
        initEditTextGoalWeight();


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
                int goalWeight = Integer.parseInt(goalWeightText);

                if(SecondFragment.option1.equals("Lose weight")){
                    if(goalWeight>weight){
                        textInputLayoutGoalWeight.setError("Your goal is Lose Weight: Goal weight must be lower than current weight.");
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
}