package com.example.seefood.profileSetup;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;
import com.example.seefood.models.MealModel;
import com.example.seefood.models.OperationsModel;
import com.example.seefood.models.RestaurantModel;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class createRestaurantProfile_1 extends Fragment implements AdapterView.OnItemSelectedListener {

    Button nextButton;
    TextView details;
    private String thePlace;
    static String theState;
    Button setAddress;
    PlacesAutocompleteTextView placestAutocomplete;

    /* TEXT FIELDS FOR COLLECTION*/ //TODO: Replace with autocomplete
    EditText restName;
    EditText address;
    EditText city;
    Spinner stateSpinner;
    EditText zipCode;
    EditText phone;
    String stringStateSpinner;

    RestaurantModel newRest = new RestaurantModel();


    public createRestaurantProfile_1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_restaurant_profile_1, container, false);
        restName = view.findViewById(R.id.RestName_Input);
        address = view.findViewById(R.id.Street_Input);
        city = view.findViewById(R.id.CityText_Input);
        zipCode = view.findViewById(R.id.ZipText_Input);
        phone = view.findViewById(R.id.PhoneText_Input);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        stateSpinner = view.findViewById(R.id.StateText_Input);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.states_array, android.R.layout.simple_spinner_item);
        adapter. setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        stateSpinner.setOnItemSelectedListener(this);

        nextButton = view.findViewById(R.id.nextButton);
//        details = view.findViewById(R.id.place_details);
//        setAddress = view.findViewById(R.id.setAddress);
//        placestAutocomplete = view.findViewById(R.id.places_autocomplete);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });

//        placestAutocomplete.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
//            @Override
//            public void onPlaceSelected(final Place place) {
//            }
//        });
//
//        setAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                thePlace = placestAutocomplete.toString();
//                Toast.makeText(getActivity(), thePlace, Toast.LENGTH_LONG).show();
//                processPlace(thePlace);
//            }
//        });

        return view;
    }

    public void goNext(){

        if(checkInputs() == true)
        {
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
//            bundle.putSerializable("my_restaurant", (Serializable) newRest);

//            newRest.setRestName(restName.getText().toString());
//            newRest.setStreetAddress(address.getText().toString());
//            newRest.setCity(city.getText().toString());
//            newRest.setState(stringStateSpinner);
//            newRest.setZipCode(zipCode.getText().toString());
//            newRest.setPhoneNumber(phone.getText().toString());


            bundle.putString("name", restName.getText().toString());
            bundle.putString("address", address.getText().toString());
            bundle.putString("city", city.getText().toString());
            bundle.putString("state", stringStateSpinner);
            bundle.putString("zipCode", zipCode.getText().toString());
            bundle.putString("phone", phone.getText().toString());

            Fragment nextStep = new createRestaurantProfile_2();
            nextStep.setArguments(bundle);
            ft.replace(R.id.createProf_container, nextStep);
            ft.commit();
        }

//        final FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.createProf_container, new createRestaurantProfile_2());
//        ft.commit();
    }

    private Boolean checkInputs() {
//        EditText restName;
//        EditText address;
//        EditText city;
//        Spinner stateSpinner;
//        EditText zipCode;
//        EditText phone;
        if(!restName.toString().equals("") && !address.toString().equals("") && !city.toString().equals("") && !stringStateSpinner.equals("Select State...")
            && !zipCode.toString().equals("") && !phone.toString().equals("")){
            newRest.setRestName(restName.getText().toString());
            newRest.setStreetAddress(address.getText().toString());
            newRest.setCity(city.getText().toString());
            newRest.setState(stringStateSpinner);
            newRest.setZipCode(zipCode.getText().toString());
            newRest.setPhoneNumber(phone.getText().toString());
            HashMap<String, ArrayList<MealModel>> offerings = new HashMap<String, ArrayList<MealModel>>();
            offerings.put("Breakfast", new ArrayList<MealModel>());
            offerings.put("Lunch", new ArrayList<MealModel>());
            offerings.put("Dinner", new ArrayList<MealModel>());
            offerings.put("Dessert", new ArrayList<MealModel>());
            newRest.setOfferings(offerings);;
//            ArrayList<HashMap<String, HashMap<String, String>>> hoursOperation = new ArrayList<HashMap<String, HashMap<String, String>>>();
//            HashMap<String, HashMap<String, String>> temp = new HashMap<String, HashMap<String, String>>();
//            temp.put("Monday", new HashMap(){{put("open", "12:00 AM");}});
//            temp.put("Monday", new HashMap(){{put("close", "12:00 AM");}});
//            temp.put("Monday", null);
//            temp.put("Tuesday", null);
//            temp.put("Wednesday", null);
//            temp.put("Thursday", null);
//            temp.put("Friday", null);
//            temp.put("Saturday", null);
//            temp.put("Sunday", null);
            HashMap<String, HashMap<String, OperationsModel>> myOps = new HashMap<String, HashMap<String, OperationsModel>>();
            HashMap<String, OperationsModel> hours = new HashMap<String, OperationsModel>();

            hours.put("Breakfast", new OperationsModel());
            hours.put("Lunch", new OperationsModel());
            hours.put("Dinner", new OperationsModel());

            myOps.put("Monday", hours);
            myOps.put("Tuesday", hours);
            myOps.put("Wednesday", hours);
            myOps.put("Thursday", hours);
            myOps.put("Friday", hours);
            myOps.put("Saturday", hours);
            myOps.put("Sunday", hours);

            newRest.sethOps(myOps);

            String result = newRest.printRest(newRest);

            //Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();



            //TODO DELETE THE HOURS OF OPERATION
            return true;
        }
        return false; //TODO DELETE
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stringStateSpinner = parent.getItemAtPosition(position).toString();
        //Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}