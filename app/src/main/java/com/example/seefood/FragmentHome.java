package com.example.seefood;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentHome extends Fragment {

    private View view;
    private AutoCompleteTextView searchInput; //Butterknife has complications with AutoCompleteTextView
    @BindView(R.id.search_button) ImageButton searchButton;
    @BindView(R.id.near_me_button) Button nearMeButton;
    @BindView(R.id.favorites_button) Button favoritesButton;
    @BindView(R.id.recents_button) Button recentButton;

    public FragmentHome() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.favorites_button)
    public void one() {
        Toast.makeText(getActivity(), "Working", Toast.LENGTH_SHORT).show();
    }
}
