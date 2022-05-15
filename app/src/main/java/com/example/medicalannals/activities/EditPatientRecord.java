package com.example.medicalannals.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.example.medicalannals.R;
import com.example.medicalannals.models.DocPatientViewMedicalRecordModel;
import com.example.medicalannals.models.DoctorSlotsBookedModel;
import com.example.medicalannals.models.DoctorsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class EditPatientRecord extends AppCompatActivity {

    ImageView toolbarImageBackEditRecord;
    Button btnEditPatient;
    private DoctorSlotsBookedModel doctorSlotsBookedModel;
    private EditText appointmentDate, patientName;
    private EditText remarksEt, prescriptionEt;
    private DoctorsModel doctorsModel;
    private DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel;
    ProgressDialog progressDialog;
    ImageView ivPrescriptionPic;
    Uri prescriptionUri;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_record);

        storageReference = FirebaseStorage.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        doctorSlotsBookedModel = (DoctorSlotsBookedModel) bundle.getSerializable("PatientRecord");
        initViews();
        clickListeners();
        getDoctor();
        getPatientRecord();
    }


    private void getPatientRecord() {
        progressDialog.show();
        final Query userQuery = FirebaseDatabase.getInstance().getReference().child("Patient Records").orderByChild("appointmentKey");

        userQuery.equalTo(doctorSlotsBookedModel.getAppointmentKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.hide();
//                Toast.makeText(getApplicationContext() , "check", Toast.LENGTH_SHORT).show();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel = dsp.getValue(DocPatientViewMedicalRecordModel.class);
//                    if (doctorSlotsBookedModel.patientBookedSlotDate.equals(docPatientViewMedicalRecordModel.getTvDatePatientRecord()) &&
//                            doctorSlotsBookedModel.getDoctorBookedSlotId().equals(docPatientViewMedicalRecordModel.getDoctorBookedSlotId())) {

                        remarksEt.setText(docPatientViewMedicalRecordModel.getTvRemarksPatientRecord());
                        prescriptionEt.setText(docPatientViewMedicalRecordModel.getTvPrescriptionPatientRecord());
                        Glide.with(EditPatientRecord.this).load(docPatientViewMedicalRecordModel.getPrescription()).into(ivPrescriptionPic);

//                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deletePatientRecord() {
        progressDialog.show();
        final Query userQuery = FirebaseDatabase.getInstance().getReference().child("Patient Records").orderByChild("appointmentKey");

        userQuery.equalTo(doctorSlotsBookedModel.getAppointmentKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.hide();
//                Toast.makeText(getApplicationContext() , "check", Toast.LENGTH_SHORT).show();
                if (snapshot.exists()){
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        dsp.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                updateIfUserExist();
                            }
                        });
                    }

                }else {
                    updateIfUserExist();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateIfUserExist() {
        DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel = new DocPatientViewMedicalRecordModel();
        docPatientViewMedicalRecordModel.setTvPatientNamePatientRecord(doctorSlotsBookedModel.patientName);
        docPatientViewMedicalRecordModel.setPatientBookedSlotId(doctorSlotsBookedModel.patientBookedSlotId);
        docPatientViewMedicalRecordModel.setTvRemarksPatientRecord(remarksEt.getText().toString());
        docPatientViewMedicalRecordModel.setTvPrescriptionPatientRecord(prescriptionEt.getText().toString());
        docPatientViewMedicalRecordModel.setTvDocNamePatientRecord(doctorsModel.name);
        docPatientViewMedicalRecordModel.setDoctorBookedSlotId(doctorSlotsBookedModel.doctorBookedSlotId);
        docPatientViewMedicalRecordModel.setTvDatePatientRecord(doctorSlotsBookedModel.patientBookedSlotDate);
        docPatientViewMedicalRecordModel.setAppointmentKey(doctorSlotsBookedModel.getAppointmentKey());
        docPatientViewMedicalRecordModel.setPrescription(prescriptionUri.toString());

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
                            doctorsModel = snapshot.getValue(DoctorsModel.class);
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

                    deletePatientRecord();


                }
            }
        });

        ivPrescriptionPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPrescriptionPic.setImageBitmap(imageBitmap);
            uploadImage(imageBitmap);
        }
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
        ivPrescriptionPic = findViewById(R.id.iv_prescription_pic);

        patientName.setText(doctorSlotsBookedModel.getPatientName());
        appointmentDate.setText(doctorSlotsBookedModel.getPatientBookedSlotDate());


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
    }

    private void uploadImage(Bitmap bitmap) {
        if(bitmap != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String uid = FirebaseAuth.getInstance().getUid();
            StorageReference ref = storageReference.child("prescription/"+ uid);
/*            ref.putFile(bitmap)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(EditPatientRecord.this, "Uploaded...", Toast.LENGTH_SHORT).show();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {//add this method
                                @Override
                                public void onSuccess(Uri uri) {


                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditPatientRecord.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });*/

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = ref.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(EditPatientRecord.this, "Uploaded...", Toast.LENGTH_SHORT).show();

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {//add this method
                        @Override
                        public void onSuccess(Uri uri) {
                            prescriptionUri = uri;

                        }
                    });
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}