package com.example.seefood;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class FragmentList extends Fragment implements RecyclerViewAdapter.OnRestaurantListener{


    private View v;
    private RecyclerView myRecyclerView;
    private List<Restaurant> lstRestaurant;
    private RecyclerViewAdapter recycleAdapter;
    private Context mContext;


    public FragmentList(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_fragment, container, false);

        myRecyclerView = v.findViewById(R.id.restaurant_recyclerview);
        recycleAdapter = new RecyclerViewAdapter(getContext(), lstRestaurant, this);
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
        lstRestaurant.add(new Restaurant("Burger King", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("Pizza Hut", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("Carls Jr", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("Pizaa factory", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("Blaze", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("Chipotle", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("Deli Delicious", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("Starbucks", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));
        lstRestaurant.add(new Restaurant("ChopStix", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds ));

    }

    @Override
    public void onRestaurantClick(int position) {
        mContext = getContext();
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


}
