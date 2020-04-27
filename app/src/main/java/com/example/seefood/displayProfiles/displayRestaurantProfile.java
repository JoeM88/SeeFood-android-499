package com.example.seefood.displayProfiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.example.seefood.models.RestaurantModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class displayRestaurantProfile extends Fragment {

    public displayRestaurantProfile() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    //TextView tv;

    String uid;
    RestaurantModel dispRest = null;

    TextView showRestName;
    TextView showRestAddress;
    ImageView im;
    Button logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_restaurant_profile, container, false);
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        //tv = view.findViewById(R.id.sampleDataReturnRest);
        showRestName = view.findViewById(R.id.dispRest_RestName);
        showRestAddress = view.findViewById(R.id.dispRest_StreetAddress);
        im = view.findViewById(R.id.profile_image_rest);
        logoutButton = view.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                logout(v);
            }
        });

        displayTestData();


        return view;
    }

    //APPLEJACKS
    private void displayTestData() {
        CollectionReference rp = db.collection("Restaurants");
        Query queryRestaurant = rp.whereEqualTo("owner", uid);
        queryRestaurant.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        dispRest = document.toObject(RestaurantModel.class);
                        showRestName.setText(dispRest.getRestName());
                        showRestAddress.setText(dispRest.getStreetAddress() + "\n" + dispRest.getCity() + ", " + dispRest.getState() + " " + dispRest.getZipCode() + "\n" + dispRest.getPhoneNumber());

                        Glide.with(getContext())
                                .load(dispRest.getPhotoURL())
                                .into(im);
                    }
                }
            }
        });
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}
