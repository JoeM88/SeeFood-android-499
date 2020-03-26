package com.example.seefood;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SignUpActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fragmentManager = getSupportFragmentManager();


        if(findViewById(R.id.fragment_container) != null){

            if(savedInstanceState != null){
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SignUpFragmentUser signUpFragmentUser = new SignUpFragmentUser();
            SignUpRestaurantOwnerFragment signUpRestaurantOwnerFragment = new SignUpRestaurantOwnerFragment();
            LoginFragmentUser loginFragmentUser = new LoginFragmentUser();

            fragmentTransaction.replace(R.id.fragment_container, loginFragmentUser, "LoginUser");
            //fragmentTransaction.replace(R.id.fragment_container, signUpFragmentUser, "SignUpUser");
            //fragmentTransaction.replace(R.id.fragment_container, signUpRestaurantOwnerFragment, "SignUpRO");
            fragmentTransaction.commit();
        }
    }


}