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
import com.google.android.material.textfield.TextInputEditText;

public class DoctorUpdatePassword extends AppCompatActivity {

    ImageView ivToolbarImageBack;
    Button btnUpdatePasswordDoc;
    TextInputEditText tiedUpdatePasswordDoc;

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

        btnUpdatePasswordDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(DoctorUpdatePassword.this);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow;
                Message = (TextView) dialogView.findViewById(R.id.tvMessage);
                btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);

                Message.setText("Verification code has been sent to your email address.Click on the link to change the password");

                btnAllow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent i = new Intent(DoctorUpdatePassword.this, DoctorDashboard.class);
                        startActivity(i);
                        finish();
                    }
                });

            }
        });
    }

    private void initViews() {
        ivToolbarImageBack = findViewById(R.id.toolbar_image_back_doctor);
        btnUpdatePasswordDoc = findViewById(R.id.btn_update_password_doctor);
        tiedUpdatePasswordDoc = findViewById(R.id.tied_update_password_doc);
    }
}