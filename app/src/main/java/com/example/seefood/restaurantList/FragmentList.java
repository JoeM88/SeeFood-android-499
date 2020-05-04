package com.example.seefood.restaurantList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.FragmentProfile;
import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.example.seefood.models.CustomerModel;
import com.example.seefood.models.RestaurantModel;
import com.example.seefood.restaurantDetails.FragmentRestaurantDetails;
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
import java.util.Objects;


public class FragmentList extends Fragment implements RecyclerViewAdapter.OnRestaurantListener, RecyclerViewAdapter.OnRestaurantLikeListener {


    private View v;
    private RecyclerView myRecyclerView;
    private RecyclerViewAdapter recycleAdapter;
    private Context mContext;
    private FirebaseFirestore db;
    private FirebaseUser mCurrentUser;
    private String type;
    private FragmentProfile mFragmentProfile;
    List<RestaurantModel> lstRestaurant;
    FirebaseAuth firebaseAuth;
    DocumentReference customerRef;
    String userId;
    CustomerModel mCustomer;
    ArrayList<String> mFavoriteRestaurants;

    public FragmentList() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_fragment, container, false);
        mContext = getContext();


        assert getArguments() != null;
        type = getArguments().getString("type");
        assert type != null;

        lstRestaurant = new ArrayList<>();
        mFavoriteRestaurants = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getUid();

        myRecyclerView = v.findViewById(R.id.restaurant_recyclerview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.addItemDecoration(new DividerItemDecoration(myRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        db = FirebaseFirestore.getInstance();
        customerRef = db.collection("Customer").document(userId);
        getCustomer();
        loadDataFromFirebase(type);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRestaurantClick(int position) {

        if (mContext == null) {
            return;
        }

        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Bundle b = new Bundle();
            b.putParcelable("RestaurantObject", lstRestaurant.get(position));
            FragmentRestaurantDetails frag = new FragmentRestaurantDetails();
            frag.setArguments(b);
            mainActivity.switchContent(R.id.container_fragment, frag);
        }
    }


    public void loadDataFromFirebase(String type) {
        if (lstRestaurant.size() > 0) {
            lstRestaurant.clear();
        }

        if (type.charAt(0) < 58 && type.charAt(0) > 47) {
            if (type.length() > 5) {
                db.collection("Restaurants")
                        .whereEqualTo("streetAddress", type)
                        .get()
                        .addOnCompleteListener(task -> {

                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                RestaurantModel res = querySnapshot.toObject(RestaurantModel.class);

                                lstRestaurant.add(res);
                            }
                            recycleAdapter = new RecyclerViewAdapter(mContext, lstRestaurant, FragmentList.this::onRestaurantClick, FragmentList.this::onRestaurantLikeClicked);
                            myRecyclerView.setAdapter(recycleAdapter);


                        }).addOnFailureListener(e -> {
                            Toast.makeText(mContext, "Problem ----1----", Toast.LENGTH_SHORT);
                            Log.v("----1----", e.getMessage());
                        });

            } else {
                db.collection("Restaurants")
                        .whereEqualTo("zipCode", type)
                        .get()
                        .addOnCompleteListener(task -> {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                RestaurantModel res = querySnapshot.toObject(RestaurantModel.class);
                                lstRestaurant.add(res);
                            }
                            recycleAdapter = new RecyclerViewAdapter(mContext, lstRestaurant, FragmentList.this::onRestaurantClick, FragmentList.this::onRestaurantLikeClicked);
                            myRecyclerView.setAdapter(recycleAdapter);


                        }).addOnFailureListener(e -> {
                            Toast.makeText(mContext, "Problem ----1----", Toast.LENGTH_SHORT);
                            Log.v("----1----", e.getMessage());
                        });
            }
        } else {
            db.collection("Restaurants")
                    .whereEqualTo("city", type)
                    .get()
                    .addOnCompleteListener(task -> {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            RestaurantModel res = querySnapshot.toObject(RestaurantModel.class);
                            lstRestaurant.add(res);
                        }
                        recycleAdapter = new RecyclerViewAdapter(mContext, lstRestaurant, FragmentList.this::onRestaurantClick, FragmentList.this::onRestaurantLikeClicked);
                        myRecyclerView.setAdapter(recycleAdapter);


                    }).addOnFailureListener(e -> {
                        Toast.makeText(mContext, "Problem ----1----", Toast.LENGTH_SHORT);
                        Log.v("----1----", e.getMessage());
                    });
        }

    }

    @Override
    public void onRestaurantLikeClicked(int position, ImageView img) {
        this.mCurrentUser = firebaseAuth.getCurrentUser();
        if (this.mCurrentUser != null) {
            RestaurantModel currRestaurant = lstRestaurant.get(position);
            img.setImageResource(R.drawable.heart_on);
            mFavoriteRestaurants.add(currRestaurant.restName);
            if (!mFavoriteRestaurants.isEmpty()) {
                for (String rest : mFavoriteRestaurants) {
                    Log.d("added to favorites", rest);
                }
            }
        } else {
            mFragmentProfile = new FragmentProfile();
            mFragmentProfile.goSignUp(null);
        }
        Log.v("pressed", "like button pressed");
    }

    @Override
    public void onPause() {
        super.onPause();
        updateCustomer();
    }

    private void getCustomer() {
        this.customerRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                this.mCustomer = task.getResult().toObject(CustomerModel.class);
                this.mFavoriteRestaurants = mCustomer.getFavorites();
                Log.d("display name", this.mCustomer.getDiplayName());
            } else {
                Log.d("query error", "Could not find customer");
            }
        });
    }

    private void updateCustomer() {
        this.customerRef.update("favorites", mFavoriteRestaurants)
                .addOnSuccessListener(aVoid -> {
                    Log.d("successful update", "Customer data updated successfully!");
                })
                .addOnFailureListener(aVoid -> {
                    Log.d("failed update", "Customer update failed");
                });
    }
}

