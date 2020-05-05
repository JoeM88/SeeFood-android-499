package com.example.seefood.restaurantDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.R;
import com.example.seefood.models.MealModel;
import com.example.seefood.models.RestaurantModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FragmentRestaurantDetails extends Fragment {

    private View v;
    private TextView detailsName;
    private TextView detailsAddress;
    private ImageView detailsCirclePhotoURL;
    private ImageView detailsBannerPhotoURL;

    private RecyclerView myRecyclerView;

    private List<Offering> lstMeals;
    private OfferingAdapter mealRecycleAdapter;


    public FragmentRestaurantDetails(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.restaurant_details_fragment, container, false);

        assert getArguments() != null;
        RestaurantModel obj = getArguments().getParcelable("RestaurantObject");
        assert obj != null;
        lstMeals = new ArrayList<>();
        ArrayList<MealModel> arr = new ArrayList<>();

        detailsName = v.findViewById(R.id.Restaurant_Details_Name);
        detailsAddress = v.findViewById(R.id.Restaurant_Details_Address);
        detailsCirclePhotoURL = v.findViewById(R.id.Restaurant_Details_Circle_Photo);
        detailsBannerPhotoURL = v.findViewById(R.id.restaurantDetailsImage);

        detailsName.setText(obj.getRestName());
        detailsAddress.setText(obj.getStreetAddress());
        Picasso.get()
                .load(obj.getPhotoURL()).into(detailsCirclePhotoURL);
        Picasso.get()
                .load(obj.getPhotoURL()).into(detailsBannerPhotoURL);

        for (Map.Entry mapElement : obj.getOfferings().entrySet()) {

            if(mapElement.getValue() != null)
            {
                String offer = (String)mapElement.getKey();
                arr = (ArrayList<MealModel>) mapElement.getValue();
                System.out.println(arr.get(0).getCalories());
                Offering o = new Offering(offer, arr);
                lstMeals.add(o);
            }


        }
        mealRecycleAdapter = new OfferingAdapter(lstMeals);
        myRecyclerView = v.findViewById(R.id.detailsRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(mealRecycleAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
