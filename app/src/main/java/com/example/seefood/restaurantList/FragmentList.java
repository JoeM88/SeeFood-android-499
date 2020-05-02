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

import com.example.seefood.models.RestaurantModel;
import com.example.seefood.restaurantDetails.FragmentRestaurantDetails;
import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
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
//        assert getArguments() != null;
//        String type = getArguments().getString("type");
//        assert type != null;
        restaurantNames = new ArrayList<>();
        lstRestaurant = new ArrayList<>();
        myRecyclerView = v.findViewById(R.id.restaurant_recyclerview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.addItemDecoration(new DividerItemDecoration(myRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        db = FirebaseFirestore.getInstance();
        loadDataFromFirebase("");


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




//        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", "https://www.mcdonalds.com/content/dam/uk/logo/logo-80.png" ));
//        lstRestaurant.add(new Restaurant("Burger King", "123 Main St.", 3, 21.5, 45, "fastfood", "https://pbs.twimg.com/profile_images/1229180816435142660/MLoubJPL_400x400.jpg" ));
//        lstRestaurant.add(new Restaurant("Pizza Hut", "123 Main St.", 3, 21.5, 45, "fastfood", "https://upload.wikimedia.org/wikipedia/sco/thumb/d/d2/Pizza_Hut_logo.svg/1200px-Pizza_Hut_logo.svg.png" ));
//        lstRestaurant.add(new Restaurant("Carls Jr", "123 Main St.", 3, 21.5, 45, "fastfood", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Carls_logo_%281%29.png/245px-Carls_logo_%281%29.png" ));
//        lstRestaurant.add(new Restaurant("Pizaa factory", "123 Main St.", 3, 21.5, 45, "fastfood", "https://upload.wikimedia.org/wikipedia/en/a/a2/Pizza_Factory_logo.png" ));
//        lstRestaurant.add(new Restaurant("Blaze", "123 Main St.", 3, 21.5, 45, "fastfood", "https://www.alshaya.com/images/portfolio_logo/english/logo_blazepizza.jpg"));
//        lstRestaurant.add(new Restaurant("Chipotle", "123 Main St.", 3, 21.5, 45, "fastfood", "https://upload.wikimedia.org/wikipedia/en/thumb/3/3b/Chipotle_Mexican_Grill_logo.svg/220px-Chipotle_Mexican_Grill_logo.svg.png" ));
//        lstRestaurant.add(new Restaurant("Starbucks", "123 Main St.", 3, 21.5, 45, "fastfood","https://pbs.twimg.com/profile_images/1109148609218412545/XDVmdQm9_400x400.png" ));
//        lstRestaurant.add(new Restaurant("ChopStix", "123 Main St.", 3, 21.5, 45, "fastfood", "https://cdn.doordash.com/media/restaurant/cover/ChopstixMilwaukee_1820_Milwaukee_WI.png"));

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

//        switch (type)
//        {
//            case("All"):
                db.collection("Restaurants")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(DocumentSnapshot querySnapshot : task.getResult()){
                                    //String restName, String owner, String streetAddress, String state, String zipCode,
                                    //                           String city, String phoneNumber, HashMap<String, ArrayList<MealModel>> offerings,
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


//}
