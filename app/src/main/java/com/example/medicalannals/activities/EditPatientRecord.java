package com.example.medicalannals.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medicalannals.R;
import com.example.medicalannals.models.DocPatientViewMedicalRecordModel;
import com.example.medicalannals.models.DoctorSlotsBookedModel;
import com.example.medicalannals.models.DoctorsModel;
import com.example.medicalannals.models.PatientModel;
import com.example.medicalannals.models.SlotsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditPatientRecord extends AppCompatActivity {

    ImageView toolbarImageBackEditRecord;
    Button btnEditPatient;
    private DoctorSlotsBookedModel doctorSlotsBookedModel;
    private EditText appointmentDate, patientName;
    private EditText remarksEt, prescriptionEt;
    private DoctorsModel doctorsModel;
    private DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_record);
        Bundle bundle = getIntent().getExtras();
        doctorSlotsBookedModel = (DoctorSlotsBookedModel) bundle.getSerializable("PatientRecord");
        initViews();
        clickListeners();
        getDoctor();
        getPatientRecord();
    }


    private void getPatientRecord() {
        progressDialog.show();
        final Query userQuery = FirebaseDatabase.getInstance().getReference().child("Patient Records").orderByChild("patientBookedSlotId");

        userQuery.equalTo(doctorSlotsBookedModel.getPatientBookedSlotId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.hide();
//                Toast.makeText(getApplicationContext() , "check", Toast.LENGTH_SHORT).show();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel = dsp.getValue(DocPatientViewMedicalRecordModel.class);
                    if (doctorSlotsBookedModel.patientBookedSlotDate.equals(docPatientViewMedicalRecordModel.getTvDatePatientRecord()) &&
                            doctorSlotsBookedModel.getDoctorBookedSlotId().equals(docPatientViewMedicalRecordModel.getDoctorBookedSlotId())) {

                        remarksEt.setText(docPatientViewMedicalRecordModel.getTvRemarksPatientRecord());
                        prescriptionEt.setText(docPatientViewMedicalRecordModel.getTvPrescriptionPatientRecord());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDoctor() {
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Doctor")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            docPatientViewMedicalRecordModel = snapshot.getValue(DocPatientViewMedicalRecordModel.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
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
                if (!checkFields()) {
                    progressDialog.show();
                    DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel = new DocPatientViewMedicalRecordModel();
                    docPatientViewMedicalRecordModel.setTvPatientNamePatientRecord(doctorSlotsBookedModel.patientName);
                    docPatientViewMedicalRecordModel.setPatientBookedSlotId(doctorSlotsBookedModel.patientBookedSlotId);
                    docPatientViewMedicalRecordModel.setTvRemarksPatientRecord(remarksEt.getText().toString());
                    docPatientViewMedicalRecordModel.setTvPrescriptionPatientRecord(prescriptionEt.getText().toString());
                    docPatientViewMedicalRecordModel.setTvDocNamePatientRecord(doctorsModel.name);
                    docPatientViewMedicalRecordModel.setDoctorBookedSlotId(doctorSlotsBookedModel.doctorBookedSlotId);
                    docPatientViewMedicalRecordModel.setTvDatePatientRecord(doctorSlotsBookedModel.patientBookedSlotDate);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("Patient Records");
                    reference.push().setValue(docPatientViewMedicalRecordModel).addOnCompleteListener(task -> {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            showDialog();
                        } else {
                            Toast.makeText(EditPatientRecord.this, "Please Try again", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {

                }
            }
        });
    }


    private boolean checkFields() {
        remarksEt.setError(null);
        prescriptionEt.setError(null);

        String stRemarks = remarksEt.getText().toString().trim();
        String stPrescription = prescriptionEt.getText().toString().trim();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(stRemarks)) {
            ShowAlertDialog("Please Add Remarks");
            focusView = remarksEt;
            cancel = true;
        } else if (TextUtils.isEmpty(stPrescription)) {
            ShowAlertDialog("Please Add Prescription");
            focusView = prescriptionEt;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;

    }


    protected void ShowAlertDialog(String stMessage) {

        Dialog dialog = new Dialog(EditPatientRecord.this);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View view = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        dialog.setContentView(view);

        TextView Message, btnAllow;
        ImageView ivAlert;
        Message = (TextView) view.findViewById(R.id.tvMessage);
        btnAllow = (TextView) view.findViewById(R.id.btn_allow);
        ivAlert = (ImageView) view.findViewById(R.id.imageView16);

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
    }

    private void showDialog() {
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
                onBackPressed();
            }
        });

        dialog.show();
    }

    private void initViews() {
        toolbarImageBackEditRecord = findViewById(R.id.toolbar_image_back_edit_record);
        btnEditPatient = findViewById(R.id.btn_edit_patient);
        patientName = findViewById(R.id.patientName);
        appointmentDate = findViewById(R.id.appointmentDate);
        remarksEt = findViewById(R.id.tied_remarks_edit_patient);
        prescriptionEt = findViewById(R.id.tied_prescription_edit_patient);

        patientName.setText(doctorSlotsBookedModel.getPatientName());
        appointmentDate.setText(doctorSlotsBookedModel.getPatientBookedSlotDate());


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
    }
}