package com.example.seefood.editRestaurant;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;
import com.example.seefood.displayProfiles.displayRestaurantProfile;
import com.example.seefood.models.RestaurantModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class editRestDetails extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText restName;
    EditText address;
    EditText city;
    Spinner stateSpinner;
    EditText zipCode;
    EditText phone;
    String stringStateSpinner;

    Bundle bundle;
    RestaurantModel dispRest;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;

    Button updateButton;

    public editRestDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_rest_details, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        restName = view.findViewById(R.id.RestName_Input);
        address = view.findViewById(R.id.Street_Input);
        city = view.findViewById(R.id.CityText_Input);
        zipCode = view.findViewById(R.id.ZipText_Input);
        phone = view.findViewById(R.id.PhoneText_Input);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        updateButton = view.findViewById(R.id.nextButton);

        bundle = getArguments();
        dispRest = bundle.getParcelable("restaurant");

        restName.setText(dispRest.getRestName());
        address.setText(dispRest.getStreetAddress());
        city.setText(dispRest.getCity());

        zipCode.setText(dispRest.getZipCode());
        phone.setText(dispRest.getPhoneNumber());

        stateSpinner = view.findViewById(R.id.StateText_Input);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.states_array, android.R.layout.simple_spinner_item);
        adapter. setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        stateSpinner.setOnItemSelectedListener(this);

        stateSpinner.setSelection(findIndex(stateSpinner, dispRest.getState()));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()){
                    db.collection("Restaurants").document(Objects.requireNonNull(firebaseAuth.getUid())).set(dispRest);
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment nextStep = new displayRestaurantProfile();
                    ft.replace(R.id.container_fragment, nextStep);
                    ft.commit();
                } else {
                    Toast.makeText(getContext(), "Error: Form Incomplete", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        restName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                restName.setText("");
//            }
//        });
        restName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                restName.setText(null);
                return false;
            }
        });

        return view;
    }


    private Boolean checkInput(){
        if(!restName.getText().toString().trim().equals("") && !address.getText().toString().trim().equals("") && !city.getText().toString().trim().equals("") && !stringStateSpinner.equals("Select State...")
                && !zipCode.getText().toString().trim().equals("") && !phone.getText().toString().trim().equals("")){
            dispRest.setRestName(restName.getText().toString());
            dispRest.setStreetAddress(address.getText().toString());
            dispRest.setCity(city.getText().toString());
            dispRest.setState(stringStateSpinner);
            dispRest.setZipCode(zipCode.getText().toString());
            dispRest.setPhoneNumber(phone.getText().toString());

            return true;
        }
        return false;
    }

    public int findIndex(Spinner spinner, String statename){
        int selectionIndex = 0;
        for(int i=0; i<spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).equals(statename)){
                selectionIndex = i;
            }
        }
        return selectionIndex;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stringStateSpinner = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        stringStateSpinner = dispRest.getState();
    }
}
