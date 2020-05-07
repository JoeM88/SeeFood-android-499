package com.example.seefood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.seefood.favorites.FragmentFavorite;
import com.example.seefood.restaurantList.FragmentList;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentHome extends Fragment {

    private static final String TAG = "";
    private static final int PERMISSION_ID = 711;
    private View view;
    private Context mContext;
    private String APIkey = "@hidden_strings/apiKey"; // PLEASE CHANGE WHEN APP GOES OUT OTHERWISE I JUST GAVE AWAY AN API KEY
    private double latitude = 36.683; // currently set to marina, but if you change these values it shows the different local restaurants
    private double longitude = -121.798;
    @BindView(R.id.search_input) EditText searchText;
    @BindView(R.id.search_button) ImageButton searchBtn;
    @BindView(R.id.near_me_button) Button nearMeButton;
    @BindView(R.id.favorites_button) Button favoritesButton;
    PlacesClient placesClient;
    FusedLocationProviderClient mFusedLocationClient;

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
        //FragmentTransaction ft = getFragmentManager().beginTransaction(); Probably dont need

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        searchBtn.setOnClickListener(view -> {
            String searchedObj = searchText.getText().toString();
            if(searchedObj.length() == 0){
                return;
            }
            else {
                Bundle b = new Bundle();
                b.putString("type", searchedObj);
                FragmentList fl = new FragmentList();
                fl.setArguments(b);
                //ft.replace(R.id.container_fragment, fl); Probably don't need this anymore
                //ft.commit();
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.switchContent(R.id.container_fragment, fl, true);
            }
        });

        nearMeButton.setOnClickListener(view -> {
            getLastLocation();
            // Test numbers for the moment
            String address = getAddressFromLocation(latitude, longitude);
            Bundle b = new Bundle();
            b.putString("type", address);
            FragmentList fl = new FragmentList();
            fl.setArguments(b);
            MainActivity mainActivity = (MainActivity) mContext;
            mainActivity.switchContent(R.id.container_fragment, fl);
        });

        favoritesButton.setOnClickListener(view -> {
            FragmentFavorite ff = new FragmentFavorite();
            ff.setArguments(null);
            MainActivity mainActivity = (MainActivity) mContext;
            mainActivity.switchContent(R.id.container_fragment, ff);
        });

        return view;
    }

    private boolean checkPermissions() {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] perms, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, perms, grantResults);
        if (requestCode == PERMISSION_ID){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Permission granted, we are ready for takeoff
            }
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()){
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        task -> {
                            Location location = task.getResult();
                            if (location == null) {
                                requestNewLocationData();
                            }
                            else {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                );
            }
            else {
                Toast.makeText(getActivity(), "Turn on Location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    private LocationCallback mLocationCallback = new LocationCallback(){
      public void onLocationResult(LocationResult locationResult){
          Location mLastLocation = locationResult.getLastLocation();
          latitude = mLastLocation.getLatitude();
          longitude = mLastLocation.getLongitude();
      }
    };

    private String getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
        String address = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getLocality();
        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Could not get address", Toast.LENGTH_LONG).show();
        }
        return address;
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
            mainActivity.switchContent(R.id.container_fragment, frag, true);
        }
    }

}
