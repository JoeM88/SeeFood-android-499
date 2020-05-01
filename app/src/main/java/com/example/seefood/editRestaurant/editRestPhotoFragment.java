package com.example.seefood.editRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.seefood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class editRestPhotoFragment extends Fragment {

    public editRestPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_rest_photo, container, false);
    }
}
