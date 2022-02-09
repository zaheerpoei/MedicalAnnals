package com.example.medicalannals.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class SignIn extends AppCompatActivity {
    TextInputEditText tiedEmailAddress, tiedPassword , tiedPhoneNumber;
    TextView tvForgotPassword , tvSignUpForAnAccount;
    TextInputLayout tiPhoneNumber, tiPassword  , tiEmailAddress ;
    Button btnLogin;
    FirebaseAuth mAuth;
    ConstraintLayout constraintLayout;
    String  stPhoneNumber,stPassword;
    RadioButton rbDoctor , rbPatient;
    public static Boolean  doctor = false , patient = false;

    ProgressDialog progressDialog;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        clickListeners();
    }

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
                if(rbPatient.isChecked() == true){
                    progressDialog.show();
                    LoginPatient("ibra.noor@appinsnap.com","ibra.noor");
                }
//                    startActivity(new Intent(SignIn.this, PatientDashboard.class));}
                else if(rbDoctor.isChecked() == true){
                        startActivity(new Intent(SignIn.this, DoctorDashboard.class));
                }

//                if(!checkFields()){
//                    authenticateUser();
//                }
            }
        });

        tvSignUpForAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SignIn.this , SignUp.class);
                startActivity(i);

            }
        });

        rbDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        rbPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private boolean checkFields()
    {
        tiedPhoneNumber.setError(null);
        tiedPassword.setError(null);

        boolean cancel = false;
        View focusView = null;

        stPhoneNumber = tiedPhoneNumber.getText().toString().toLowerCase().trim();
        stPassword = tiedPassword.getText().toString().trim();

        if (TextUtils.isEmpty(stPhoneNumber)) {
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, "Enter Phone Number", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedPhoneNumber;
            cancel = true;
        }

        else if (TextUtils.isEmpty(stPassword)) {
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, "Enter Password", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedPassword;
            cancel = true;
        }
        else if (tiedPassword.length() < 8) {
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, "Password too short", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedPassword;
            cancel = true;
        }
        if (cancel) {

            focusView.requestFocus();

        }

        return cancel;

    }
    private void authenticateUser()
    {
        mAuth.signInWithEmailAndPassword(stPhoneNumber,stPassword).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Snackbar snackbar = Snackbar
                            .make(constraintLayout, "Invalid Phone Number or Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    public void LoginPatient(String EMAIL, String PASSWORD) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(SignIn.this, PatientDashboard.class));
                            finish();
                            progressDialog.dismiss();

                        }else {
                            Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignIn.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}