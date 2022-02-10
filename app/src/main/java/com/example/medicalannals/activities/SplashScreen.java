package com.example.medicalannals.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalannals.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                FirebaseAuth.getInstance().getCurrentUser();
//                if ()
                Intent intent=new Intent(SplashScreen.this, SignIn.class);
                startActivity(intent);
                finish();


            }
        }, 2000);
    }
}