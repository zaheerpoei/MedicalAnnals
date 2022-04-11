package com.example.medicalannals.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.medicalannals.R;
import com.example.medicalannals.models.PatientModel;

public class ProfileManagement extends AppCompatActivity {

    ImageView toolbarImageBackPatient,ivChangePass;
    private EditText etFullName,etAddress,etPhoneNumber;
    PatientModel patientModel;
    ConstraintLayout clChangePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        Intent i = getIntent();
        patientModel = (PatientModel)i.getSerializableExtra("patientModel");
        initViews();

        clickListeners();
    }

    private void initViews() {
        toolbarImageBackPatient = findViewById(R.id.toolbar_image_back_patient);
        clChangePassword = findViewById(R.id.cl_change_password);
        ivChangePass = findViewById(R.id.iv_edit_password_patient);
        etFullName = findViewById(R.id.ed_full_name_patient);
        etAddress = findViewById(R.id.ed_email_address_patient);
        etPhoneNumber = findViewById(R.id.ed_phone_number_patient);
        etFullName.setText(patientModel.getName());
        etAddress.setText(patientModel.getEmail());
        etPhoneNumber.setText(patientModel.getNumber());
    }

    private void clickListeners() {
        toolbarImageBackPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        clChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ProfileManagement.this , UpdatePassword.class);
                i.putExtra("email",patientModel.getEmail());
                startActivity(i);
            }
        });
    }
}