package com.example.seefood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.restaurantList.FragmentList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Stack;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private Stack<Fragment> stack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BottomNavigationView botNav = findViewById(R.id.bottom_nav);
        botNav.setOnNavigationItemSelectedListener(navListener);
        stack = new Stack<>();
        switchContent(R.id.container_fragment, new FragmentHome(), true);



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
            clearBackStack();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, selectedFragment).commit();

            return true;
        }
    };

    public void switchContent(int id, Fragment fragment, boolean add) {

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(id, fragment, fragment.toString());
//        ft.addToBackStack(null);
//        ft.commit();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (add)
            stack.push(fragment);
        transaction.replace(R.id.container_fragment, fragment);
        transaction.commit();
    }

    public void popFragment() {
        if (!stack.isEmpty()) {
            Fragment fragment = stack.elementAt(stack.size() - 2);
            stack.pop();
            switchContent(R.id.container_fragment, fragment, false);
        } else
            super.onBackPressed();
    }

    public void clearBackStack() {
        stack.clear();
    }

    @Override
    public void onBackPressed() {
        popFragment();
    }

}
