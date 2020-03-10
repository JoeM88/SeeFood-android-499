package com.example.seefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class userProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    public void postData(View view){
        Intent intent = new Intent(this, dataLoadingActivity.class);
        //use put extra to pass things to the activity
        //Nonsense comment.
        startActivity(intent);
    }
}
