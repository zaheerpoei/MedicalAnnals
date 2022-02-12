package com.example.medicalannals.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalannals.R;

public class EditPatientRecord extends AppCompatActivity {

    ImageView toolbarImageBackEditRecord;
    Button btnEditPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_record);
        initViews();
        clickListeners();
    }

    private void clickListeners() {
        toolbarImageBackEditRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnEditPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(EditPatientRecord.this);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow;
                Message = (TextView) dialogView.findViewById(R.id.tvMessage);
                btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);

                Message.setText("Record has been added.");

                btnAllow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent i = new Intent(EditPatientRecord.this , DoctorDashboard.class);
                        startActivity(i);
                        finish();
                    }
                });

                dialog.show();
            }
        });
    }

    private void initViews() {
        toolbarImageBackEditRecord = findViewById(R.id.toolbar_image_back_edit_record);
        btnEditPatient = findViewById(R.id.btn_edit_patient);
    }
}