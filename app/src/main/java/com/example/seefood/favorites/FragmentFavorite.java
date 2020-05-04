package com.example.seefood.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seefood.FragmentProfile;
import com.example.seefood.R;
import com.example.seefood.displayProfiles.dispCustomerProfile;
import com.example.seefood.displayProfiles.displayRestaurantProfile;
import com.example.seefood.login.SignUpActivity;
import com.example.seefood.models.CustomerModel;
import com.example.seefood.profileSetup.createProfileActivity;
import com.example.seefood.restaurantList.RecyclerViewAdapter;
import com.example.seefood.restaurantList.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FragmentFavorite extends Fragment {
    private View v;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private DocumentReference customerRef;
    String uid;
    String type;
    CustomerModel mCustomer;
    ArrayList<String> mFavorites;

    public FragmentFavorite() {}

    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            uid = currentUser.getUid();
        } else {
            goSignUp(null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.favorite_fragment, container, false);
        mContext = getContext();

        //mRecyclerView = v.findViewById(R.id.fa)

        assert getArguments() != null;
        type = getArguments().getString("type");
        assert type != null;

        mFavorites = new ArrayList<>();

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
