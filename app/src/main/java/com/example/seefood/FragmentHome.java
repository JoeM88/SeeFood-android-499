package com.example.seefood;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.seefood.restaurantList.FragmentList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentHome extends Fragment {

    private View view;
    private Context mContext;
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
        mContext = getContext();
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.favorites_button)
    public void switchToFavorite () {
        if(mContext == null)
        {
            return;
        }

        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity)mContext;
            Bundle b = new Bundle();
            b.putString("type", "Favorite");
            FragmentList frag = new FragmentList();
            frag.setArguments(b);
            mainActivity.switchContent(R.id.container_fragment, frag);
        }
        Toast.makeText(getActivity(), "Working", Toast.LENGTH_SHORT).show();
    }
}
