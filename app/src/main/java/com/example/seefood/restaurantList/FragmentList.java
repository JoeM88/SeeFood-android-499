package com.example.seefood.restaurantList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.example.seefood.restaurantDetails.FragmentRestaurantDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FragmentList extends Fragment implements RecyclerViewAdapter.OnRestaurantListener{


    private View v;
    private RecyclerView myRecyclerView;
    List<Restaurant> lstRestaurant;
    List<String> restaurantNames;
    private RecyclerViewAdapter recycleAdapter;
    private Context mContext;
    private FirebaseFirestore db;
    private String type;



    public FragmentList(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_fragment, container, false);
        mContext = getContext();
        //String name, String address, int rating, double distance, int numReviews, String category
        assert getArguments() != null;
        //String type = getArguments().getString("type");
        type = getArguments().getString("type");
        assert type != null;
        restaurantNames = new ArrayList<>();
        lstRestaurant = new ArrayList<>();
        myRecyclerView = v.findViewById(R.id.restaurant_recyclerview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.addItemDecoration(new DividerItemDecoration(myRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        db = FirebaseFirestore.getInstance();
        loadDataFromFirebase(type);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRestaurantClick(int position) {

        if(mContext == null)
        {
            return;
        }

        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity)mContext;
            Bundle b = new Bundle();
            b.putParcelable("RestaurantObject", lstRestaurant.get(position));
            FragmentRestaurantDetails frag = new FragmentRestaurantDetails();
            frag.setArguments(b);
            mainActivity.switchContent(R.id.container_fragment, frag);
        }



    }
    public void loadDataFromFirebase(String type){
        if(lstRestaurant.size() > 0)
        {
            lstRestaurant.clear();
        }

        if(type.charAt(0) < 58 && type.charAt(0) > 47){
            if(type.length() > 5){
                db.collection("Restaurants")
                        .whereEqualTo("streetAddress", type)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    Restaurant res = new Restaurant(querySnapshot.getString("restName"), querySnapshot.getString("streetAddress"), 3, 21.5, 45, "fastfood", querySnapshot.getString("photoURL"));
                                    lstRestaurant.add(res);
                                }
                                recycleAdapter = new RecyclerViewAdapter(mContext, lstRestaurant, FragmentList.this::onRestaurantClick);
                                myRecyclerView.setAdapter(recycleAdapter);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Problem ----1----", Toast.LENGTH_SHORT);
                        Log.v("----1----", e.getMessage());
                    }
                });
            }
            else {
                db.collection("Restaurants")
                        .whereEqualTo("zipCode", type)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    Restaurant res = new Restaurant(querySnapshot.getString("restName"), querySnapshot.getString("streetAddress"), 3, 21.5, 45, "fastfood", querySnapshot.getString("photoURL"));
                                    lstRestaurant.add(res);
                                }
                                recycleAdapter = new RecyclerViewAdapter(mContext, lstRestaurant, FragmentList.this::onRestaurantClick);
                                myRecyclerView.setAdapter(recycleAdapter);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Problem ----1----", Toast.LENGTH_SHORT);
                        Log.v("----1----", e.getMessage());
                    }
                });
            }
        }
        else {
            db.collection("Restaurants")
                    .whereEqualTo("city", type)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                Restaurant res = new Restaurant(querySnapshot.getString("restName"), querySnapshot.getString("streetAddress"), 3, 21.5, 45, "fastfood", querySnapshot.getString("photoURL"));
                                lstRestaurant.add(res);
                            }
                            recycleAdapter = new RecyclerViewAdapter(mContext, lstRestaurant, FragmentList.this::onRestaurantClick);
                            myRecyclerView.setAdapter(recycleAdapter);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Problem ----1----", Toast.LENGTH_SHORT);
                    Log.v("----1----", e.getMessage());
                }
            });
        }

//        db.collection("Restaurants")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                for(DocumentSnapshot querySnapshot : task.getResult()){
//                                    Restaurant res = new Restaurant(querySnapshot.getString("restName") , querySnapshot.getString("streetAddress"), 3, 21.5, 45, "fastfood", querySnapshot.getString("photoURL"));
//                                    lstRestaurant.add(res);
//                                }
//                                recycleAdapter = new RecyclerViewAdapter(mContext, lstRestaurant, FragmentList.this::onRestaurantClick);
//                                myRecyclerView.setAdapter(recycleAdapter);
//
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(mContext, "Problem ----1----", Toast.LENGTH_SHORT);
//                        Log.v("----1----", e.getMessage());
//                    }
//                });



//        switch (type)
//        {
//            case("All"):
//                db.collection("Restaurants")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                for(DocumentSnapshot querySnapshot : task.getResult()){
//                                    Restaurant res = new Restaurant(querySnapshot.getString("restName") , querySnapshot.getString("streetAddress"), 3, 21.5, 45, "fastfood", querySnapshot.getString("photoURL"));
//                                    lstRestaurant.add(res);
//                                }
//                                recycleAdapter = new RecyclerViewAdapter(mContext, lstRestaurant, FragmentList.this::onRestaurantClick);
//                                myRecyclerView.setAdapter(recycleAdapter);
//
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(mContext, "Problem ----1----", Toast.LENGTH_SHORT);
//                        Log.v("----1----", e.getMessage());
//                    }
//                });
//            case("favorite"):
//                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                String userId = firebaseAuth.getUid();
//                DocumentReference docRef = db.collection("Customer").document(userId);
//                readData(new FireStoreCallBack() {
//                    @Override
//                    public void onCallBack(List<String> res) {
//                        restaurantNames = res;
//                    }
//                }, docRef);
//
//
//
//
        }



    }
//    private void readData(FireStoreCallBack f, DocumentReference docRef){
//        docRef.get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task){
//                        if(task.isSuccessful()){
//                            DocumentSnapshot document = task.getResult();
//
//                            if(document.exists()){
//                                restaurantNames = (List<String>) document.get("favorites");
//                                f.onCallBack(restaurantNames);
//                            }
//                            else {
//                                Toast.makeText(mContext, "problem retrieving data", Toast.LENGTH_SHORT);
//
//                            }
//                        }
//                        else {
//                            Toast.makeText(mContext, "data does not exist",  Toast.LENGTH_SHORT);
//                        }
//
//                    }
//                });
//    }
//    private interface FireStoreCallBack{
//        void onCallBack(List<String> restaurantNames);
//    }
