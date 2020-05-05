package com.example.seefood.editCustomer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.FragmentProfile;
import com.example.seefood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class changePasswordFragment extends Fragment {

    public changePasswordFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser currentUser;

    private TextInputEditText oldPass, newPass, newPassCheck;
    private TextView errorText;
    Button nextButton;
    Button cancelButton;

    String oldInput;
    String newInput;
    String newInputCheck;

    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "^" +
                            "(?=.*[0-9])" +         // at least 1 digit
                            "(?=.*[a-z])" +         // at least 1 lowercase letter
                            "(?=.*[A-Z])" +         // at least 1 uppercase letter
                            "(?=.*[@#$%^&+=])" +    // at least 1 special character
                            "(?=\\S+$)" +           // no white spaces
                            ".{6,20}" +               // at least 6 characters
                            "$");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        oldPass = view.findViewById(R.id.userTextInputPasswordLogin);
        newPass = view.findViewById(R.id.userTextInputPasswordLogin2);
        newPassCheck = view.findViewById(R.id.userTextInputPasswordLogin3);
        errorText = view.findViewById(R.id.ErrorText);
        cancelButton = view.findViewById(R.id.cancelButton);

        nextButton = view.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyPassword()){
                    changePassword();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new FragmentProfile();
                ft.replace(R.id.container_fragment, nextStep);
                ft.commit();
            }
        });


        return view;
    }

    private boolean verifyPassword() {
        oldInput = Objects.requireNonNull(oldPass.getText()).toString().trim();
        newInput = Objects.requireNonNull(newPass.getText()).toString().trim();
        newInputCheck = Objects.requireNonNull(newPassCheck.getText()).toString().trim();

        if (oldInput.isEmpty() || newInput.isEmpty() || newInputCheck.isEmpty()) {
            //password.setError("Password cannot be empty");
            errorText.append("Fields cannot be empty.\n");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(oldInput).matches() || !PASSWORD_PATTERN.matcher(newInput).matches() || !PASSWORD_PATTERN.matcher(newInputCheck).matches()) {
            //password.setError("Password does not meet requirements. Requires at least 1 Uppercase, Lowercase, Number, and Special Character: @#$%^&+_ are valid characters");
            errorText.append("Password does not meet requirements. Requires at least 1 Uppercase, Lowercase, Number, and Special Character: @#$%^&+_ are valid characters \n");
            return false;
        } else if (!newInput.equals(newInputCheck)) {
            errorText.append("New Passwords do not match.");
        } else {
            //password.setError(null);
            return true;
        }
        return false;
    }

    private void changePassword(){
        currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        String email = currentUser.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldInput);

        currentUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            currentUser.updatePassword(newInput).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        Fragment nextStep = new FragmentProfile();
                                        ft.replace(R.id.container_fragment, nextStep);
                                        ft.commit();

                                    } else {
                                        Toast.makeText(getContext(), "Error: Password not updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
    }

}
