package com.example.seefood.profileSetup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link createProfile_step1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class createProfile_step1 extends Fragment{

    public createProfile_step1() {
        // Required empty public constructor
    }

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button nextButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String choice;

    public static createProfile_step1 newInstance(String param1, String param2) {
        createProfile_step1 fragment = new createProfile_step1();
        Bundle args = new Bundle();
        return fragment;
    }

    private FirebaseAuth firebaseAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_profile_step1, container, false);
        radioGroup = view.findViewById(R.id.choiceRadio);
        Button nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToStep2();
            }
        });
        return view;
    }

    public void addListenerOnButton(){
        radioGroup = radioGroup.findViewById(R.id.choiceRadio);
        nextButton = nextButton.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadio = radioGroup.getCheckedRadioButtonId();

                radioButton = radioButton.findViewById(selectedRadio);
                Toast.makeText(getActivity(), (CharSequence) radioButton, Toast.LENGTH_LONG).show();
            }
        });

    }

//    public void setRadio(View view){
//        boolean checked = ((RadioButton) view).isChecked();
//        switch(view.getId()){
//            case R.id.yesButton:
//                if(checked)
//                    Toast.makeText(getActivity(), "You are an owner", Toast.LENGTH_LONG).show();
//                break;
//            case R.id.noButton:
//                if(checked)
//                    Toast.makeText(getActivity(), "You are not an owner", Toast.LENGTH_LONG).show();
//                break;
//        }
//    }

    public void moveToStep2(){
        firebaseAuth = FirebaseAuth.getInstance();
        String temp = firebaseAuth.getUid();
        Toast.makeText(getActivity(), temp, Toast.LENGTH_LONG).show();
        System.out.println(temp);

        int selectedRadio = radioGroup.getCheckedRadioButtonId();

        final FragmentTransaction ft = getFragmentManager().beginTransaction();

        //Toast.makeText(getActivity(), String.valueOf(selectedRadio), Toast.LENGTH_LONG).show();

        if(selectedRadio == R.id.yesButton/*2131230965*/){
            Toast.makeText(getActivity(), "You are an owner", Toast.LENGTH_LONG).show();
            Map<String, String> userType = new HashMap<>();
            userType.put("userType", "owner");
            userType.put("user_id", firebaseAuth.getUid());
            db.collection("userType").document(firebaseAuth.getUid()).set(userType);
            ft.replace(R.id.createProf_container, new createRestaurantProfile_1());
            ft.commit();
        } else {
            Toast.makeText(getActivity(), "You are not an owner", Toast.LENGTH_LONG).show();
            Map<String, String> userType = new HashMap<>();
            userType.put("userType", "customer");
            userType.put("user_id", firebaseAuth.getUid());
            db.collection("userType").document(firebaseAuth.getUid()).set(userType);
            ft.replace(R.id.createProf_container, new createCustomerProfileFragment());
            ft.commit();
        }

        //setRadio(null);

    }

}