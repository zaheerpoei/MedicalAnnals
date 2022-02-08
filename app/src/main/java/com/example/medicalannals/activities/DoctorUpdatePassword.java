package com.example.medicalannals.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.medicalannals.R;

public class DoctorUpdatePassword extends AppCompatActivity {

    ImageView ivToolbarImageBack;
    Button btnUpdatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update_password);

        initViews();
        clickListeners();
    }

    private void clickListeners() {
        ivToolbarImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DoctorUpdatePassword.this, DoctorDashboard.class);
                startActivity(i);
                finish();
            }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(DoctorUpdatePassword.this, DoctorDashboard.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initViews() {
        ivToolbarImageBack = findViewById(R.id.toolbar_image_back_doctor);
        btnUpdatePassword = findViewById(R.id.btn_update_password_doctor);
    }
}