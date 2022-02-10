package com.example.medicalannals.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalannals.R;
import com.example.medicalannals.models.DoctorsModel;
import com.example.medicalannals.models.PatientModel;
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

public class SignUp extends AppCompatActivity {

    TextView tvSignInForAnAccount;
    TextInputEditText tiedUsernameSignUp, tiedEmailSignUp, tiedPasswordSignUp, tiedConfirmPasswordSignUp, tiedContactSignUp, tiedAgeSignUp, tiedGenderSignUp, tiedExperienceSignUp, tiedSpecializationSignUp, tiedFeeSignUp, tiedQualification;
    String stUsernameSignUp, stEmailSignUp, stPasswordSignUp, stConfirmPasswordSignUp, stContactSignUp, stAgeSignUp, stGenderSignUp, stSpecializationSignUp,stHospitalSignUp, stExperienceSignUp,stFeesSignUp,stQualificationSignUp;
    FirebaseAuth mAuth;
    ConstraintLayout constraintLayout;
    Button btnSignUp;

    ProgressDialog progressDialog;
    RadioButton rbDoctorSignUp, rbPatientSignUp;
    TextInputLayout tiExperienceSignUp, spinnerSpecialization, tiFeeSignUp, spinnerHospital, tiQualification;
    String[] type = new String[]{"Child Specialist", "Skin Specialist", "Psychiatrist", "Diabetes Specialist", "Eye Specialist", "Dentist", "ENT Specialist", "Kidney Specialist", "Heart Specialist", "Gastroenterologist"};
    String[] typeHospital = new String[]{"KRL Hospital", "Shifa International Hospital", "Bilal Hospital", "Ahmed Medical Complex", "Holy Family"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        visibility();
        clickListeners();
        setSpinners();

//        PatientModel patientModel = new PatientModel("Adnan", "zaheer.ahmed@appinsnap.com", "Male", 29, "03246159187");
//
//
//        String uid = "Adnan";
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("Patient");
//
//        reference.child(uid).setValue(patientModel);
////        reference.child(uid).child( "Email" ).setValue("Email");
////        reference.child(uid).child( "Contact" ).setValue("Contact");
////        reference.child(uid).child( "Id" ).setValue(uid);
////        reference.child(uid).child( "Age" ).setValue("Age");
////        reference.child(uid).child( "Department" ).setValue("Department");
////        activity.startActivity(new Intent(activity, MainActivity.class));
//
//        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
//        dref.child("Users").child("zaheer").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
////                    PatientModel patientModel1 = dataSnapshot.getValue(PatientModel.class);
////                    Toast.makeText(getApplicationContext(), patientModel1.getName(), Toast.LENGTH_LONG).show();
//
//                    String name = dataSnapshot.child("Name").getValue().toString();
//                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "No notice found.", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    private void setSpinners() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        type);

        AutoCompleteTextView editTextFilledExposedDropdown =
                findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                stSpecializationSignUp = type[position];
            }
        });

        ArrayAdapter<String> adapterHospital =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        typeHospital);

        AutoCompleteTextView editTextFilledExposedDropdownHospital =
                findViewById(R.id.filled_exposed_dropdown_hospital);
        editTextFilledExposedDropdownHospital.setAdapter(adapterHospital);
        editTextFilledExposedDropdownHospital.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stHospitalSignUp = typeHospital[position];
            }
        });


    }

    private void visibility() {
        if (rbPatientSignUp.isChecked() == true) {
            tiedExperienceSignUp.setVisibility(View.GONE);
//            tiedSpecializationSignUp.setVisibility(View.GONE);
            tiExperienceSignUp.setVisibility(View.GONE);
            spinnerSpecialization.setVisibility(View.GONE);
            spinnerHospital.setVisibility(View.GONE);
            tiedFeeSignUp.setVisibility(View.GONE);
            tiFeeSignUp.setVisibility(View.GONE);
            tiedQualification.setVisibility(View.GONE);
            tiQualification.setVisibility(View.GONE);
        } else if (rbDoctorSignUp.isChecked() == true) {
            tiedExperienceSignUp.setVisibility(View.VISIBLE);
//            tiedSpecializationSignUp.setVisibility(View.VISIBLE);
            tiExperienceSignUp.setVisibility(View.VISIBLE);
            spinnerSpecialization.setVisibility(View.VISIBLE);
            spinnerHospital.setVisibility(View.VISIBLE);
            tiedFeeSignUp.setVisibility(View.VISIBLE);
            tiFeeSignUp.setVisibility(View.VISIBLE);
            tiedQualification.setVisibility(View.VISIBLE);
            tiQualification.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
        tvSignInForAnAccount = findViewById(R.id.tv_sign_in_for_an_account);
        tiedUsernameSignUp = findViewById(R.id.tied_username_sign_up);
        tiedEmailSignUp = findViewById(R.id.tied_email_sign_up);
        tiedPasswordSignUp = findViewById(R.id.tied_password_sign_up);
        tiedConfirmPasswordSignUp = findViewById(R.id.tied_confirm_password_sign_up);
        tiedContactSignUp = findViewById(R.id.tied_contact_sign_up);
        tiedAgeSignUp = findViewById(R.id.tied_age_sign_up);
        tiedGenderSignUp = findViewById(R.id.tied_gender_sign_up);
        tiedExperienceSignUp = findViewById(R.id.tied_experience_sign_up);
        rbDoctorSignUp = findViewById(R.id.rb_doctor_sign_up);
        rbPatientSignUp = findViewById(R.id.rb_patient_sign_up);
        tiExperienceSignUp = findViewById(R.id.ti_experience_sign_up);
        spinnerSpecialization = findViewById(R.id.spinner_specialization);
        tiedFeeSignUp = findViewById(R.id.tied_fee_sign_up);
        tiFeeSignUp = findViewById(R.id.ti_fee_sign_up);
        spinnerHospital = findViewById(R.id.spinner_hospital);
        tiedQualification = findViewById(R.id.tied_qualification_sign_up);
        tiQualification = findViewById(R.id.ti_qualification_sign_up);

        btnSignUp = findViewById(R.id.btn_sign_up);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering..... ");
    }

    private void clickListeners() {
        tvSignInForAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SignUp.this, SignIn.class);
                startActivity(i);

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbPatientSignUp.isChecked()) {
                    if (!checkFields()) {
                        progressDialog.show();
                        PatientModel patientModel = new PatientModel(stUsernameSignUp, stEmailSignUp, stGenderSignUp, stAgeSignUp, stContactSignUp);
                        RegisterPatient(patientModel, stPasswordSignUp);
                    }
                } else {
                    if (!checkFields()) {
                        progressDialog.show();
                        DoctorsModel doctorsModel = new DoctorsModel(stUsernameSignUp, stEmailSignUp, stContactSignUp,stAgeSignUp,stGenderSignUp,stSpecializationSignUp,stHospitalSignUp,stExperienceSignUp,stQualificationSignUp,stFeesSignUp);
                        RegisterDoctor(doctorsModel, stPasswordSignUp);
                    }
                }

            }
        });
        rbDoctorSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiedExperienceSignUp.setVisibility(View.VISIBLE);
