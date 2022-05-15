package com.example.medicalannals.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.medicalannals.R;
import com.example.medicalannals.models.PatientModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.Locale;
import java.util.UUID;

public class ProfileManagement extends AppCompatActivity {

    ImageView toolbarImageBackPatient,ivChangePass;
    private EditText etFullName,etAddress,etPhoneNumber;
    PatientModel patientModel;
    ConstraintLayout clChangePassword;
    TextView tvChangePatient;
    Uri sIUActionGetContent;
    ImageView ivPatientPic;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        storageReference = FirebaseStorage.getInstance().getReference();//added this line

        Intent i = getIntent();
        patientModel = (PatientModel)i.getSerializableExtra("patientModel");
        initViews();
        clickListeners();
    }

    private void uploadImage(Uri sIUActionGetContent) {
        if(sIUActionGetContent != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String uid = FirebaseAuth.getInstance().getUid();
            StorageReference ref = storageReference.child("images/"+ uid);
            ref.putFile(sIUActionGetContent)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileManagement.this, "Uploaded...", Toast.LENGTH_SHORT).show();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {//add this method
                                @Override
                                public void onSuccess(Uri uri) {
                                    patientModel.setPatientProfilePic(uri.toString());
                                    DatabaseReference reference = database.getReference("Patient");
                                    reference.child(uid).setValue(patientModel);
                                    Glide.with(ProfileManagement.this).load(patientModel.getPatientProfilePic()).into(ivPatientPic);

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileManagement.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void initViews() {
        toolbarImageBackPatient = findViewById(R.id.toolbar_image_back_patient);
        clChangePassword = findViewById(R.id.cl_change_password);
        ivChangePass = findViewById(R.id.iv_edit_password_patient);
        etFullName = findViewById(R.id.ed_full_name_patient);
        etAddress = findViewById(R.id.ed_email_address_patient);
        etPhoneNumber = findViewById(R.id.ed_phone_number_patient);
        tvChangePatient = findViewById(R.id.tv_change_patient);
        ivPatientPic = findViewById(R.id.iv_patient_profile_image);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Patient")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            patientModel = snapshot.getValue(PatientModel.class);
                            etFullName.setText(patientModel.getName());
                            etAddress.setText(patientModel.getEmail());
                            etPhoneNumber.setText(patientModel.getNumber());
                            Glide.with(ProfileManagement.this).load(patientModel.getPatientProfilePic()).into(ivPatientPic);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void clickListeners() {
        toolbarImageBackPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        clChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ProfileManagement.this , UpdatePassword.class);
                i.putExtra("email",patientModel.getEmail());
                startActivity(i);
            }
        });

        tvChangePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ProfileManagement.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileManagement.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(ProfileManagement.this, "Please Accept Permission", Toast.LENGTH_LONG).show();
                    } else {
                        ActivityCompat.requestPermissions(ProfileManagement.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                }
                imageChooserActionGetContent();
            }
        });
    }

    void imageChooserActionGetContent() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(i);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                sIUActionGetContent = result.getData().getData();
                    if (null != sIUActionGetContent) {
                        ivPatientPic.setImageURI(sIUActionGetContent);
                        uploadImage(sIUActionGetContent);
                    } else {
                        Toast.makeText(ProfileManagement.this, "Image not selected!", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    });
}