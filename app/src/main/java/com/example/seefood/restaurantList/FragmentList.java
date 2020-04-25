package com.example.seefood.restaurantList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.restaurantDetails.FragmentRestaurantDetails;
import com.example.seefood.MainActivity;
import com.example.seefood.R;

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
        lstRestaurant.add(new Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", "https://www.mcdonalds.com/content/dam/uk/logo/logo-80.png" ));
        lstRestaurant.add(new Restaurant("Burger King", "123 Main St.", 3, 21.5, 45, "fastfood", "https://pbs.twimg.com/profile_images/1229180816435142660/MLoubJPL_400x400.jpg" ));
        lstRestaurant.add(new Restaurant("Pizza Hut", "123 Main St.", 3, 21.5, 45, "fastfood", "https://upload.wikimedia.org/wikipedia/sco/thumb/d/d2/Pizza_Hut_logo.svg/1200px-Pizza_Hut_logo.svg.png" ));
        lstRestaurant.add(new Restaurant("Carls Jr", "123 Main St.", 3, 21.5, 45, "fastfood", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Carls_logo_%281%29.png/245px-Carls_logo_%281%29.png" ));
        lstRestaurant.add(new Restaurant("Pizaa factory", "123 Main St.", 3, 21.5, 45, "fastfood", "https://upload.wikimedia.org/wikipedia/en/a/a2/Pizza_Factory_logo.png" ));
        lstRestaurant.add(new Restaurant("Blaze", "123 Main St.", 3, 21.5, 45, "fastfood", "https://www.alshaya.com/images/portfolio_logo/english/logo_blazepizza.jpg"));
        lstRestaurant.add(new Restaurant("Chipotle", "123 Main St.", 3, 21.5, 45, "fastfood", "https://upload.wikimedia.org/wikipedia/en/thumb/3/3b/Chipotle_Mexican_Grill_logo.svg/220px-Chipotle_Mexican_Grill_logo.svg.png" ));
        lstRestaurant.add(new Restaurant("Starbucks", "123 Main St.", 3, 21.5, 45, "fastfood","https://pbs.twimg.com/profile_images/1109148609218412545/XDVmdQm9_400x400.png" ));
        lstRestaurant.add(new Restaurant("ChopStix", "123 Main St.", 3, 21.5, 45, "fastfood", "https://cdn.doordash.com/media/restaurant/cover/ChopstixMilwaukee_1820_Milwaukee_WI.png"));

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
