package com.example.vitalitypro;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public final static String TAG = "Second Fragment";
    private ProgressBar progressBar;
    private MaterialButtonToggleGroup toggleGroupTop;
    private MaterialButton btnNext;
    private MaterialButton btnLoseWeight, btnMaintainWeight, btnGainWeight;

    public static String option1;
    private int buttonsChecked = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        initViews(rootView);
        initProgressBar();

        toggleGroupTop.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int checkedId, boolean isChecked) {
                if(isChecked){
                    String selectedButtonId = getResources().getResourceEntryName(checkedId);
                    switch(selectedButtonId){
                        case "btnLoseWeight":
                            option1 = "Lose weight";
                            buttonsChecked = 1;
                            Log.d(TAG, "toggleGroupTop started: Chosen "+option1);
                            break;
                        case "btnMaintainWeight":
                            option1 = "Maintain weight";
                            buttonsChecked = 1;
                            Log.d(TAG, "toggleGroupTop started: Chosen "+option1);
                            break;
                        case "btnGainWeight":
                            option1 = "Gain weight";
                            buttonsChecked = 1;
                            Log.d(TAG, "toggleGroupTop started: Chosen "+option1);
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
                    Toast.makeText(requireActivity(), "Please select at least one option", Toast.LENGTH_SHORT).show();
                }
                else{
                    openThirdFragment();
                }
            }
        });
        
        return rootView;
    }

    private void openThirdFragment() {
        // Create an instance of the SecondFragment
        ThirdFragment thirdFragment = new ThirdFragment();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the SecondFragment
        fragmentTransaction.replace(R.id.frameLayout, thirdFragment);

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
            }
        }
    }

    private void initViews(View rootView) {
        toggleGroupTop = rootView.findViewById(R.id.toggleGroupTop);
        btnNext = rootView.findViewById(R.id.btnNext);
        btnLoseWeight = rootView.findViewById(R.id.btnLoseWeight);
        btnMaintainWeight = rootView.findViewById(R.id.btnMaintainWeight);
    }



}