package com.example.vitalitypro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    private final static String TAG ="Login Fragment";
    private TextView txtTitle;
    private TextInputLayout usernameInputLayout, passwordInputLayout;
    private TextInputEditText usernameEditText, passwordEditText;
    private MaterialButton btnNext;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_login, container, false);
        initViews(rootView);
        initToolBar(rootView);
        initUsernameEditText();
        initPasswordEditText();
        initLoginCheck();


        return rootView;
    }

    private void initPasswordEditText() {
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString().trim();
                if(TextUtils.isEmpty(password)){
                    passwordInputLayout.setError(getString(R.string.login_goals_edit_text_valid_value));
                }
                else{
                    passwordInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initUsernameEditText() {
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username = s.toString().trim();
                if(TextUtils.isEmpty(username)){
                    usernameInputLayout.setError(getString(R.string.login_goals_edit_text_valid_value));
                }
                else{
                    usernameInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initLoginCheck() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your username.", Toast.LENGTH_SHORT).show();
                    return; // Exit method early if username is empty
                } else if (usernameInputLayout.getError() != null) {
                    Toast.makeText(getContext(), "Your username is not valid.", Toast.LENGTH_SHORT).show();
                    return; // Exit method early if username is invalid
                }
                if (passwordEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your password.", Toast.LENGTH_SHORT).show();
                    return; // Exit method early if password is empty
                } else if (passwordInputLayout.getError() != null) {
                    Toast.makeText(getContext(), "Your password is not valid.", Toast.LENGTH_SHORT).show();
                    return; // Exit method early if password is invalid
                }

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                UserDatabaseHandler userDatabaseHandler = new UserDatabaseHandler(getContext());
                boolean doesUserExist = userDatabaseHandler.isUsernameAndPasswordExists(username, password);
                if(!doesUserExist){
                    Toast.makeText(getContext(), "Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    private void initViews(View rootView) {
        txtTitle = rootView.findViewById(R.id.txtTitle);
        usernameInputLayout = rootView.findViewById(R.id.usernameInputLayout);
        passwordInputLayout = rootView.findViewById(R.id.passwordInputLayout);
        usernameEditText = rootView.findViewById(R.id.usernameEditText);
        passwordEditText = rootView.findViewById(R.id.passwordEditText);
        btnNext = rootView.findViewById(R.id.btnNext);
    }


    private void initToolBar(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        // Find the nested layout within the Toolbar
        View nestedLayout = toolbar.findViewById(R.id.nestedLayout);

        // Find the txtTitle TextView within the nested layout
        TextView txtToolbarTitle = nestedLayout.findViewById(R.id.txtToolbarTitle);

        // Set the new text for txtTitle
        txtToolbarTitle.setText("Sign In");

        ImageView imgBack = nestedLayout.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openPreviusFragment();
            }
        });
    }
    private OnBackPressedCallback onBackPressedCallback;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Register onBackPressedCallback
        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle back press
                // Check if you are in FirstFragment, if not, navigate back to SignUpLoginFragment
                if (!(requireActivity().getSupportFragmentManager().findFragmentById(R.id.frameLayout) instanceof SignUpLoginFragment)) {
                    // Navigate back to SignUpLoginFragment
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                            .replace(R.id.frameLayout, new SignUpLoginFragment())
                            .commit();
                } else {
                    // If already in SignUpLoginFragment, handle back press as normal
                    requireActivity().onBackPressed();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister onBackPressedCallback by disabling it
        onBackPressedCallback.setEnabled(false);
    }

}