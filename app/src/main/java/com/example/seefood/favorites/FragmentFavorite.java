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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FragmentFavorite extends Fragment {
    private View v;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private DocumentReference customerRef;
    private CollectionReference restRef;
    String uid;
    CustomerModel mCustomer;
    ArrayList<String> mFavorites;
    ArrayList<RestaurantModel> lstFavorites;

    public FragmentFavorite() {}

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser().getUid() != null) {
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
        restRef = db.collection("Restaurants");
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
        customerRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mCustomer = task.getResult().toObject(CustomerModel.class);
                mFavorites = mCustomer.getFavorites();
                if (!mFavorites.isEmpty()) {
                    for (String favorites : mFavorites) {
                        restRef.whereEqualTo("restName", favorites)
                                .get()
                                .addOnCompleteListener(nTask ->{
                            if (nTask.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : nTask.getResult()) {
                                    RestaurantModel currRest = doc.toObject(RestaurantModel.class);
                                    lstFavorites.add(currRest);
                                }
                            }
                            lstFavorites.size();
                            mAdapter = new RecyclerViewAdapter(getContext(), lstFavorites,
                                    FragmentFavorite.this::onRestaurantClick,
                                    FragmentFavorite.this::onRestaurantLikeClicked);
                            mRecyclerView.setAdapter(mAdapter);
                        });
                    }
                }
            } else {
                Log.d("customer fail", "Could not load customer");
            }
        });
    }

    private void onRestaurantLikeClicked(int i, ImageView imageView) {
    }

    private void onRestaurantClick(int i) {
    }

}
