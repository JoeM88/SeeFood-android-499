package com.example.seefood.profileSetup;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;


public class createProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.createProf_container, new createProfile_step1());
        ft.commit();
    }

        @SuppressLint("MissingSuperCall")
        @Override
        public void onBackPressed() {

        }
}