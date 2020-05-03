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
import com.example.seefood.models.RestaurantModel;
import com.like.LikeButton;
import com.squareup.picasso.Picasso;


import java.io.Serializable;
import java.util.List;
import static com.example.seefood.restaurantDetails.MealsData.makeMeals;

public class FragmentRestaurantDetails extends Fragment {

    private View v;
    private TextView detailsName;
    private TextView detailsAddress;
    private ImageView detailsCirclePhotoURL;

    private RecyclerView myRecyclerView;
    private LikeButton starButton;

    private List<Meals> lstMeals;
    private MealsAdapter mealRecycleAdapter;


    public FragmentRestaurantDetails(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.restaurant_details_fragment, container, false);
        assert getArguments() != null;
        Serializable obj = getArguments().getSerializable("RestaurantObject");

        RestaurantModel restaurant = (RestaurantModel) obj;
        assert obj != null;
        detailsName = v.findViewById(R.id.Restaurant_Details_Name);
        detailsAddress = v.findViewById(R.id.Restaurant_Details_Address);
        detailsCirclePhotoURL = v.findViewById(R.id.Restaurant_Details_Circle_Photo);
//        detailsFavorites = v.findViewById(R.id.favorite_icon_button);

        detailsName.setText(restaurant.getRestName());
        detailsAddress.setText(restaurant.getStreetAddress());
        Picasso.get()
                .load(restaurant.getPhotoURL()).into(detailsCirclePhotoURL);

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
