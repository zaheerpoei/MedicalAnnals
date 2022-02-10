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

public class ForgotPassword extends AppCompatActivity {

    Button btnChangePassword;
    ImageView ivBackArrowForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        clickListeners();

    }

    private void clickListeners() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ForgotPassword.this);
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
                        Intent i = new Intent(ForgotPassword.this , SignIn.class);
                        startActivity(i);
                        finish();
                    }
                });

            }
        });

        ivBackArrowForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ForgotPassword.this , SignIn.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initViews() {
        btnChangePassword = findViewById(R.id.btn_change_password);
        ivBackArrowForgotPassword = findViewById(R.id.iv_back_arrow_forgot_password);
    }
}