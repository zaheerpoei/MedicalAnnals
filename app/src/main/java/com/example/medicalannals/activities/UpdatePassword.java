package com.example.medicalannals.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.medicalannals.R;

public class UpdatePassword extends AppCompatActivity {

    ImageView ivToolbarImageBack;
    Button btnUpdatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        initViews();
        clickListeners();
    }

    private void clickListeners() {
        ivToolbarImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(UpdatePassword.this, PatientDashboard.class);
                    startActivity(i);
                    finish();
                }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                if(SignIn.patient==true) {
                    Intent i = new Intent(UpdatePassword.this, PatientDashboard.class);
                    startActivity(i);
                }
                else if(SignIn.doctor == true)
                {
                    Intent i = new Intent(UpdatePassword.this, DoctorDashboard.class);
                    startActivity(i);
                }
            }
        });
    }

    private void initViews() {
        ivToolbarImageBack = findViewById(R.id.toolbar_image_back);
        btnUpdatePassword = findViewById(R.id.btn_update_password);
    }
}