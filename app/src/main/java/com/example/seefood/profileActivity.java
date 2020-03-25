package com.example.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class profileActivity extends AppCompatActivity {

    public static FragmentManager fm;
    private FirebaseAuth mAuth;

    static String uid;

    public void goSignUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            uid = currentUser.getUid();
        }
        else{
            goSignUp(null);
        }
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        //TODO: DELETE THIS TEMPORARY


        fm = getSupportFragmentManager();

        if(findViewById(R.id.container_fragment) != null) {
            if(savedInstanceState != null){
                return;
            }

            FragmentTransaction ft = fm.beginTransaction();

        }
//        FragmentTransaction ft  = getSupportFragmentManager().beginTransaction();

//        ft.replace(R.id.container_fragment, new FragmentList());
//        ft.commit();
    }


}
