package com.example.medicalannals.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medicalannals.R;

public class DoctorProfileManagement extends AppCompatActivity {

    ImageView toolbarImageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_management);

        toolbarImageBack = findViewById(R.id.toolbar_image_back);
        clickListeners();
    }

    private void clickListeners() {
        toolbarImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DoctorProfileManagement.this , DoctorDashboard.class);
                startActivity(i);
                finish();
            }
        });

    }
}