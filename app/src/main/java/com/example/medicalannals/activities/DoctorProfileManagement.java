package com.example.medicalannals.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalannals.R;

public class DoctorProfileManagement extends AppCompatActivity {

    ImageView toolbarImageBack , ivEditPasswordDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_management);

        initViews();
        clickListeners();
    }

    private void initViews() {
        ivEditPasswordDoctor = findViewById(R.id.iv_edit_password_doctor);
        toolbarImageBack = findViewById(R.id.toolbar_image_back);
    }

    private void clickListeners() {
        toolbarImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ivEditPasswordDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DoctorProfileManagement.this , DoctorUpdatePassword.class);
                startActivity(i);
                finish();
            }
        });

    }
}