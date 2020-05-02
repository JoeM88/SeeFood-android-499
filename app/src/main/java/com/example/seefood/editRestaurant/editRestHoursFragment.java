package com.example.seefood.editRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.seefood.R;
import com.example.seefood.restaurantList.Restaurant;


/**
 * A simple {@link Fragment} subclass.
 */
public class editRestHoursFragment extends Fragment {

    public editRestHoursFragment() {
        // Required empty public constructor
    }

    Restaurant dispRest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_rest_hours, container, false);

        return v;
    }
}
