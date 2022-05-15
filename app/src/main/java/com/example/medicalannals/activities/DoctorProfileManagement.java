package com.example.medicalannals.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.medicalannals.models.DoctorsModel;
import com.example.medicalannals.models.PatientModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfileManagement extends AppCompatActivity {

    ImageView toolbarImageBack ;
    EditText edFullName, edEmail, phoneNumber, childSpecialist, qualification;
    ConstraintLayout constraintLayout;
    DoctorsModel doctorsModel;
    CircleImageView ivDoctorProfilePic;
    TextView tvChangeDoc;
    Uri sIUActionGetContent;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_management);

        storageReference = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        doctorsModel = (DoctorsModel)intent.getSerializableExtra("doctorModel");
        initViews();
        clickListeners();
    }

    private void initViews() {
        toolbarImageBack = findViewById(R.id.toolbar_image_back);
        edFullName = findViewById(R.id.ed_full_name);
        edEmail = findViewById(R.id.ed_email_address);
        phoneNumber = findViewById(R.id.ed_phone_number);
        childSpecialist = findViewById(R.id.ed_specialization);
        qualification = findViewById(R.id.ed_qualification);
        constraintLayout = findViewById(R.id.cl_change_password);
        tvChangeDoc = findViewById(R.id.tv_change_doc);
        ivDoctorProfilePic = findViewById(R.id.iv_doc_pic);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Doctor")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            doctorsModel = snapshot.getValue(DoctorsModel.class);
                            edFullName.setText(doctorsModel.name);
                            edEmail.setText(doctorsModel.email);
                            phoneNumber.setText(doctorsModel.contact);
                            childSpecialist.setText(doctorsModel.specialization);
                            qualification.setText(doctorsModel.qualification);

                            Glide.with(DoctorProfileManagement.this).load(doctorsModel.getDoctorProfilePic()).into(ivDoctorProfilePic);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

    }

    private void clickListeners() {
        toolbarImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DoctorProfileManagement.this, DoctorUpdatePassword.class);
                intent.putExtra("email",doctorsModel.getEmail());
                startActivity(intent);
            }
        });
        tvChangeDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(DoctorProfileManagement.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(DoctorProfileManagement.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(DoctorProfileManagement.this, "Please Accept Permission", Toast.LENGTH_LONG).show();
                    } else {
                        ActivityCompat.requestPermissions(DoctorProfileManagement.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
                    ivDoctorProfilePic.setImageURI(sIUActionGetContent);
                    uploadImage(sIUActionGetContent);
                } else {
                    Toast.makeText(DoctorProfileManagement.this, "Image not selected!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

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
                            Toast.makeText(DoctorProfileManagement.this, "Uploaded...", Toast.LENGTH_SHORT).show();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {//add this method
                                @Override
                                public void onSuccess(Uri uri) {
                                    doctorsModel.setDoctorProfilePic(uri.toString());
                                    DatabaseReference reference = database.getReference("Doctor");
                                    reference.child(uid).setValue(doctorsModel);
                                    Glide.with(DoctorProfileManagement.this).load(doctorsModel.getDoctorProfilePic()).into(ivDoctorProfilePic);

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(DoctorProfileManagement.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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