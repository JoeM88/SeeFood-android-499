package com.example.seefood;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

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

            SignUpRestaurantOwnerFragment test = new SignUpRestaurantOwnerFragment();

            fragmentTransaction.add(R.id.fragment_container, signUpFragmentUser, null);

            //fragmentTransaction.add(R.id.fragment_container, test, null);
            fragmentTransaction.commit();
        }
    }


}