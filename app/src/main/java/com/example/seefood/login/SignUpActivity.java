package com.example.seefood.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.MainActivity;
import com.example.seefood.R;

public class SignUpActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fragmentManager = getSupportFragmentManager();


        if(findViewById(R.id.fragment_container) != null){

            if(savedInstanceState != null){
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SignUpFragmentUser signUpFragmentUser = new SignUpFragmentUser();
            LoginFragmentUser loginFragmentUser = new LoginFragmentUser();

            fragmentTransaction.replace(R.id.fragment_container, loginFragmentUser, "LoginUser");
            //fragmentTransaction.replace(R.id.fragment_container, signUpFragmentUser, "SignUpUser");
            //fragmentTransaction.replace(R.id.fragment_container, signUpRestaurantOwnerFragment, "SignUpRO");
            fragmentTransaction.commit();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}