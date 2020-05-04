package com.example.seefood.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seefood.FragmentProfile;
import com.example.seefood.R;
import com.example.seefood.displayProfiles.dispCustomerProfile;
import com.example.seefood.displayProfiles.displayRestaurantProfile;
import com.example.seefood.login.SignUpActivity;
import com.example.seefood.models.CustomerModel;
import com.example.seefood.models.RestaurantModel;
import com.example.seefood.profileSetup.createProfileActivity;
import com.example.seefood.restaurantList.RecyclerViewAdapter;
import com.example.seefood.restaurantList.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    CustomerModel mCustomer;
    ArrayList<String> mFavorites;
    ArrayList<RestaurantModel> lstFavorites;

    public FragmentFavorite() {}

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            this.uid = mAuth.getCurrentUser().getUid();
        } else {
            goSignUp(null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.favorite_fragment, container, false);
        mContext = getContext();

        mRecyclerView = v.findViewById(R.id.favorite_list_fragment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mFavorites = new ArrayList<>();
        lstFavorites = new ArrayList<>();
        mCustomer = new CustomerModel();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        customerRef = db.collection("Customer").document(this.uid);
        //loadFavoritesFromDatabase();
        getCustomer();

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

    private void getCustomer() {
        this.customerRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                this.mCustomer = task.getResult().toObject(CustomerModel.class);
                this.mFavorites = this.mCustomer.getFavorites();
                Log.d("favorites", this.mCustomer.getFavorites().toString());
                if (!this.mFavorites.isEmpty()) {
                    Log.d("inside loop", "inside first loop");
                    db.collection("Restaurants")
                            .get()
                            .addOnCompleteListener(atask -> {
                                for (DocumentSnapshot qSnapshot : atask.getResult()) {
                                    RestaurantModel res = qSnapshot.toObject(RestaurantModel.class);
                                    Log.d("grabbing database", "grabbing snapshot");
                                    for (String resName : mFavorites) {
                                        Log.d("customer favorites", "comparing " + res.restName + " to " + resName);
                                        if (res.restName == resName) {
                                            lstFavorites.add(res);
                                        }
                                    }
                                }
                                Log.d("list", lstFavorites.toString());
                                mAdapter = new RecyclerViewAdapter(getContext(), lstFavorites,
                                        FragmentFavorite.this::onRestaurantClick,
                                        FragmentFavorite.this::onRestaurantLikeClicked);
                                mRecyclerView.setAdapter(mAdapter);
                            })
                            .addOnFailureListener(e -> {
                                Log.e("load error", "Could not load customer data from database");
                            });
                } else {
                    Log.d("empty", "currently no favorites");
                }
            } else {
                Log.d("query error", "Could not find customer");
            }
        });
    }

    public void loadFavoritesFromDatabase(){
        Log.d("inside", "Entered load data function");
        getCustomer();
        if (!this.mFavorites.isEmpty()) {
            Log.d("inside loop", "inside first loop");
            db.collection("Restaurants")
                    .get()
                    .addOnCompleteListener(task -> {
                       for (DocumentSnapshot qSnapshot : task.getResult()) {
                           RestaurantModel res = qSnapshot.toObject(RestaurantModel.class);
                           Log.d("grabbing database", "grabbing snapshot");
                           for (String resName : mFavorites) {
                               Log.d("customer favorites", "comparing " + res.restName + " to " + resName);
                               if (res.restName == resName) {
                                   lstFavorites.add(res);
                               }
                           }
                       }
                       Log.d("list",lstFavorites.toString());
                       mAdapter = new RecyclerViewAdapter(getContext(), lstFavorites,
                               FragmentFavorite.this::onRestaurantClick,
                               FragmentFavorite.this::onRestaurantLikeClicked);
                       mRecyclerView.setAdapter(mAdapter);
                    })
            .addOnFailureListener(e -> {
                Log.e("load error", "Could not load customer data from database");
            });
        } else {
            Log.d("empty", "currently no favorites");
        }
    }

    private void onRestaurantLikeClicked(int i, ImageView imageView) {
    }

    private void onRestaurantClick(int i) {
    }

}
