package com.example.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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


public class profileActivity extends AppCompatActivity {

    public static FragmentManager fm;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentUser;
    TextView tv;
    FragmentTransaction ft;
    Button createProfileButton;

    static String uid;

    public void goSignUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
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

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createProfile(View view){
        Intent intent = new Intent(this, createProfile.class);
        startActivity(intent);
    }


    public void checkProfExists(){
        CollectionReference cf = db.collection("userType");
        Query query = cf.whereEqualTo("user_id", uid);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Toast.makeText(getApplicationContext(), "User is typed", Toast.LENGTH_LONG).show();
                        Map<String, Object> data = document.getData();
                        String theID = (String) data.get("user_id");
                        String theType = (String) data.get("userType");
                        tv.append(theID);
                        tv.append(theType);
                        System.out.println(document.getId() + " ----> " + document.getData());
                        //createProfileButton.setVisibility(View.GONE);
                        ft.replace(R.id.container_fragment, new DisplayProfileFragment());
                        ft.commit();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User is NOT typed", Toast.LENGTH_LONG).show();;
                }
            }
            //call something else if you don't get directed into the proper profile fragment
        });
//        createProfile(null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        tv = (TextView) findViewById(R.id.displayData);
        createProfileButton = (Button) findViewById(R.id.create_profile);

        //TODO: DELETE THIS TEMPORARY


        fm = getSupportFragmentManager();

        if(findViewById(R.id.container_fragment) != null) {
            if(savedInstanceState != null){
                return;
            }

            ft = fm.beginTransaction();

        }
//        FragmentTransaction ft  = getSupportFragmentManager().beginTransaction();

//        ft.replace(R.id.container_fragment, new FragmentList());
//        ft.commit();
    }


}
