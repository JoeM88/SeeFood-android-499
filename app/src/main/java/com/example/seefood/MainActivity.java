package com.example.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView botNav = findViewById(R.id.bottom_nav);
        botNav.setOnNavigationItemSelectedListener(navListener);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.container_fragment, new FragmentList());
        ft.commit();

    }

    public void viewProfile(View view){
        Intent intent = new Intent(this, profileActivity.class);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new FragmentList();
                    break;
                case R.id.nav_favorite:
                    selectedFragment = new FragmentFavorite();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new FragmentProfile();
                    //break;
                    viewProfile(null);

            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, selectedFragment).commit();

            return true;
        }
    };
}
