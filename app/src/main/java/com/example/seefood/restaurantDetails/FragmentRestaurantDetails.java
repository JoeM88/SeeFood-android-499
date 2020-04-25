package com.example.seefood.restaurantDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.R;
import com.example.seefood.restaurantList.Restaurant;

import java.util.List;
import static com.example.seefood.restaurantDetails.MealsData.makeMeals;

public class FragmentRestaurantDetails extends Fragment {

    private View v;
    private TextView detailsName;
    private TextView detailsCategory;
    private RecyclerView myRecyclerView;
    private List<Meals> lstMeals;
    private MealsAdapter mealRecycleAdapter;


    public FragmentRestaurantDetails(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.restaurant_details_fragment, container, false);
        assert getArguments() != null;
        Restaurant obj = getArguments().getParcelable("RestaurantObject");
        assert obj != null;
        detailsName = v.findViewById(R.id.Restaurant_Details_Name);
        detailsName.setText(obj.getName());
        detailsCategory = v.findViewById(R.id.Restaurant_Details_Category);
        detailsCategory.setText(obj.getCategory());

        mealRecycleAdapter = new MealsAdapter(lstMeals);
        myRecyclerView = v.findViewById(R.id.detailsRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(mealRecycleAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstMeals = makeMeals();
    }

}
