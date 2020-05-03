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
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class FragmentList extends Fragment implements RecyclerViewAdapter.OnRestaurantListener{


    private View v;
    private RecyclerView myRecyclerView;
    List<RestaurantModel> lstRestaurant;
    List<String> restaurantIds;
    private RecyclerViewAdapter recycleAdapter;
    private Context mContext;
    private FirebaseFirestore db;
    private String type;
    FirebaseAuth firebaseAuth;
    String userId;



    public FragmentList(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_fragment, container, false);
        mContext = getContext();

        assert getArguments() != null;
        //String type = getArguments().getString("type");
        type = getArguments().getString("type");
        assert type != null;
        restaurantNames = new ArrayList<>();

        lstRestaurant = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getUid();

        myRecyclerView = v.findViewById(R.id.restaurant_recyclerview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.addItemDecoration(new DividerItemDecoration(myRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(myRecyclerView);

        db = FirebaseFirestore.getInstance();
        loadDataFromFirebase("");


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Toast.makeText(mContext, "Swiped worked", Toast.LENGTH_SHORT);
        }
    };

    public void onLongItemClick(final int position){

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
            b.putSerializable("RestaurantObject", lstRestaurant.get(position));
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
                                    RestaurantModel res =  querySnapshot.toObject(RestaurantModel.class);

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
      
}

