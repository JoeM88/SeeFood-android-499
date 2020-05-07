package com.example.seefood.displayProfiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.example.seefood.editRestaurant.editRestDetails;
import com.example.seefood.editRestaurant.editRestHoursFragment;
import com.example.seefood.editRestaurant.editRestPhotoFragment;
import com.example.seefood.editRestaurant.viewRestMenuItemsFragment;
import com.example.seefood.models.OperationsModel;
import com.example.seefood.models.RestaurantModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class displayRestaurantProfile extends Fragment {

    public displayRestaurantProfile() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    //TextView tv;

    String uid;
    RestaurantModel dispRest = null;

    TextView showRestName;
    TextView showRestAddress;
    TextView showHours;
    ImageView im;
    Button logoutButton;

    Button breakfastButton;
    Button lunchButton;
    Button dinnerButton;
    Button dessertButton;

    Button editStage1;
    Button editStage2;
    Button editStage3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_restaurant_profile, container, false);
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        //tv = view.findViewById(R.id.sampleDataReturnRest);
        showRestName = view.findViewById(R.id.dispRest_RestName);
        showRestAddress = view.findViewById(R.id.dispRest_StreetAddress);
        im = view.findViewById(R.id.profile_image_rest);
        logoutButton = view.findViewById(R.id.logoutButton);
        showHours = view.findViewById(R.id.hoursOperationText);

        editStage1 = view.findViewById(R.id.editRestDetails);
        editStage2 = view.findViewById(R.id.editRestPhoto);
        editStage3 = view.findViewById(R.id.editHours);

        breakfastButton = view.findViewById(R.id.dispRest_Breakfast);
        lunchButton = view.findViewById(R.id.dispRest_Lunch);
        dinnerButton = view.findViewById(R.id.dispRest_Dinner);
        dessertButton = view.findViewById(R.id.dispRest_Dessert);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle passForward = new Bundle();
                passForward.putString("viewController", "Breakfast");
                //passForward.putSerializable("restaurant", dispRest);
                passForward.putParcelable("restaurant", dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new viewRestMenuItemsFragment();
                nextStep.setArguments(passForward);
                ft.replace(R.id.container_fragment, nextStep).addToBackStack(null);
                ft.commit();
            }
        });

        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle passForward = new Bundle();
                passForward.putString("viewController", "Lunch");
                passForward.putParcelable("restaurant", dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new viewRestMenuItemsFragment();
                nextStep.setArguments(passForward);
                ft.replace(R.id.container_fragment, nextStep).addToBackStack(null);
                ft.commit();
            }
        });

        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle passForward = new Bundle();
                passForward.putString("viewController", "Dinner");
                passForward.putParcelable("restaurant", dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new viewRestMenuItemsFragment();
                nextStep.setArguments(passForward);
                ft.replace(R.id.container_fragment, nextStep).addToBackStack(null);
                ft.commit();
            }
        });

        dessertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle passForward = new Bundle();
                passForward.putString("viewController", "Dessert");
                passForward.putParcelable("restaurant", dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new viewRestMenuItemsFragment();
                nextStep.setArguments(passForward);
                ft.replace(R.id.container_fragment, nextStep).addToBackStack(null);
                ft.commit();
            }
        });

        editStage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle passForward = new Bundle();
                passForward.putParcelable("restaurant", dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new editRestDetails();
                nextStep.setArguments(passForward);
                ft.replace(R.id.container_fragment, nextStep).addToBackStack(null);
                ft.commit();
            }
        });

        editStage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle passForward = new Bundle();
                passForward.putParcelable("restaurant", dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new editRestPhotoFragment();
                nextStep.setArguments(passForward);
                ft.replace(R.id.container_fragment, nextStep).addToBackStack(null);
                ft.commit();
            }
        });

        editStage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle passForward = new Bundle();
                passForward.putParcelable("restaurant", dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new editRestHoursFragment();
                nextStep.setArguments(passForward);
                ft.replace(R.id.container_fragment, nextStep).addToBackStack(null);
                ft.commit();
            }
        });

        displayTestData();


        return view;
    }

    //APPLEJACKS
    private void displayTestData() {
        CollectionReference rp = db.collection("Restaurants");
        Query queryRestaurant = rp.whereEqualTo("owner", uid);
        queryRestaurant.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        dispRest = document.toObject(RestaurantModel.class);
                        showRestName.setText(dispRest.getRestName());
                        showRestAddress.setText(dispRest.getStreetAddress() + "\n" + dispRest.getCity() + ", " + dispRest.getState() + " " + dispRest.getZipCode() + "\n" + dispRest.getPhoneNumber());

                        if (Objects.requireNonNull(dispRest.getOfferings().get("Breakfast")).size() != 0) {
                            int items = dispRest.getOfferings().get("Breakfast").size();
                            String newText = breakfastButton.getText() + " (" + Integer.toString(items) + " items)";
                            breakfastButton.setText(newText);
                        } else {
                            String newText = breakfastButton.getText() + " (0 Items)";
                            breakfastButton.setText(newText);
                        }

                        if (Objects.requireNonNull(dispRest.getOfferings().get("Lunch")).size() != 0) {
                            int items = dispRest.getOfferings().get("Lunch").size();
                            String newText = lunchButton.getText() + " (" + Integer.toString(items) + " items)";
                            lunchButton.setText(newText);
                        } else {
                            String newText = lunchButton.getText() + " (0 Items)";
                            lunchButton.setText(newText);
                        }

                        if (Objects.requireNonNull(dispRest.getOfferings().get("Dinner")).size() != 0) {
                            int items = dispRest.getOfferings().get("Dinner").size();
                            String newText = dinnerButton.getText() + " (" + Integer.toString(items) + " items)";
                            dinnerButton.setText(newText);
                        } else {
                            String newText = dinnerButton.getText() + " (0 Items)";
                            dinnerButton.setText(newText);
                        }

                        if (Objects.requireNonNull(dispRest.getOfferings().get("Dessert")).size() != 0) {
                            int items = dispRest.getOfferings().get("Dessert").size();
                            String newText = dessertButton.getText() + " (" + Integer.toString(items) + " items)";
                            dessertButton.setText(newText);
                        } else {
                            String newText = dessertButton.getText() + " (0 Items)";
                            dessertButton.setText(newText);
                        }

                        Glide.with(getContext())
                                .load(dispRest.getPhotoURL())
                                .into(im);

                        showHours.setText("Hours of Operation - ");
                        ArrayList<String> days = new ArrayList<String>();
                        days.add("Monday");
                        days.add("Tuesday");
                        days.add("Wednesday");
                        days.add("Thursday");
                        days.add("Friday");
                        days.add("Saturday");
                        days.add("Sunday");

                        for(int i = 0; i<7; i++){
                            showHours.append("\n" + days.get(i) + "- Breakfast:" + runHourCheck(Objects.requireNonNull(Objects.requireNonNull(dispRest.gethOps().get(days.get(i))).get("Breakfast"))) + ", Lunch:" + runHourCheck(Objects.requireNonNull(Objects.requireNonNull(dispRest.gethOps().get(days.get(i))).get("Lunch"))) + ", Dinner: " + runHourCheck(Objects.requireNonNull(Objects.requireNonNull(dispRest.gethOps().get(days.get(i))).get("Dinner"))));
                        }
//                        showHours.append("\n Monday - Breakfast:" + runHourCheck(Objects.requireNonNull(dispRest.gethOps().get("Monday")).get("Breakfast")) + ", Lunch:" + runHourCheck(Objects.requireNonNull(dispRest.gethOps().get("Monday")).get("Lunch")) + ", Dinner: " + runHourCheck(Objects.requireNonNull(dispRest.gethOps().get("Monday")).get("Dinner")));
//                        showHours.append("\n Tuesday - Breakfast:" + runHourCheck(Objects.requireNonNull(dispRest.gethOps().get("Tuesday")).get("Breakfast")) + ", Lunch:" + runHourCheck(Objects.requireNonNull(dispRest.gethOps().get("Tuesday")).get("Lunch")) + ", Dinner: " + runHourCheck(Objects.requireNonNull(dispRest.gethOps().get("Tuesday")).get("Dinner")));
//                        showHours.append("\n Wednesday - Breakfast:" + runHourCheck(Objects.requireNonNull(dispRest.gethOps().get("Wednesday")).get("Breakfast")) + ", Lunch:" + runHourCheck(Objects.requireNonNull(dispRest.gethOps().get("Wednesday")).get("Lunch")) + ", Dinner: " + runHourCheck(Objects.requireNonNull(dispRest.gethOps().get("Wednesday")).get("Dinner")));




                    }
                }
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    public String runHourCheck(OperationsModel opera){
        int startHours = opera.getOpenHour();
        int endHours = opera.getDurHours();
        String beginResult = "";
        String endBegin = "";
        String endResult = "";
        String endEnd = "";
        if(startHours == endHours){
            return "CLOSED";
        } else {

            if(startHours > 12){
                int convertedHours = startHours - 12;
                if(opera.getDurMins() < 10){
                    endBegin = "0" + Integer.toString(opera.getDurMins()) + " PM";
                } else {
                    endBegin = Integer.toString(opera.getDurMins()) + " PM";
                }
                beginResult = Integer.toString(convertedHours);
            } else if(startHours == 0){
                int convertedHours = startHours + 12;
                if(opera.getOpenMins() < 10){
                    endBegin = "0" + Integer.toString(opera.getOpenMins()) + " AM";
                } else {
                    endBegin = Integer.toString(opera.getOpenMins()) + " AM";
                }
                beginResult = Integer.toString(convertedHours);
            } else {
                beginResult = Integer.toString(startHours);
                if(opera.getOpenMins() < 10){
                    endBegin = "0" + Integer.toString(opera.getOpenMins()) + " AM";
                } else {
                    endBegin = Integer.toString(opera.getOpenMins()) + " AM";
                }
            }

            if(endHours > 12){
                int convertedHours = endHours - 12;
                if(opera.getDurMins() < 10){
                    endEnd = "0" + Integer.toString(opera.getDurMins()) + " PM";
                } else {
                    endEnd = Integer.toString(opera.getDurMins()) + " PM";
                }
                endResult = Integer.toString(convertedHours);
            } else if(endHours == 0){
                int convertedHours = endHours + 12;
                if(opera.getDurMins() < 10){
                    endEnd = "0" + Integer.toString(opera.getDurMins()) + " AM";
                } else {
                    endEnd = Integer.toString(opera.getDurMins()) + " AM";
                }
                endResult = Integer.toString(convertedHours);
            } else {
                endResult = Integer.toString(endHours);
                if(opera.getDurMins() < 10){
                    endEnd = "0" + Integer.toString(opera.getDurMins()) + " AM";
                } else {
                    endEnd = Integer.toString(opera.getDurMins()) + " AM";
                }
            }


            return beginResult + ":" + endBegin + "-" + endResult + ":" + endEnd;
        }

    }
}


