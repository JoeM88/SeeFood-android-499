package com.example.seefood.ui.login;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seefood.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.LoginUsername);
        password = findViewById(R.id.LoginPassword);
    }
}
