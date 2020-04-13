package com.example.seefood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayProfileFragment extends Fragment {

    public DisplayProfileFragment() {
        // Required empty public constructor
    }

    Button changeProfButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_profile, container, false);
        changeProfButton = view.findViewById(R.id.changeProfilePhotoButton);

        changeProfButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "HERE WE ARE...", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
