package com.example.seefood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragmentG extends Fragment {

    public LoginFragmentG() {
        // Required empty public constructor
    }

    private TextInputLayout email, password;
    private FirebaseAuth firebaseAuth;
    private Button loginButton;
    private Button signButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_g, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.userTextInputEmail);
        password = view.findViewById(R.id.userTextInputLayoutPassword);
        loginButton = view.findViewById(R.id.buttonUserLogin);
        signButton = view.findViewById(R.id.buttonGoSignUp);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        return view;
    }

    private void userLogin() {

    }

    private void goSignUp() {

    }
}