//                tiedSpecializationSignUp.setVisibility(View.VISIBLE);
                tiExperienceSignUp.setVisibility(View.VISIBLE);
                spinnerSpecialization.setVisibility(View.VISIBLE);
                spinnerHospital.setVisibility(View.VISIBLE);
                tiedFeeSignUp.setVisibility(View.VISIBLE);
                tiFeeSignUp.setVisibility(View.VISIBLE);
                tiedQualification.setVisibility(View.VISIBLE);
                tiQualification.setVisibility(View.VISIBLE);
            }
        });
        rbPatientSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiedExperienceSignUp.setVisibility(View.GONE);
//                tiedSpecializationSignUp.setVisibility(View.GONE);
                tiExperienceSignUp.setVisibility(View.GONE);
                spinnerSpecialization.setVisibility(View.GONE);
                spinnerHospital.setVisibility(View.GONE);
                tiedFeeSignUp.setVisibility(View.GONE);
                tiFeeSignUp.setVisibility(View.GONE);
                tiedQualification.setVisibility(View.GONE);
                tiQualification.setVisibility(View.GONE);
            }
        });

    }

    private boolean checkFields() {
        tiedUsernameSignUp.setError(null);
        tiedEmailSignUp.setError(null);
        tiedPasswordSignUp.setError(null);
        tiedConfirmPasswordSignUp.setError(null);
        tiedContactSignUp.setError(null);
        tiedAgeSignUp.setError(null);
        tiedGenderSignUp.setError(null);

        boolean cancel = false;
        View focusView = null;

        stUsernameSignUp = tiedUsernameSignUp.getText().toString().toLowerCase().trim();
        stEmailSignUp = tiedEmailSignUp.getText().toString().toLowerCase().trim();
        stPasswordSignUp = tiedPasswordSignUp.getText().toString().trim();
        stConfirmPasswordSignUp = tiedConfirmPasswordSignUp.getText().toString().trim();
        stContactSignUp = tiedContactSignUp.getText().toString().trim();
        stAgeSignUp = tiedAgeSignUp.getText().toString().trim();
        stGenderSignUp = tiedGenderSignUp.getText().toString().trim();
        stExperienceSignUp = tiedExperienceSignUp.getText().toString().trim();
        stFeesSignUp = tiedFeeSignUp.getText().toString().trim();
        stQualificationSignUp = tiedQualification.getText().toString().trim();
//        stSpecializationSignUp = tiedSpecializationSignUp.getText().toString().trim();

        if (TextUtils.isEmpty(stUsernameSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Username", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedUsernameSignUp;
            cancel = true;
        } else if (TextUtils.isEmpty(stEmailSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Email", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedEmailSignUp;
            cancel = true;
        } else if (TextUtils.isEmpty(stPasswordSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Password", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedPasswordSignUp;
            cancel = true;
        } else if (tiedPasswordSignUp.length() < 8) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Password too short", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedPasswordSignUp;
            cancel = true;
        } else if (TextUtils.isEmpty(stConfirmPasswordSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Confirm Password", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedConfirmPasswordSignUp;
            cancel = true;
        } else if (tiedContactSignUp.length() < 8) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Contact Number", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedContactSignUp;
            cancel = true;
        }else if (tiedConfirmPasswordSignUp.length() < 8) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Password too short", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedConfirmPasswordSignUp;
            cancel = true;
        }  else if (tiedAgeSignUp.length() < 1) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Age", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedAgeSignUp;
            cancel = true;
        } else if (TextUtils.isEmpty(stGenderSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Gender", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedGenderSignUp;
            cancel = true;
        }else if (rbDoctorSignUp.isChecked()&&TextUtils.isEmpty(stSpecializationSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Specialization", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = spinnerSpecialization;
            cancel = true;
        } else if (rbDoctorSignUp.isChecked()&&TextUtils.isEmpty(stHospitalSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Hospital", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = spinnerHospital;
            cancel = true;
        }else if (rbDoctorSignUp.isChecked()&&TextUtils.isEmpty(stExperienceSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Experience", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedExperienceSignUp;
            cancel = true;
        }else if (rbDoctorSignUp.isChecked()&&TextUtils.isEmpty(stFeesSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Fees", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedFeeSignUp;
            cancel = true;
        }else if (rbDoctorSignUp.isChecked()&&TextUtils.isEmpty(stQualificationSignUp)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Enter Qualification", Snackbar.LENGTH_LONG);
            snackbar.show();
            focusView = tiedQualification;
            cancel = true;
        }


        if (cancel) {

            focusView.requestFocus();

        }

        return cancel;

    }

    private void authenticateUser() {
/*        mAuth.createUserWithEmailAndPassword(stEmailAddressSignUp,stPasswordSignUp).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Snackbar snackbar = Snackbar
                            .make(constraintLayout, "Invalid Email or Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    startActivity(new Intent(SignUp.this, SignInActivity.class));
                    finish();
                }
            }
        });*/
    }

    public void RegisterPatient(PatientModel patientModel, String Password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(patientModel.getEmail(), Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference("Patient");
                            reference.child(uid).setValue(patientModel);

                            Intent intent = new Intent(SignUp.this, PatientDashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
//                            activity.startActivity(new Intent(activity, MainActivity.class));
//                            Toast.makeText(activity, "Account Created", Toast.LENGTH_SHORT).show();
//                            activity.finish();
//                            progressDialog.dismiss();


                        } else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(SignUp.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void RegisterDoctor(DoctorsModel doctorsModel, String Password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(doctorsModel.getEmail(), Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference("Doctor");
                            reference.child(uid).setValue(doctorsModel);

                            Intent intent = new Intent(SignUp.this, PatientDashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(SignUp.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}