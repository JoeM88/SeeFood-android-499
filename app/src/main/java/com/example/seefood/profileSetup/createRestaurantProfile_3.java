package com.example.seefood.profileSetup;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.seefood.R;
import com.example.seefood.models.RestaurantModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class createRestaurantProfile_3 extends Fragment {

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

    Button monButton;
    Button tueButton;
    Button wedButton;
    Button thuButton;
    Button friButton;
    Button satButton;
    Button sunButton;

//    TextView myText;
//    TextView myText2;
//    TextView myText3;
//    TextView myText4;
//    TextView myText5;
//    TextView myText6;
//    TextView myText7;

    /*TEST ONLY*/
    TextView rmDetails;
    TextView purl;
    /*TEST ONLY*/

    Bundle bundle;

RestaurantModel rm = new RestaurantModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_restaurant_profile_3, container, false);

        monButton = view.findViewById(R.id.mondayButton);
        tueButton = view.findViewById(R.id.tuesdayButton);
        wedButton = view.findViewById(R.id.wednesdayButton);
        thuButton = view.findViewById(R.id.thursdayButton);
        friButton = view.findViewById(R.id.fridayButton);
        satButton = view.findViewById(R.id.saturdayButton);
        sunButton = view.findViewById(R.id.sundayButton);

//        myText = view.findViewById(R.id.appleText);
//        myText2 = view.findViewById(R.id.appleText2);
//        myText3 = view.findViewById(R.id.appleText3);
//        myText4 = view.findViewById(R.id.appleText4);
//        myText5 = view.findViewById(R.id.appleText5);
//        myText6 = view.findViewById(R.id.appleText6);
//        myText7 = view.findViewById(R.id.appleText7);

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



        //rmDetails.setText(rm.printRest(rm));
        //purl.setText(url);

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
}