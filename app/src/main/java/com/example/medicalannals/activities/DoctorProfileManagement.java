package com.example.medicalannals.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.medicalannals.R;
import com.example.medicalannals.models.DoctorsModel;

public class DoctorProfileManagement extends AppCompatActivity {

    ImageView toolbarImageBack ;
    EditText edFullName, edEmail, phoneNumber, childSpecialist, qualification;
    ConstraintLayout constraintLayout;
    DoctorsModel doctorsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_management);
        Intent intent = getIntent();
        doctorsModel = (DoctorsModel)intent.getSerializableExtra("doctorModel");
        initViews();
        clickListeners();
    }

    private void initViews() {
        toolbarImageBack = findViewById(R.id.toolbar_image_back);
        edFullName = findViewById(R.id.ed_full_name);
        edEmail = findViewById(R.id.ed_email_address);
        phoneNumber = findViewById(R.id.ed_phone_number);
        childSpecialist = findViewById(R.id.ed_specialization);
        qualification = findViewById(R.id.ed_qualification);
        constraintLayout = findViewById(R.id.cl_change_password);

        edFullName.setText(doctorsModel.name);
        edEmail.setText(doctorsModel.email);
        phoneNumber.setText(doctorsModel.contact);
        childSpecialist.setText(doctorsModel.specialization);
        qualification.setText(doctorsModel.qualification);

    }

    private void clickListeners() {
        toolbarImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DoctorProfileManagement.this, DoctorUpdatePassword.class);
                intent.putExtra("email",doctorsModel.getEmail());
                startActivity(intent);
            }
        });
    }
}