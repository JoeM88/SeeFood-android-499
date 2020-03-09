package com.example.seefood.ui.signup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.seefood.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragmentUser extends Fragment {

    public SignUpFragmentUser() {
        // Required empty public constructor
    }

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "^" +
                            "(?=.*[0-9])" +         // at least 1 digit
                            "(?=.*[a-z])" +         // at least 1 lowercase letter
                            "(?=.*[A-Z])" +         // at least 1 uppercase letter
                            "(?=.*[@#$%^&+=])" +    // at least 1 special character
                            "(?=\\s+$)" +           // no white spaces
                            ".{6,}" +               // at least 6 characters
                            "$");

    private TextInputLayout username, password;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_user, container, false);

        username = view.findViewById(R.id.userTextInputLayoutUsername);
        password = view.findViewById(R.id.userTextInputLayoutPassword);

        button = view.findViewById(R.id.buttonCreateAccUser);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInput(view);
            }
        });

        return view;
    }

    public void confirmInput(View v){
        if (!verifyPassword() | !verifyUsername()){
            return;
        }

        String input = "Username: " + username.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + password.getEditText().getText().toString();

        Toast.makeText(getActivity(), input, Toast.LENGTH_SHORT).show();
    }

    private boolean verifyUsername() {
        String usernameInput = username.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            username.setError("Username cannot be empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private boolean verifyPassword() {
        String passwordInput = password.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password does not meet requirements");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }
}
