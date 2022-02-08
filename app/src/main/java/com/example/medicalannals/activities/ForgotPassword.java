package com.example.medicalannals.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
                Intent i = new Intent(ForgotPassword.this , SignIn.class);
                startActivity(i);
                finish();
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