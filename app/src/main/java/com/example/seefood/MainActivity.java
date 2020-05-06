package com.example.seefood;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.favorites.FragmentFavorite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BottomNavigationView botNav = findViewById(R.id.bottom_nav);
        botNav.setOnNavigationItemSelectedListener(navListener);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.container_fragment, new FragmentHome());
        ft.commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new FragmentHome();
                    break;
                case R.id.nav_favorite:
                    selectedFragment = new FragmentFavorite();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new FragmentProfile();
                    break;

            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_fragment, selectedFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
    };

    public void switchContent(int id, Fragment fragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }


}
