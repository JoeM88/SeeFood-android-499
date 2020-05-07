package com.example.seefood.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.example.seefood.login.SignUpActivity;
import com.example.seefood.models.CustomerModel;
import com.example.seefood.models.RestaurantModel;
import com.example.seefood.restaurantDetails.FragmentRestaurantDetails;
import com.example.seefood.restaurantList.RecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FragmentFavorite extends Fragment {
    private View v;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Context mContext;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private DocumentReference customerRef;
    private CollectionReference restRef;
    String uid;
    CustomerModel mCustomer;
    ArrayList<String> mFavorites;
    ArrayList<RestaurantModel> lstFavorites;

    public FragmentFavorite() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.favorite_fragment, container, false);

        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            this.uid = mAuth.getCurrentUser().getUid();
            mContext = getContext();

            mRecyclerView = v.findViewById(R.id.favorite_list_fragment);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

            mFavorites = new ArrayList<>();
            lstFavorites = new ArrayList<>();
            mCustomer = new CustomerModel();

            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            currentUser = mAuth.getCurrentUser();
            assert currentUser != null;
            uid = currentUser.getUid();
            customerRef = db.collection("Customer").document(uid);
            restRef = db.collection("Restaurants");
            loadFavoritesFromDatabase();
        } else {
            goSignUp(null);
        }

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        updateCustomer();
        mCustomer.setFavorites(mFavorites);
    }

    private void updateCustomer() {
        this.customerRef.update("favorites", mFavorites)
                .addOnSuccessListener(aVoid -> {
                    Log.d("successful update", "Customer data updated successfully!");
                })
                .addOnFailureListener(aVoid -> {
                    Log.d("failed update", "Customer update failed");
                });
    }

    public void goSignUp(View view){
        Intent intent = new Intent(getContext(), SignUpActivity.class);
        startActivity(intent);
    }

    private void loadFavoritesFromDatabase() {
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

    private void onRestaurantLikeClicked(int position, ImageView img) {
        if (this.mCustomer != null) {
            RestaurantModel currRestaurant = lstFavorites.get(position);
            img.setImageResource(R.drawable.cross);
            Toast.makeText(mContext, currRestaurant.restName + " removed from favorites!", Toast.LENGTH_SHORT).show();
            mFavorites.remove(currRestaurant.restName);
        }
    }


    private void onRestaurantClick(int position) {
        if (mContext == null) {
            return;
        }

        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Bundle b = new Bundle();
            b.putParcelable("RestaurantObject", lstFavorites.get(position));
            FragmentRestaurantDetails frag = new FragmentRestaurantDetails();
            frag.setArguments(b);
            mainActivity.switchContent(R.id.container_fragment, frag, true);
        }
    }

}
