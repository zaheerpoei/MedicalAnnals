package com.example.medicalannals.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medicalannals.R;

public class ProfileManagement extends AppCompatActivity {

    ImageView toolbarImageBackPatient;
    ImageView ivEditPasswordPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        toolbarImageBackPatient = findViewById(R.id.toolbar_image_back_patient);
        ivEditPasswordPatient = findViewById(R.id.iv_edit_password_patient);

        clickListeners();
    }

    private void clickListeners() {
        toolbarImageBackPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileManagement.this , PatientDashboard.class);
                startActivity(i);
                finish();
            }
        });

        ivEditPasswordPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ProfileManagement.this , UpdatePassword.class);
                startActivity(i);
                finish();

            }
        });
    }
}