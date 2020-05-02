package com.example.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.displayProfiles.displayRestaurantProfile;
import com.example.seefood.login.SignUpActivity;
import com.example.seefood.profileSetup.createProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FragmentProfile extends Fragment {

    private View v;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;
    TextView tv;
    Button createProfileButton;
    Button logoutButton;
    FragmentTransaction ft;
    static String uid;

    public FragmentProfile(){}

    public void goSignUp(View view){
        Intent intent = new Intent(getContext(), SignUpActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    public void createProfile(View view){
        Intent intent = new Intent(getContext(), createProfileActivity.class);
        startActivity(intent);
    }

    public void onStart(){
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            uid = currentUser.getUid();
            checkProfExists();
        }
        else{
            goSignUp(null);
        }
    }

    public void checkProfExists(){
        CollectionReference cf = db.collection("userType");
        Query query = cf.whereEqualTo("user_id", uid);
        //call something else if you don't get directed into the proper profile fragment
        query.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot document : task.getResult()){
                    //Toast.makeText(getActivity(), "User is typed", Toast.LENGTH_LONG).show();
                    Map<String, Object> data = document.getData();
                    String theID = (String) data.get("user_id");
                    String theType = (String) data.get("userType");
//                        tv.append(theID);
//                        tv.append(theType);
//                        tv.append(data.toString());
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    if(theType.equals("owner")){
                        ft.replace(R.id.container_fragment, new displayRestaurantProfile());
                        ft.commit();
                    } else {

                    }
                }
            } else {
                //Toast.makeText(getActivity(), "User is NOT typed", Toast.LENGTH_LONG).show();
                createProfile(null);

            }
        });
//        CollectionReference rp = db.collection("Restaurants");
//        Query queryRestaurant = rp.whereEqualTo("owner", uid);
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//
//                }
//            }
//        });
//        createProfile(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.profile_fragment, container, false);
        //tv = v.findViewById(R.id.displayData);
        logoutButton = v.findViewById(R.id.logoutButton);
        createProfileButton = v.findViewById(R.id.create_profile);
       logoutButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               logout(v);
           }
       });
       createProfileButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               createProfile(v);
           }
       });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}
