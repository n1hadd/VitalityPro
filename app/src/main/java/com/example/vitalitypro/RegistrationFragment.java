package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
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

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_registration, container, false);
        initViews(rootView);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        initGetUserName();
        initGetPassword();

        initOnClickBtnNext();

        return rootView;
    }

    private void initOnClickBtnNext() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameEditText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please enter your username.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(usernameInputLayout.getError() != null) {
                        Toast.makeText(getContext(), "Your username is not valid.", Toast.LENGTH_SHORT).show();
                    }
                }
                if(passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please enter your password.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(passwordInputLayout.getError() != null){
                        Toast.makeText(getContext(), "Your password is not valid.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        saveCredentials(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                        Log.d(TAG, "Username: "+usernameEditText.getText().toString()+", Password: "+passwordEditText.getText().toString());
                    }
                }

            }
        });

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
                            usernameInputLayout.setError(null);
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
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
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
}