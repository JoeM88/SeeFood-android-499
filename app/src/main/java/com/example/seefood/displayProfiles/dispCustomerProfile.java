package com.example.seefood.displayProfiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.example.seefood.editCustomer.changePasswordFragment;
import com.example.seefood.editCustomer.editCustomerProfileFragment;
import com.example.seefood.models.CustomerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class dispCustomerProfile extends Fragment {

    public dispCustomerProfile() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;

    String uid;
    CustomerModel customer = null;

    TextView displayName;
    Button editProfile1;
    Button changePassword;
    ImageView im;

    Button logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_disp_customer_profile, container, false);

        displayName = view.findViewById(R.id.profileName);
        editProfile1 = view.findViewById(R.id.changeDisplayName);
        changePassword = view.findViewById(R.id.changePassword);
        im = view.findViewById(R.id.profileImage);
        logout = view.findViewById(R.id.logoutButtonCustomer);

        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();

        Toast.makeText(getContext(), uid, Toast.LENGTH_LONG).show();

        displayData();

        editProfile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle passForward = new Bundle();
                passForward.putParcelable("customer", customer);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new editCustomerProfileFragment();
                nextStep.setArguments(passForward);
                ft.replace(R.id.container_fragment, nextStep);
                ft.commit();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle passForward = new Bundle();
                passForward.putParcelable("customer", customer);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new changePasswordFragment();
                nextStep.setArguments(passForward);
                ft.replace(R.id.container_fragment, nextStep);
                ft.commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(null);
            }
        });

        return view;
    }

    private void displayData(){
        CollectionReference customerProfiles = db.collection("Customer");
        Query qCustomer = customerProfiles.whereEqualTo("displayID", uid);
        qCustomer.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())){
                    customer = document.toObject(CustomerModel.class);
                    displayName.setText(customer.getDiplayName());

                    Glide.with(getContext())
                            .load(customer.getPhotoUrl())
                            .into(im);

                }
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}
