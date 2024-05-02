package com.example.vitalitypro;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FourthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourthFragment newInstance(String param1, String param2) {
        FourthFragment fragment = new FourthFragment();
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

    private final String TAG = "Fourth Fragment";
    private ConstraintLayout parent;
    private TextView txtTitle, txtHint, txtAge, txtBioSex, txtCountry;
    private MaterialButtonToggleGroup materialButtonToggleGroup;
    private MaterialButton btnMale, btnFemale, btnNext;
    private TextInputLayout textInputLayoutAge;
    private TextInputEditText textInputEditTextAge;
    private Spinner countrySpinner;
    private ProgressBar progressBar;
    private int currentProgress = 60;

    public static String gender;
    public static int age;
    public static String country;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fourth, container, false);
        initViews(rootView);
        initProgressBar();
        initEditText();
        initSpinner();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if Male or Female is selected
                if (!btnMale.isChecked() && !btnFemale.isChecked()) {
                    // Neither Male nor Female is selected, show toast message
                    Toast.makeText(getContext(), "Please select your gender", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick method
                }

                // Get the selected gender
                String gender = btnMale.isChecked() ? "Male" : "Female";

                // Get the entered age
                String ageStr = textInputEditTextAge.getText().toString();
                // Check if age is empty
                if (ageStr.isEmpty()) {
                    // Age is empty, show toast message
                    Toast.makeText(getContext(), getString(R.string.age_empty), Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick method
                }
                // Check if age is a valid number
                int age;
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    // Age is not a valid number
                    return; // Exit the onClick method
                }
                // Check if age is less than 18
                if (age < 18) {
                    // Age is less than 18
                    return; // Exit the onClick method
                }
                // Get the selected country
                String country = countrySpinner.getSelectedItem().toString();

                //
                Log.d(TAG, "btnNext clicked: Selected values: Gender: "+gender+" Age: "+age+" Country: "+country);
                openFourthFragment();
            }
        });

        return rootView;

    }

    private void openFourthFragment() {
        // Create an instance of the FifthFragment
        FifthFragment fifthFragment = new FifthFragment();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the FifthFragment
        fragmentTransaction.replace(R.id.frameLayout, fifthFragment);

        // Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();

    }

    private void initEditText() {
        textInputEditTextAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called after the text has changed

                // Get the text entered by the user
                String ageText = s.toString().trim();

                // Check if the entered text is empty
                if (TextUtils.isEmpty(ageText)) {
                    // If it's empty, show a generic error message
                    textInputLayoutAge.setError(getString(R.string.login_goals_edit_text_valid_value));
                } else {
                    // Parse the age entered by the user
                    int age = Integer.parseInt(ageText);

                    // Check if the age is less than 18
                    if (age < 18) {
                        // If the age is less than 18, show a specific error message
                        textInputLayoutAge.setError(getString(R.string.login_goals_edit_text_error1));
                    } else {
                        // If the age is valid, clear any error message
                        textInputLayoutAge.setError(null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), R.array.countries_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        countrySpinner.setAdapter(adapter);
        // def: Slovenia
        countrySpinner.setSelection(192);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected country name
                String selectedCountry = parent.getItemAtPosition(position).toString();

                // Do something with the selected country
                Toast.makeText(getContext(), "Selected country: " + selectedCountry, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

    }

    private void initViews(View rootView) {
        parent = rootView.findViewById(R.id.parent);

        txtTitle = rootView.findViewById(R.id.txtTitle);
        txtHint = rootView.findViewById(R.id.txtHint);
        txtAge = rootView.findViewById(R.id.txtAge);
        txtBioSex = rootView.findViewById(R.id.txtBioSex);
        txtCountry = rootView.findViewById(R.id.txtCountry);

        materialButtonToggleGroup = rootView.findViewById(R.id.materialButtonToggleGroup);

        btnMale = rootView.findViewById(R.id.btnMale);
        btnFemale = rootView.findViewById(R.id.btnFemale);
        btnNext = rootView.findViewById(R.id.btnNext);

        textInputLayoutAge = rootView.findViewById(R.id.textInputLayoutAge);
        textInputEditTextAge = rootView.findViewById(R.id.textInputEditTextAge);
        countrySpinner = rootView.findViewById(R.id.countrySpinner);

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