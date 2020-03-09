package com.example.seefood.ui.signup;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seefood.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class SignUpRestaurantOwnerActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERNRO =
            Pattern.compile(
                    "^" +
                            "(?=.*[0-9])" +         // at least 1 digit
                            "(?=.*[a-z])" +         // at least 1 lowercase letter
                            "(?=.*[A-Z])" +         // at least 1 uppercase letter
                            "(?=.*[@#$%^&+=])" +    // at least 1 special character
                            "(?=\\s+$)" +           // no white spaces
                            ".{6,}" +               // at least 6 characters
                            "$");

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]+ 256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]* 64}" +
                    "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]* 25}" +
                    ")+"
            );

    private TextInputLayout email, usernameRO, passwordRO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_restaurantowner);

        usernameRO = findViewById(R.id.userTextInputLayoutUsernameRO);
        passwordRO = findViewById(R.id.userTextInputLayoutPasswordRO);
        email = findViewById(R.id.userTextInputLayoutEmailRO);
    }

    public void confirmInput(View v){
        if (!verifyPassword() | !verifyUsername() | !verifyEmail()){
            return;
        }

        String input = "Email: " + email.getEditText().getText().toString();
        input += "\n";
        input += "Username: " + usernameRO.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + passwordRO.getEditText().getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

    private boolean verifyEmail() {
        String emailInput = email.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Email cannot be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    private boolean verifyUsername() {
        String usernameInput = usernameRO.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            usernameRO.setError("Username cannot be empty");
            return false;
        }
        else {
            usernameRO.setError(null);
            return true;
        }
    }

    private boolean verifyPassword() {
        String passwordInput = passwordRO.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordRO.setError("Password cannot be empty");
            return false;
        }
        else if (!PASSWORD_PATTERNRO.matcher(passwordInput).matches()) {
            passwordRO.setError("Password does not meet requirements");
            return false;
        }
        else {
            passwordRO.setError(null);
            return true;
        }
    }
}
