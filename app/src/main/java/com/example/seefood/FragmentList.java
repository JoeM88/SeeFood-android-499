package com.example.seefood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class FragmentList extends Fragment {


    private View v;
    private RecyclerView myRecyclerView;
    private List<Restaurant> lstRestaurant;



    public FragmentList(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_fragment, container, false);

        myRecyclerView = v.findViewById(R.id.restaurant_recyclerview);
        RecyclerViewAdapter recycleAdapter = new RecyclerViewAdapter(getContext(), lstRestaurant);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recycleAdapter);
        myRecyclerView.addItemDecoration(new DividerItemDecoration(myRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String name, String address, int rating, double distance, int numReviews, String category
        lstRestaurant = new ArrayList<>();
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));

    }
}
