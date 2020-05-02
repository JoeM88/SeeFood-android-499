package com.example.seefood;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.seefood.restaurantList.FragmentList;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentHome extends Fragment {

    private static final String TAG = "";
    private static final int RESULT_OK = 1;
    private static final int RESULT_CANCELED = 2;
    private View view;
    private Context mContext;
    private AutoCompleteTextView searchInput; //Butterknife has complications with AutoCompleteTextView
    private String APIkey = "AIzaSyAnuKtCuR8_6WyQ2pNGP_JYnkYSzdF4vgU"; // PLEASE CHANGE WHEN APP GOES OUT OTHERWISE I JUST GAVE AWAY AN API KEY
    private static final int LOCATION_REQUEST = 222;
    private EditText searchText;
    private ImageButton searchBtn;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    @BindView(R.id.search_button) ImageButton searchButton;
    @BindView(R.id.near_me_button) Button nearMeButton;
    @BindView(R.id.favorites_button) Button favoritesButton;
    @BindView(R.id.recents_button) Button recentButton;

    PlacesClient placesClient;

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
        if (!Places.isInitialized()){
            Places.initialize(getActivity(), APIkey);
        }
        placesClient = Places.createClient(getActivity());
        searchBtn = view.findViewById(R.id.search_button);
        searchText = view.findViewById(R.id.search_input);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //runStuff();
            }
        });

        //Auto complete
//        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
//
//        Intent intent = new Autocomplete.IntentBuilder(
//                AutocompleteActivityMode.FULLSCREEN, fields).build(getActivity());
//        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

        return view;
    }

    // Auto complete
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Place place = Autocomplete.getPlaceFromIntent(data);
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
//            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//                // TODO: Handle the error.
//                Status status = Autocomplete.getStatusFromIntent(data);
//                Log.i(TAG, status.getStatusMessage());
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//    }

    // not working search
//    private void runStuff() {
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
//        FetchPlaceRequest request = FetchPlaceRequest.builder(searchText.getText().toString(), placeFields).build();
//        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
//            Place place = response.getPlace();
//            Log.i(TAG, "Place found: " + place.getName());
//        }).addOnFailureListener((exception) -> {
//            if (exception instanceof ApiException) {
//                ApiException apiException = (ApiException) exception;
//                int statusCode = apiException.getStatusCode();
//                Log.e(TAG, "Place not found: " + exception.getMessage());
//            }
//        });
//    }

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
