package com.example.seefood.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class LoginFragmentUser extends Fragment {
    public LoginFragmentUser() {
        // Required empty public constructor
    }

    private TextInputLayout email, password;
    private Button button;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        password = view.findViewById(R.id.userTextInputLayoutPasswordLogin);
        email = view.findViewById(R.id.userTextInputLayoutEmailLogin);

        button = view.findViewById(R.id.buttonLoginUser);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInput(view);
            }
        });

        return view;
    }

    public void confirmInput(View v){
        firebaseAuth.signInWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // Sign in success, Maybe? update UI with the signed in user's info
                            // Not currently in use
                            // FirebaseUser user = firebaseAuth.getCurrentUser();

                            Toast.makeText(getActivity(), "Logged in Successfully", Toast.LENGTH_SHORT);
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                        else {
                            // If sign in fails, display a message to the user
                            Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

}