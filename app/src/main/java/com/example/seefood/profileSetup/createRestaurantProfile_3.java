package com.example.seefood.profileSetup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.example.seefood.models.MealModel;
import com.example.seefood.models.OperationsModel;
import com.example.seefood.models.RestaurantModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class createRestaurantProfile_3 extends Fragment implements AdapterView.OnItemSelectedListener {

    public createRestaurantProfile_3() {
        // Required empty public constructor
    }

    private Boolean Monday = false;
    private Boolean Tuesday = false;
    private Boolean Wednesday = false;
    private Boolean Thursday = false;
    private Boolean Friday = false;
    private Boolean Saturday = false;
    private Boolean Sunday = false;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button monButton;
    Button tueButton;
    Button wedButton;
    Button thuButton;
    Button friButton;
    Button satButton;
    Button sunButton;

    Button notOffered;
    Button setHours;

    Spinner hourSpinner;
    Spinner minSpinner;
    Spinner ampamSpinner;

    Spinner hourEnd;
    Spinner minEnd;
    Spinner ampamEnd;

    String startHours;
    String startMinutes;
    String startHalf;

    String endHours;
    String endMinutes;
    String endHalf;

    TextView mondayList;
    TextView tuesdayList;
    TextView wednesdayList;
    TextView thursdayList;
    TextView fridayList;
    TextView saturdayList;
    TextView sundayList;

    Button goLunch;
    Button goDinner;
    Button completeSetup;
    Button resetButton;

    String controllerString;
    TextView settingThis;

//    TextView myText;
//    TextView myText2;
//    TextView myText3;
//    TextView myText4;
//    TextView myText5;
//    TextView myText6;
//    TextView myText7;


    Bundle bundle;

RestaurantModel rm = new RestaurantModel();
HashMap<String, HashMap<String, OperationsModel>> hoursOperation;
//HashMap<String, OperationsModel> mondayMap;
//HashMap<String, OperationsModel> tuesdayMap;
//HashMap<String, OperationsModel> wednesdayMap;
//HashMap<String, OperationsModel> thursdayMap;
//HashMap<String, OperationsModel> fridayMap;
//HashMap<String, OperationsModel> saturdayMap;
//HashMap<String, OperationsModel> sundayMap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_restaurant_profile_3, container, false);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Restaurants");

        controllerString = "Breakfast";
        settingThis = view.findViewById(R.id.settingThis);

        monButton = view.findViewById(R.id.mondayButton);
        tueButton = view.findViewById(R.id.tuesdayButton);
        wedButton = view.findViewById(R.id.wednesdayButton);
        thuButton = view.findViewById(R.id.thursdayButton);
        friButton = view.findViewById(R.id.fridayButton);
        satButton = view.findViewById(R.id.saturdayButton);
        sunButton = view.findViewById(R.id.sundayButton);

        setHours = view.findViewById(R.id.setHoursButton);
        notOffered = view.findViewById(R.id.notOffered);
        resetButton = view.findViewById(R.id.resetButtons);

        goLunch = view.findViewById(R.id.goLunch);
        goDinner = view.findViewById(R.id.goDinner);
        completeSetup = view.findViewById(R.id.completeSetup);
        goDinner.setVisibility(View.GONE);
        completeSetup.setVisibility(View.GONE);

        mondayList = view.findViewById(R.id.MondayList);
        tuesdayList = view.findViewById(R.id.TuesdayList);
        wednesdayList = view.findViewById(R.id.WednesdayList);
        thursdayList = view.findViewById(R.id.ThursdayList);
        fridayList = view.findViewById(R.id.FridayList);
        saturdayList = view.findViewById(R.id.SaturdayList);
        sundayList = view.findViewById(R.id.SundayList);


        hoursOperation = new HashMap<String, HashMap<String, OperationsModel>>();
        hoursOperation.put("Monday", mealOptions());
        hoursOperation.put("Tuesday", mealOptions());
        hoursOperation.put("Wednesday", mealOptions());
        hoursOperation.put("Thursday", mealOptions());
        hoursOperation.put("Friday", mealOptions());
        hoursOperation.put("Saturday", mealOptions());
        hoursOperation.put("Sunday", mealOptions());


        monButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        monButton.setTextColor(Color.RED);
        tueButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        tueButton.setTextColor(Color.RED);
        wedButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        wedButton.setTextColor(Color.RED);
        thuButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        thuButton.setTextColor(Color.RED);
        friButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        friButton.setTextColor(Color.RED);
        satButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        satButton.setTextColor(Color.RED);
        sunButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        sunButton.setTextColor(Color.RED);

        bundle = getArguments();

        rm.setRestName(bundle.getString("name"));
        rm.setStreetAddress(bundle.getString("address"));
        rm.setCity(bundle.getString("city"));
        rm.setState(bundle.getString("state"));
        rm.setZipCode(bundle.getString("zipCode"));
        rm.setPhoneNumber(bundle.getString("phone"));
        rm.setPhotoURL(bundle.getString("photoURL"));
        rm.setPhotoName(bundle.getString("photoName"));

        hourSpinner = view.findViewById(R.id.hourSpinner);
        ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(getContext(), R.array.hours, android.R.layout.simple_spinner_item);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);
        hourSpinner.setOnItemSelectedListener(this);
        //hourSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        minSpinner = view.findViewById(R.id.minSpinner);
        ArrayAdapter<CharSequence> minAdapter = ArrayAdapter.createFromResource(getContext(), R.array.minutes, android.R.layout.simple_spinner_item);
        minAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minSpinner.setAdapter(minAdapter);
        minSpinner.setOnItemSelectedListener(this);

        ampamSpinner = view.findViewById(R.id.ampam);
        ArrayAdapter<CharSequence> ampamAdapter = ArrayAdapter.createFromResource(getContext(), R.array.dayNight, android.R.layout.simple_spinner_item);
        ampamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ampamSpinner.setAdapter(ampamAdapter);
        ampamSpinner.setOnItemSelectedListener(this);
        //rmDetails.setText(rm.printRest(rm));
        //purl.setText(url);

        hourEnd = view.findViewById(R.id.hourSpinner2);
        ArrayAdapter<CharSequence> hourAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.hours, android.R.layout.simple_spinner_item);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourEnd.setAdapter(hourAdapter2);
        hourEnd.setOnItemSelectedListener(this);

        minEnd = view.findViewById(R.id.minSpinner2);
        ArrayAdapter<CharSequence> minAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.minutes, android.R.layout.simple_spinner_item);
        minAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minEnd.setAdapter(minAdapter2);
        minEnd.setOnItemSelectedListener(this);

        ampamEnd = view.findViewById(R.id.ampam2);
        ArrayAdapter<CharSequence> ampamAdapter2 = ArrayAdapter.createFromResource(getContext(), R.array.dayNight, android.R.layout.simple_spinner_item);
        ampamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ampamEnd.setAdapter(ampamAdapter2);
        ampamEnd.setOnItemSelectedListener(this);

        notOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Monday == true){
                    String day = "Monday";
                    hoursOperation.get(day).put(controllerString, new OperationsModel());
                    mondayList.setText("CLOSED");
                } if(Tuesday == true) {
                    String day = "Tuesday";
                    hoursOperation.get(day).put(controllerString, new OperationsModel());
                    tuesdayList.setText("CLOSED");
                } if(Wednesday == true) {
                    String day = "Wednesday";
                    hoursOperation.get(day).put(controllerString, new OperationsModel());
                    wednesdayList.setText("CLOSED");
                } if(Thursday == true) {
                    String day = "Thursday";
                    hoursOperation.get(day).put(controllerString, new OperationsModel());
                    thursdayList.setText("CLOSED");
                } if(Friday == true) {
                    String day = "Friday";
                    hoursOperation.get(day).put(controllerString, new OperationsModel());
                    fridayList.setText("CLOSED");
                } if(Saturday == true) {
                    String day = "Saturday";
                    hoursOperation.get(day).put(controllerString, new OperationsModel());
                    saturdayList.setText("CLOSED");
                } if(Sunday == true) {
                    String day = "Sunday";
                    hoursOperation.get(day).put(controllerString, new OperationsModel());
                    sundayList.setText("CLOSED");
                }
            }
        });

        setHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String set = startHours + ":" + startMinutes + startHalf + "--" + endHours + ":" + endMinutes + endHalf;

                int intStartHour;
                int intStartMinute;
                int intEndHour;
                int intEndMinute;

                if(startHalf.equals("PM")){
                    if(Integer.parseInt(startHours) == 12){
                        intStartHour = Integer.parseInt(startHours);
                        intStartMinute = Integer.parseInt(startMinutes);
                    } else {
                        intStartHour = Integer.parseInt(startHours) + 12;
                        intStartMinute = Integer.parseInt(startMinutes);
                    }
                } else {
                    intStartHour = Integer.parseInt(startHours);
                    intStartMinute = Integer.parseInt(startMinutes);
                }
                if(endHalf.equals("PM")){
                    if(Integer.parseInt(endHours) == 12){
                        intEndHour = Integer.parseInt(endHours);
                        intEndMinute = Integer.parseInt(endMinutes);
                    } else {
                        intEndHour = Integer.parseInt(endHours) + 12;
                        intEndMinute = Integer.parseInt(endMinutes);
                    }
                } else {
                    if(Integer.parseInt(startHours) == 12){
                        intStartHour = 0;
                        intStartMinute = Integer.parseInt(startMinutes);
                    } else {
                        intEndHour = Integer.parseInt(endHours);
                        intEndMinute = Integer.parseInt(endMinutes);
                    }
                    if(Integer.parseInt(endHours) == 12){
                        intEndHour = 0;
                        intEndMinute = Integer.parseInt(endMinutes);
                    } else {
                        intEndHour = Integer.parseInt(endHours);
                        intEndMinute = Integer.parseInt(endMinutes);
                    }
                }

                if(Monday == true){
                    String day = "Monday";
                    mondayList.setText(set);
                    hoursOperation.get(day).get(controllerString).setOpenHour(intStartHour);
                    hoursOperation.get(day).get(controllerString).setOpenMins(intStartMinute);
                    hoursOperation.get(day).get(controllerString).setDurHours(intEndHour);
                    hoursOperation.get(day).get(controllerString).setDurMins(intEndMinute);
                } if(Tuesday == true){
                    String day = "Tuesday";
                    hoursOperation.get(day).get(controllerString).setOpenHour(intStartHour);
                    hoursOperation.get(day).get(controllerString).setOpenMins(intStartMinute);
                    hoursOperation.get(day).get(controllerString).setDurHours(intEndHour);
                    hoursOperation.get(day).get(controllerString).setDurMins(intEndMinute);
                    tuesdayList.setText(set);
                } if(Wednesday == true){
                    String day = "Wednesday";
                    wednesdayList.setText(set);
                    hoursOperation.get(day).get(controllerString).setOpenHour(intStartHour);
                    hoursOperation.get(day).get(controllerString).setOpenMins(intStartMinute);
                    hoursOperation.get(day).get(controllerString).setDurHours(intEndHour);
                    hoursOperation.get(day).get(controllerString).setDurMins(intEndMinute);
                } if(Thursday == true){
                    String day = "Thursday";
                    thursdayList.setText(set);
                    hoursOperation.get(day).get(controllerString).setOpenHour(intStartHour);
                    hoursOperation.get(day).get(controllerString).setOpenMins(intStartMinute);
                    hoursOperation.get(day).get(controllerString).setDurHours(intEndHour);
                    hoursOperation.get(day).get(controllerString).setDurMins(intEndMinute);
                } if(Friday == true){
                    String day = "Friday";
                    fridayList.setText(set);
                    hoursOperation.get(day).get(controllerString).setOpenHour(intStartHour);
                    hoursOperation.get(day).get(controllerString).setOpenMins(intStartMinute);
                    hoursOperation.get(day).get(controllerString).setDurHours(intEndHour);
                    hoursOperation.get(day).get(controllerString).setDurMins(intEndMinute);
                } if(Saturday == true){
                    String day = "Saturday";
                    saturdayList.setText(set);
                    hoursOperation.get(day).get(controllerString).setOpenHour(intStartHour);
                    hoursOperation.get(day).get(controllerString).setOpenMins(intStartMinute);
                    hoursOperation.get(day).get(controllerString).setDurHours(intEndHour);
                    hoursOperation.get(day).get(controllerString).setDurMins(intEndMinute);
                } if(Sunday == true){
                    String day = "Sunday";
                    sundayList.setText(set);
                    hoursOperation.get(day).get(controllerString).setOpenHour(intStartHour);
                    hoursOperation.get(day).get(controllerString).setOpenMins(intStartMinute);
                    hoursOperation.get(day).get(controllerString).setDurHours(intEndHour);
                    hoursOperation.get(day).get(controllerString).setDurMins(intEndMinute);
                }
                //String set = startHours + ":" + startMinutes + startHalf + "--" + endHours + ":" + endMinutes + endHalf;
                //mondayList.setText(set);
            }
        });

        goLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLunch.setVisibility(View.GONE);
                goDinner.setVisibility(View.VISIBLE);
                controllerString = "Lunch";
                settingThis.setText("Lunch Hours");
                clearAllDays();
            }
        });

        goDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDinner.setVisibility(View.GONE);
                completeSetup.setVisibility(View.VISIBLE);
                controllerString = "Dinner";
                settingThis.setText("Dinner Hours");
                clearAllDays();
            }
        });
        
        completeSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFinalObject();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDinner.setVisibility(View.GONE);
                completeSetup.setVisibility(View.GONE);
                goLunch.setVisibility(View.VISIBLE);
                controllerString = "Breakfast";
                settingThis.setText("Breakfast Hours");
                clearAllDays();
            }
        });

        monButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //monButton.setBackgroundColor(red);
                Monday = !Monday;
                if(Monday == false)
                {
                    //myText.setText("FALSE");
                    monButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    monButton.setTextColor(Color.RED);
                    //monButton.setBackgroundColor(material_blue_grey_800);
                } else {
                    //myText.setText("TRUE");
                    monButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    monButton.setTextColor(Color.WHITE);
                    //monButton.setBackgroundColor(red);
                }
                //changeActive(monButton, Monday);
            }
        });

        tueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeActive(tueButton, Tuesday);
                Tuesday = !Tuesday;
                if(Tuesday == false)
                {
                    //myText2.setText("FALSE");
                    tueButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    tueButton.setTextColor(Color.RED);
                    //monButton.setBackgroundColor(material_blue_grey_800);
                } else {
                    //myText2.setText("TRUE");
                    tueButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    tueButton.setTextColor(Color.WHITE);
                    //monButton.setBackgroundColor(red);
                }
            }
        });

        wedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeActive(wedButton, Wednesday);
                Wednesday = !Wednesday;
                if(Wednesday == false)
                {
                    //myText3.setText("FALSE");
                    wedButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    wedButton.setTextColor(Color.RED);
                    //monButton.setBackgroundColor(material_blue_grey_800);
                } else {
                    //myText3.setText("TRUE");
                    wedButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    wedButton.setTextColor(Color.WHITE);
                    //monButton.setBackgroundColor(red);
                }
            }
        });

        thuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeActive(thuButton, Thursday);
                Thursday = !Thursday;
                if(Thursday == false)
                {
                    //myText4.setText("FALSE");
                    thuButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    thuButton.setTextColor(Color.RED);
                    //monButton.setBackgroundColor(material_blue_grey_800);
                } else {
                    //myText4.setText("TRUE");
                    thuButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    thuButton.setTextColor(Color.WHITE);
                    //monButton.setBackgroundColor(red);
                }
            }
        });

        friButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeActive(friButton, Friday);
                Friday = !Friday;
                if(Friday == false)
                {
                    //myText5.setText("FALSE");
                    friButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    friButton.setTextColor(Color.RED);
                    //monButton.setBackgroundColor(material_blue_grey_800);
                } else {
                    //myText5.setText("TRUE");
                    friButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    friButton.setTextColor(Color.WHITE);
                    //monButton.setBackgroundColor(red);
                }
            }
        });

        satButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeActive(satButton, Saturday);
                Saturday = !Saturday;
                if(Saturday == false)
                {
                    //myText6.setText("FALSE");
                    satButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    satButton.setTextColor(Color.RED);
                    //monButton.setBackgroundColor(material_blue_grey_800);
                } else {
                    //myText6.setText("TRUE");
                    satButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    satButton.setTextColor(Color.WHITE);
                    //monButton.setBackgroundColor(red);
                }
            }
        });

        sunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeActive(sunButton, Sunday);
                Sunday = !Sunday;
                if(Sunday == false)
                {
                    //myText7.setText("FALSE");
                    sunButton.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    sunButton.setTextColor(Color.RED);
                    //monButton.setBackgroundColor(material_blue_grey_800);
                } else {
                    //myText7.setText("TRUE");
                    sunButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    sunButton.setTextColor(Color.WHITE);
                    //monButton.setBackgroundColor(red);
                }
            }
        });


        return view;
    }

    private void createFinalObject() {
        HashMap<String, ArrayList<MealModel>> offerings = new HashMap<String, ArrayList<MealModel>>();
        offerings.put("Breakfast", new ArrayList<MealModel>());
        offerings.put("Lunch", new ArrayList<MealModel>());
        offerings.put("Dinner", new ArrayList<MealModel>());
        offerings.put("Dessert", new ArrayList<MealModel>());
        rm.setOfferings(offerings);
        rm.sethOps(hoursOperation);
        rm.setOwner(bundle.getString("owner"));
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();
        mDatabaseRef.child(uid).setValue(rm);
        db.collection("Restaurants").document(firebaseAuth.getUid()).set(rm);

        goHome(null);

    }

    private void goHome(View view){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    private void clearAllDays() {
        mondayList.setText("");
        tuesdayList.setText("");
        wednesdayList.setText("");
        thursdayList.setText("");
        fridayList.setText("");
        saturdayList.setText("");
        sundayList.setText("");
    }

    public HashMap<String, OperationsModel> mealOptions(){
        HashMap<String, OperationsModel> temp = new HashMap<String, OperationsModel>();
        temp.put("Breakfast", new OperationsModel());
        temp.put("Lunch", new OperationsModel());
        temp.put("Dinner", new OperationsModel());
        temp.put("Dessert", new OperationsModel());
        return temp;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent == hourSpinner){
//            String stringStateSpinner = parent.getItemAtPosition(position).toString();
//            String result = "HOUR --> " + stringStateSpinner;
//            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
            startHours = parent.getItemAtPosition(position).toString();
        } else if(parent == minSpinner){
//            String stringStateSpinner = parent.getItemAtPosition(position).toString();
//            String result = "MINUTE --> " + stringStateSpinner;
//            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
            startMinutes = parent.getItemAtPosition(position).toString();
        } else if(parent == ampamSpinner){
//            String stringStateSpinner = parent.getItemAtPosition(position).toString();
//            String result = "AM/PM --> " + stringStateSpinner;
//            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
            startHalf = parent.getItemAtPosition(position).toString();
        } else if(parent == hourEnd){
            endHours = parent.getItemAtPosition(position).toString();
        } else if(parent == minEnd){
            endMinutes = parent.getItemAtPosition(position).toString();
        } else if(parent == ampamEnd){
            endHalf = parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}