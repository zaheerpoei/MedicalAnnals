package com.example.medicalannals.activities;

import android.Manifest;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.medicalannals.R;
import com.example.medicalannals.models.PatientModel;

import java.net.URI;

public class ProfileManagement extends AppCompatActivity {

    ImageView toolbarImageBackPatient,ivChangePass;
    private EditText etFullName,etAddress,etPhoneNumber;
    PatientModel patientModel;
    ConstraintLayout clChangePassword;
    TextView tvChangePatient;
    Uri sIUActionGetContent;
    ImageView ivPatientPic;
    //these two below lines
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Intent i = getIntent();
        patientModel = (PatientModel)i.getSerializableExtra("patientModel");
        initViews();
        clickListeners();
        Uri getUri = Uri.parse(sharedpreferences.getString("sIUActionGetContent", ""));
        //this if else statement
        if (sharedpreferences.contains("sIUActionGetContent")) {
//            Toast.makeText(ProfileManagement.this, "Image  selected!", Toast.LENGTH_SHORT).show();
            if (null != getUri) {
                ivPatientPic.setImageURI(getUri);
            } else {
                Toast.makeText(ProfileManagement.this, "Image not selected!", Toast.LENGTH_SHORT).show();
            }
//            if (null != sIUActionGetContent) {

//                ivPatientPic.setImageURI(getUri);
//            } else {
//                Toast.makeText(ProfileManagement.this, "Image not selected!", Toast.LENGTH_SHORT).show();
//            }
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
        ivPatientPic = findViewById(R.id.iv_patient_pic);

        etFullName.setText(patientModel.getName());
        etAddress.setText(patientModel.getEmail());
        etPhoneNumber.setText(patientModel.getNumber());
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
//        i.putExtra(i.EXTRA_ALLOW_MULTIPLE,true);    //for picking multiple images
        i.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(i);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                sIUActionGetContent = result.getData().getData();
                //these two
                Log.d("sIUActionGetContent", sIUActionGetContent.toString());
                editor.putString("sIUActionGetContent", sIUActionGetContent.toString());
                editor.commit();
                //this
                String getUri = sharedpreferences.getString("sIUActionGetContent", "");
                //this if else statement
//                if (getUri.contains("sIUActionGetContent")) {
//                    if (null != sIUActionGetContent) {
//                        ivPatientPic.setImageURI(sIUActionGetContent);
//                    } else {
//                        Toast.makeText(ProfileManagement.this, "Image not selected!", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
                    if (null != sIUActionGetContent) {
                        ivPatientPic.setImageURI(sIUActionGetContent);
                    } else {
                        Toast.makeText(ProfileManagement.this, "Image not selected!", Toast.LENGTH_SHORT).show();
                    }
//                }
            }
        }
    });
}