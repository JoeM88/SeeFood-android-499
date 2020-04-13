package com.example.seefood;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


public class createProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.createProf_container, new createProfile_step1());
        ft.commit();
    }

    public void addListenerOnButton(){

    }
}
