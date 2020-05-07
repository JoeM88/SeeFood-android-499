package com.example.seefood.editRestaurant;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;
import com.example.seefood.displayProfiles.displayRestaurantProfile;
import com.example.seefood.models.OperationsModel;
import com.example.seefood.models.RestaurantModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;



/**
 * A simple {@link Fragment} subclass.
 */
public class editRestHoursFragment extends Fragment implements AdapterView.OnItemSelectedListener{

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

    Bundle bundle;

    RestaurantModel dispRest;

    public editRestHoursFragment() {
        // Required empty public constructor
    }

    //RestaurantModel dispRest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_rest_hours, container, false);

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
        assert bundle != null;
        dispRest = bundle.getParcelable("restaurant");

        hourSpinner = view.findViewById(R.id.hourSpinner);
        ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(getContext(), R.array.hours, android.R.layout.simple_spinner_item);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);
        hourSpinner.setOnItemSelectedListener(this);

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
                    dispRest.gethOps().get(day).put(controllerString, new OperationsModel());
                    mondayList.setText("CLOSED");
                } if(Tuesday == true) {
                    String day = "Tuesday";
                    dispRest.gethOps().get(day).put(controllerString, new OperationsModel());
                    tuesdayList.setText("CLOSED");
                } if(Wednesday == true) {
                    String day = "Wednesday";
                    dispRest.gethOps().get(day).put(controllerString, new OperationsModel());
                    wednesdayList.setText("CLOSED");
                } if(Thursday == true) {
                    String day = "Thursday";
                    dispRest.gethOps().get(day).put(controllerString, new OperationsModel());
                    thursdayList.setText("CLOSED");
                } if(Friday == true) {
                    String day = "Friday";
                    dispRest.gethOps().get(day).put(controllerString, new OperationsModel());
                    fridayList.setText("CLOSED");
                } if(Saturday == true) {
                    String day = "Saturday";
                    dispRest.gethOps().get(day).put(controllerString, new OperationsModel());
                    saturdayList.setText("CLOSED");
                } if(Sunday == true) {
                    String day = "Sunday";
                    dispRest.gethOps().get(day).put(controllerString, new OperationsModel());
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

                if(checkHours()){
                    if (startHalf.equals("PM")) {
                        if (Integer.parseInt(startHours) == 12) {
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
                    if (endHalf.equals("PM")) {
                        if (Integer.parseInt(endHours) == 12) {
                            intEndHour = Integer.parseInt(endHours);
                            intEndMinute = Integer.parseInt(endMinutes);
                        } else {
                            intEndHour = Integer.parseInt(endHours) + 12;
                            intEndMinute = Integer.parseInt(endMinutes);
                        }
                    } else {
                        if (Integer.parseInt(startHours) == 12) {
                            intStartHour = 0;
                            intStartMinute = Integer.parseInt(startMinutes);
                        } else {
                            intEndHour = Integer.parseInt(endHours);
                            intEndMinute = Integer.parseInt(endMinutes);
                        }
                        if (Integer.parseInt(endHours) == 12) {
                            intEndHour = 0;
                            intEndMinute = Integer.parseInt(endMinutes);
                        } else {
                            intEndHour = Integer.parseInt(endHours);
                            intEndMinute = Integer.parseInt(endMinutes);
                        }
                    }

                    if (Monday == true) {
                        String day = "Monday";
                        mondayList.setText(set);
                        dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
                        dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
                        dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
                        dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
                    }
                    if (Tuesday == true) {
                        String day = "Tuesday";
                        dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
                        dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
                        dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
                        dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
                        tuesdayList.setText(set);
                    }
                    if (Wednesday == true) {
                        String day = "Wednesday";
                        wednesdayList.setText(set);
                        dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
                        dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
                        dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
                        dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
                    }
                    if (Thursday == true) {
                        String day = "Thursday";
                        thursdayList.setText(set);
                        dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
                        dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
                        dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
                        dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
                    }
                    if (Friday == true) {
                        String day = "Friday";
                        fridayList.setText(set);
                        dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
                        dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
                        dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
                        dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
                    }
                    if (Saturday == true) {
                        String day = "Saturday";
                        saturdayList.setText(set);
                        dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
                        dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
                        dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
                        dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
                    }
                    if (Sunday == true) {
                        String day = "Sunday";
                        sundayList.setText(set);
                        dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
                        dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
                        dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
                        dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
                    }
                } else {
                    Toast.makeText(getContext(), "Incompatible Hours, Try Again", Toast.LENGTH_SHORT).show();
                }

//                if (startHalf.equals("PM")) {
//                    if (Integer.parseInt(startHours) == 12) {
//                        intStartHour = Integer.parseInt(startHours);
//                        intStartMinute = Integer.parseInt(startMinutes);
//                    } else {
//                        intStartHour = Integer.parseInt(startHours) + 12;
//                        intStartMinute = Integer.parseInt(startMinutes);
//                    }
//                } else {
//                    intStartHour = Integer.parseInt(startHours);
//                    intStartMinute = Integer.parseInt(startMinutes);
//                }
//                if (endHalf.equals("PM")) {
//                    if (Integer.parseInt(endHours) == 12) {
//                        intEndHour = Integer.parseInt(endHours);
//                        intEndMinute = Integer.parseInt(endMinutes);
//                    } else {
//                        intEndHour = Integer.parseInt(endHours) + 12;
//                        intEndMinute = Integer.parseInt(endMinutes);
//                    }
//                } else {
//                    if (Integer.parseInt(startHours) == 12) {
//                        intStartHour = 0;
//                        intStartMinute = Integer.parseInt(startMinutes);
//                    } else {
//                        intEndHour = Integer.parseInt(endHours);
//                        intEndMinute = Integer.parseInt(endMinutes);
//                    }
//                    if (Integer.parseInt(endHours) == 12) {
//                        intEndHour = 0;
//                        intEndMinute = Integer.parseInt(endMinutes);
//                    } else {
//                        intEndHour = Integer.parseInt(endHours);
//                        intEndMinute = Integer.parseInt(endMinutes);
//                    }
//                }
//
//                if (Monday == true) {
//                    String day = "Monday";
//                    mondayList.setText(set);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
//                    dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
//                    dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
//                }
//                if (Tuesday == true) {
//                    String day = "Tuesday";
//                    dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
//                    dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
//                    dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
//                    tuesdayList.setText(set);
//                }
//                if (Wednesday == true) {
//                    String day = "Wednesday";
//                    wednesdayList.setText(set);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
//                    dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
//                    dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
//                }
//                if (Thursday == true) {
//                    String day = "Thursday";
//                    thursdayList.setText(set);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
//                    dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
//                    dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
//                }
//                if (Friday == true) {
//                    String day = "Friday";
//                    fridayList.setText(set);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
//                    dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
//                    dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
//                }
//                if (Saturday == true) {
//                    String day = "Saturday";
//                    saturdayList.setText(set);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
//                    dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
//                    dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
//                }
//                if (Sunday == true) {
//                    String day = "Sunday";
//                    sundayList.setText(set);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenHour(intStartHour);
//                    dispRest.gethOps().get(day).get(controllerString).setOpenMins(intStartMinute);
//                    dispRest.gethOps().get(day).get(controllerString).setDurHours(intEndHour);
//                    dispRest.gethOps().get(day).get(controllerString).setDurMins(intEndMinute);
//                }
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
                    monButton.getBackground().setColorFilter(Color.rgb(112, 115, 114), PorterDuff.Mode.MULTIPLY);
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
                    tueButton.getBackground().setColorFilter(Color.rgb(112, 115, 114), PorterDuff.Mode.MULTIPLY);
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
                    wedButton.getBackground().setColorFilter(Color.rgb(112, 115, 114), PorterDuff.Mode.MULTIPLY);
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
                    thuButton.getBackground().setColorFilter(Color.rgb(112, 115, 114), PorterDuff.Mode.MULTIPLY);
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
                    friButton.getBackground().setColorFilter(Color.rgb(112, 115, 114), PorterDuff.Mode.MULTIPLY);
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
                    satButton.getBackground().setColorFilter(Color.rgb(112, 115, 114), PorterDuff.Mode.MULTIPLY);
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
                    sunButton.getBackground().setColorFilter(Color.rgb(112, 115, 114), PorterDuff.Mode.MULTIPLY);
                    sunButton.setTextColor(Color.WHITE);
                    //monButton.setBackgroundColor(red);
                }
            }
        });

        return view;
    }

    private Boolean checkHours(){
        int intStartHour;
        int intEndHour;

        if(startHalf.equals("PM")){
            intStartHour = Integer.parseInt(startHours) + 12;
        } else {
            intStartHour = Integer.parseInt(startHours);
        }

        if(endHalf.equals("PM")){
            intEndHour = Integer.parseInt(endHours) + 12;
        } else {
            intEndHour = Integer.parseInt(endHours);
        }

        if(intEndHour > intStartHour){
            return true;
        } else {
            return false;
        }
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

    private void clearAllDays() {
        mondayList.setText("");
        tuesdayList.setText("");
        wednesdayList.setText("");
        thursdayList.setText("");
        fridayList.setText("");
        saturdayList.setText("");
        sundayList.setText("");
    }

    private void createFinalObject() {
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();
        mDatabaseRef.child(uid).setValue(dispRest);
        db.collection("Restaurants").document(firebaseAuth.getUid()).set(dispRest);

        goBack();

    }

    private void goBack() {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment nextStep = new displayRestaurantProfile();
        ft.replace(R.id.container_fragment, nextStep);
        ft.commit();
    }


}
