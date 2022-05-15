package com.example.medicalannals.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalannals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    TextInputEditText tiedEmailAddress, tiedPassword ;
    TextView tvForgotPassword , tvSignUpForAnAccount;
    TextInputLayout   tiEmailAddress , tiPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    ConstraintLayout constraintLayout;
    String  stEmailAddress,stPassword;
    RadioButton rbDoctor , rbPatient;
    public static Boolean  doctor = false , patient = false;
    ProgressDialog progressDialog;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        clickListeners(); }

    private void initViews() {
        constraintLayout = findViewById(R.id.constraint_parent);
        tiedEmailAddress = findViewById(R.id.tied_email_address_sign_in);
        tiedPassword = findViewById(R.id.tied_password_sign_in);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        tiEmailAddress = findViewById(R.id.ti_email_address);
        tiPassword = findViewById(R.id.ti_password);
        tvSignUpForAnAccount = findViewById(R.id.tv_sign_up_for_an_account);
        btnLogin = findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();
        rbDoctor = findViewById(R.id.rb_doctor);
        rbPatient = findViewById(R.id.rb_patient);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in..... ");
//        if(rbPatient.isChecked()) {
//            tiedEmailAddress.setText("waheed.shah121@gmail.com");
//            tiedPassword.setText("login@123");
//        }else {
//            tiedEmailAddress.setText("ibra@gmail.com");
//            tiedPassword.setText("something");
//        }
    }

    private void clickListeners() {

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this , ForgotPassword.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbPatient.isChecked()){
                    if(!checkFields()) {
                        if(isValidEmailAddress(tiedEmailAddress)) {
                            progressDialog.show();
                                LoginPatient(stEmailAddress, stPassword);
                        }
                    }
                }
                else if(rbDoctor.isChecked()){
                    if(!checkFields()) {
                        if (isValidEmailAddress(tiedEmailAddress)) {
                            progressDialog.show();
                            LoginDoctor(stEmailAddress, stPassword);
                        }
                    }
                }

            }
        });


        tvSignUpForAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SignIn.this , SignUp.class);
                startActivity(i);
                finish();

            }
        });
/*
        rbDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbDoctor.isChecked()) {
                    tiedEmailAddress.setText("ibra@gmail.com");
                    tiedPassword.setText("something");
                }else {
                    tiedEmailAddress.setText("waheed.shah121@gmail.com");
                    tiedPassword.setText("login@123");

                }
            }
        });

        rbPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbPatient.isChecked()) {
                    tiedEmailAddress.setText("waheed.shah121@gmail.com");
                    tiedPassword.setText("login@123");
                }else {
                    tiedEmailAddress.setText("ibra@gmail.com");
                    tiedPassword.setText("something");
                }
            }
        });*/
    }

    private boolean checkFields() {
        tiedEmailAddress.setError(null);
        tiedPassword.setError(null);

        boolean cancel = false;
        View focusView = null;

        stEmailAddress = tiedEmailAddress.getText().toString().toLowerCase().trim();
        stPassword = tiedPassword.getText().toString().trim();

        if (TextUtils.isEmpty(stEmailAddress)) {
            ShowAlertDialog("Enter Email Address");
            focusView = tiedEmailAddress;
            cancel = true;
        }

        else if (TextUtils.isEmpty(stPassword)) {
            ShowAlertDialog("Enter Password");
            focusView = tiedPassword;
            cancel = true;
        }
        else if (tiedPassword.length() < 8) {
            ShowAlertDialog("Password should be grater than 8 characters");
            focusView = tiedPassword;
            cancel = true;
        }
        if (cancel) {

            focusView.requestFocus();

        }

        return cancel;

    }

    public void LoginPatient(String EMAIL, String PASSWORD) {
        mAuth = FirebaseAuth.getInstance();

//        boolean emailVerified = mAuth.getCurrentUser().isEmailVerified();
//        if(emailVerified){
            mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                                String uid = mAuth.getCurrentUser().getUid();
                                database
                                        .child("Patient")
                                        .child(uid)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                progressDialog.dismiss();
                                                if(snapshot.exists()) {
                                                    mAuth = FirebaseAuth.getInstance();
                                                    boolean emailVerified = mAuth.getCurrentUser().isEmailVerified();
                                                    if (emailVerified) {
                                                        startActivity(new Intent(SignIn.this, PatientDashboard.class));
                                                        finishAffinity();
                                                    }
                                                    else
                                                    {
                                                        ShowAlertDialog("Email not verified.");
                                                    }
                                                }
                                                else{
                                                    ShowAlertDialog("Record not exists.");
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                ShowAlertDialog(error.toString());
                                            }
                                        });
                            }else {
                                ShowAlertDialog(task.getException().getMessage());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            });
//            Intent intent = new Intent(SignIn.this, PatientDashboard.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
        }
/*        mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                            String uid = mAuth.getCurrentUser().getUid();
                            database
                                    .child("Patient")
                                    .child(uid)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            progressDialog.dismiss();
                                            if(snapshot.exists()) {
                                                startActivity(new Intent(SignIn.this, PatientDashboard.class));
                                                finish();
                                            }
                                            else{
                                                ShowAlertDialog("Record not exists.");
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            ShowAlertDialog(error.toString());
                                        }
                                    });
                        }else {
                            ShowAlertDialog(task.getException().getMessage());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });*/
//    }

    public void LoginDoctor(String EMAIL, String PASSWORD) {
        mAuth = FirebaseAuth.getInstance();
//        boolean emailVerified = mAuth.getCurrentUser().isEmailVerified();
//                                        if(emailVerified){
//                                            Intent intent = new Intent(SignIn.this, PatientDashboard.class);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                            startActivity(intent);
//    }
        mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                            String uid = mAuth.getCurrentUser().getUid();
                            database
                                    .child("Doctor")
                                    .child(uid)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            progressDialog.dismiss();
                                            if(snapshot.exists()) {
                                                mAuth = FirebaseAuth.getInstance();
                                                boolean emailVerified = mAuth.getCurrentUser().isEmailVerified();
                                                if (true/*emailVerified*/) {
                                                    startActivity(new Intent(SignIn.this, DoctorDashboard.class));
                                                    finishAffinity();
                                                } else {
                                                    ShowAlertDialog("Email not verified.");
                                                }
                                            }

                                            else{ ShowAlertDialog("Record not exists.");
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            ShowAlertDialog(error.toString());
                                        }
                                    });
                        }else {
                            ShowAlertDialog(task.getException().getMessage());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });
    }

    protected void ShowAlertDialog(String stMessage){

        Dialog dialog = new Dialog(SignIn.this);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View view  = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        dialog.setContentView(view);

        TextView Message,btnAllow;
        ImageView ivAlert;
        Message=(TextView)view.findViewById(R.id.tvMessage);
        btnAllow=(TextView)view.findViewById(R.id.btn_allow);
        ivAlert = (ImageView) view.findViewById(R.id.imageView16) ;

        ivAlert.setImageResource(R.drawable.warning);
        ivAlert.setColorFilter(ContextCompat.getColor(this, R.color.dark_blue_700), android.graphics.PorterDuff.Mode.SRC_IN);

        Message.setText(stMessage);

        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });




        dialog.show();
    };

    public boolean isValidEmailAddress(EditText editText) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[^-][a-zA-Z]{2,}))$";
        if (editText.getText().toString().trim().matches(ePattern)) {
            return true;
        }
        ShowAlertDialog("Please Enter The Valid Email Address");
//        editText.setError("Please Enter The Valid Email Address");
        return false;
    }
}