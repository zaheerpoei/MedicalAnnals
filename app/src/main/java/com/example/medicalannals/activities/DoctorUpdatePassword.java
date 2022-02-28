package com.example.medicalannals.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medicalannals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class DoctorUpdatePassword extends AppCompatActivity {

    ImageView ivToolbarImageBack;
    Button btnUpdatePasswordDoc;
    TextInputEditText tietOldPass,tietNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update_password);
        String email = getIntent().getStringExtra("email");
        initViews();
        clickListeners(email);
    }

    private void clickListeners(String email) {
        ivToolbarImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnUpdatePasswordDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkField()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(email, tietOldPass.getText().toString());

                    assert user != null;
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(Objects.requireNonNull(tietNewPass.getText()).toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    ShowAlertDialog("Password Updated",true);

                                                } else {
                                                    ShowAlertDialog("Sorry we are unable to process your request this time. Try again later", false);
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d("TAG", "onComplete: "+task.getException().toString());
                                        ShowAlertDialog("Please Enter Right Password", false);
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean checkField() {
        tietOldPass.setError(null);
        tietNewPass.setError(null);
        boolean provideField = true;
        View focusView = null;

        if(TextUtils.isEmpty(tietOldPass.getText().toString())){
            ShowAlertDialog("Please Enter Old Pass", false);
            focusView = tietOldPass;
            provideField = false;

        }else if(TextUtils.isEmpty(tietNewPass.getText().toString())){
            ShowAlertDialog("Please Enter New Pass", false);
            focusView = tietNewPass;
            provideField = false;

        }
        if(!provideField){
            focusView.requestFocus();
        }
        return provideField;
    }

    protected void ShowAlertDialog(String stMessage, boolean b){

        Dialog dialog = new Dialog(this);
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

        if(stMessage.equals("Please Enter Right Password") || stMessage.equals("Sorry we are unable to process your request this time. Try again later")){
            ivAlert.setImageResource(R.drawable.warning);
            ivAlert.setColorFilter(ContextCompat.getColor(this,R.color.dark_blue_700));
        }else {
            ivAlert.setImageResource(R.drawable.done);
            ivAlert.setColorFilter(ContextCompat.getColor(this,R.color.green));
        }



        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b){
                    FirebaseAuth.getInstance().signOut();
                    SignIn.patient = false;
                    SignIn.doctor = false;
                    Intent intentSignOut = new Intent(DoctorUpdatePassword.this , SignIn.class);
                    startActivity(intentSignOut);
                    finishAffinity();
                    dialog.dismiss();
                }else {
                    dialog.dismiss();
                }

            }
        });




        dialog.show();
    };


    private void initViews() {
        ivToolbarImageBack = findViewById(R.id.toolbar_image_back_doctor);
        btnUpdatePasswordDoc = findViewById(R.id.btn_update_password_doctor);
        tietOldPass = findViewById(R.id.tiet_old_pass);
        tietNewPass = findViewById(R.id.tiet_new_password);
    }
}