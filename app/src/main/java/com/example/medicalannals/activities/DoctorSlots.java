package com.example.medicalannals.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.medicalannals.R;

public class DoctorSlots extends AppCompatActivity {


    ImageView ivToolbarBack;
    Button btnSetSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_slots);

        initViews();
        clickListeners();

    }

    private void clickListeners() {
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorSlots.this , DoctorDashboard.class);
                startActivity(i);
                finish();
            }
        });

        btnSetSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorSlots.this , DoctorDashboard.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void initViews() {
        ivToolbarBack = findViewById(R.id.toolbar_image_back);
        btnSetSlot = findViewById(R.id.btn_set_slot);
    }
}