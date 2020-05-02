package com.example.seefood.editRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;
import com.example.seefood.models.RestaurantModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
        assert bundle != null;
        dispRest = bundle.getParcelable("restaurant");
        tv.setText(dispRest.printRest(dispRest));
        //tv.append(dispRest.getOfferings().get("Breakfast").toString());
//        ArrayList<MealModel> templist = new ArrayList<MealModel>();
//        templist = dispRest.getOfferings().get("Breakfast");
//        tv.append(templist.toString());
        tv.append(dispRest.getOfferings().get("Breakfast").get(0).getName());

        /*passForward.putString("viewController", "Breakfast");
                passForward.putSerializable("restaurant", dispRest);*/

        //dispRest = (RestaurantModel) bundle.getSerializable("dispRest");

        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("type", "newItem");
                bundle.putParcelable("restaurant", dispRest);
                Fragment nextStep = new editMenuFragment();
                nextStep.setArguments(bundle);
                ft.replace(R.id.container_fragment, nextStep);
                ft.commit();
            }
        });

        return v;
    }

}
