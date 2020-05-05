package com.example.seefood.editRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.R;
import com.example.seefood.displayProfiles.displayRestaurantProfile;
import com.example.seefood.models.MealModel;
import com.example.seefood.models.RestaurantModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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
    Button stepBack;

    Bundle bundle;

    String viewController;

    TextView tv;
    String uid;

    private RecyclerView editRecycler;
    private EditRecyclerViewAdapter editAdapter;
    private ArrayList<MealModel> mealModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_rest_menu_items, container, false);
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        mealModelArrayList = new ArrayList<MealModel>();
        //tv = v.findViewById(R.id.showDetails);
        newItem = v.findViewById(R.id.newItem);
        stepBack = v.findViewById(R.id.displayRestProfile);
        editRecycler = v.findViewById(R.id.menu_management_recycler);

        editRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        editRecycler.addItemDecoration(new DividerItemDecoration(editRecycler.getContext(), DividerItemDecoration.VERTICAL));

        bundle = getArguments();
        assert bundle != null;
        dispRest = bundle.getParcelable("restaurant");
        //mealModelArrayList.add(dispRest.getOfferings().get("Breakfast").get(0));
        String viewController = bundle.getString("viewController");
        assert dispRest != null;
        mealModelArrayList = dispRest.getOfferings().get(viewController);
        editAdapter = new EditRecyclerViewAdapter(mealModelArrayList);
        editRecycler.setAdapter(editAdapter);

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

        stepBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new displayRestaurantProfile();
                ft.replace(R.id.container_fragment, nextStep);
                ft.commit();
                //Toast.makeText(getContext(), mealModelArrayList.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

}
