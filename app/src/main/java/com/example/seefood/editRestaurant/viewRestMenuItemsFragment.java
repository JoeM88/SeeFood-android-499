package com.example.seefood.editRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
 */
public class viewRestMenuItemsFragment extends Fragment {

    public viewRestMenuItemsFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;

    RestaurantModel dispRest = null;

    Button newItem;

    Bundle bundle;

    TextView tv;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_rest_menu_items, container, false);
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        tv = v.findViewById(R.id.showDetails);
        newItem = v.findViewById(R.id.newItem);

        bundle = getArguments();

        /*passForward.putString("viewController", "Breakfast");
                passForward.putSerializable("restaurant", dispRest);*/

        //dispRest = (RestaurantModel) bundle.getSerializable("dispRest");

        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("type", "newItem");
                Fragment nextStep = new editMenuFragment();
                nextStep.setArguments(bundle);
                ft.replace(R.id.container_fragment, nextStep);
                ft.commit();
            }
        });

        getRestData();

        return v;
    }

    private void getRestData(){
        CollectionReference rp = db.collection("Restaurants");
        Query queryRestaurant = rp.whereEqualTo("owner", uid);
        queryRestaurant.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        dispRest = document.toObject(RestaurantModel.class);
                        tv.setText(dispRest.printRest(dispRest));
                    }
                }
            }
        });
    }
}
