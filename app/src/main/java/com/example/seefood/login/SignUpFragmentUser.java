package com.example.seefood.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragmentUser extends Fragment {

    public SignUpFragmentUser() {
        // Required empty public constructor
    }

    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "^" +
                            "(?=.*[0-9])" +         // at least 1 digit
                            "(?=.*[a-z])" +         // at least 1 lowercase letter
                            "(?=.*[A-Z])" +         // at least 1 uppercase letter
                            "(?=.*[@#$%^&+=])" +    // at least 1 special character
                            "(?=\\S+$)" +           // no white spaces
                            ".{6,20}" +               // at least 6 characters
                            "$");

    public static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                            "\\@" +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                            "(" +
                            "\\." +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                            ")+"
            );

    private TextInputLayout email, password;
    private FirebaseAuth firebaseAuth;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        email = view.findViewById(R.id.userTextInputLayoutEmail);
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
        if (!verifyPassword() | !verifyEmail()){
            return;
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // User is successfully registered and logged in
                                Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT);
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        }
                    });
        }

        String input = "Email: " + email.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + password.getEditText().getText().toString();

        Toast.makeText(getActivity(), input, Toast.LENGTH_SHORT).show();
    }

    private boolean verifyEmail() {
        String emailInput = email.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Email cannot be empty");
            return false;
        }
        else if (!EMAIL_PATTERN.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        }
        else {
            email.setError(null);
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
            password.setError("Password does not meet requirements. Requires at least 1 Uppercase, Lowercase, Number, and Special Character: @#$%^&+_ are valid characters");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }
}