package com.example.seefood.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.seefood.FragmentProfile;
import com.example.seefood.R;
import com.example.seefood.login.SignUpActivity;
import com.example.seefood.restaurantList.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FragmentFavorite extends Fragment {
    private View v;
    private FragmentProfile mFragmentProfile;
    private ArrayList<Restaurant> mFavorites;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    static String uid;

    public FragmentFavorite() {}

    @Override
    public void onStart() {
        super.onStart();
        mFragmentProfile = new FragmentProfile();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            uid = currentUser.getUid();
            mFragmentProfile.checkProfExists();
        } else {
            goSignUp(null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.favorite_fragment, container, false);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void goSignUp(View view){
        Intent intent = new Intent(getContext(), SignUpActivity.class);
        startActivity(intent);
    }

}
