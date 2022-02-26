package com.example.medicalannals.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medicalannals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    Button btnChangePassword;
    ImageView ivBackArrowForgotPassword;
    TextInputEditText tiedEmailAddress;

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
                if(checkField()){
                    String emailAddress = tiedEmailAddress.getText().toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        ShowAlertDialog("Verification code has been sent to your email address.Click on the link to change the password");
                                    }else {
                                        Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }
        });

        ivBackArrowForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ForgotPassword.this , SignIn.class);
                startActivity(i);
            }
        });
    }

    private boolean checkField() {
        tiedEmailAddress.setError(null);
        boolean provideField = true;
        View focusView = null;
        if(TextUtils.isEmpty(tiedEmailAddress.getText().toString())){
            ShowAlertDialog("Please Enter Email");
            focusView = tiedEmailAddress;
            provideField = false;
        }
        if (!provideField){
            focusView.requestFocus();
        }
        return provideField;
    }

    private void initViews() {
        btnChangePassword = findViewById(R.id.btn_change_password);
        tiedEmailAddress = findViewById(R.id.tied_email_forgot);
        ivBackArrowForgotPassword = findViewById(R.id.iv_back_arrow_forgot_password);
    }

    protected void ShowAlertDialog(String stMessage){

        Dialog dialog = new Dialog(ForgotPassword.this);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View view  = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        dialog.setContentView(view);

        TextView Message,btnAllow;
        ImageView ivAlert;
        Message=(TextView)view.findViewById(R.id.tvMessage);
        btnAllow=(TextView)view.findViewById(R.id.btn_allow);
        ivAlert = (ImageView) view.findViewById(R.id.imageView16) ;
        Message.setText(stMessage);

        ivAlert.setImageResource(R.drawable.done);
        ivAlert.setColorFilter(ContextCompat.getColor(this,R.color.green));


        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });




        dialog.show();
    };
}