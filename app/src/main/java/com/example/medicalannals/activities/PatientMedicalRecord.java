package com.example.medicalannals.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.PatientMedicalRecordAdapter;
import com.example.medicalannals.models.DocPatientViewMedicalRecordModel;
import com.example.medicalannals.models.PatientMedicalRecordModel;
import com.example.medicalannals.models.PatientModel;
import com.example.medicalannals.models.PatientRecordModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class PatientMedicalRecord extends AppCompatActivity {

    ImageView toolbarImageBackPatientRecord;
    ImageView ivPatientRecordSearch;
    EditText edSearchbarPatientRecord;
    long age;
    PatientModel patientModel;
    RecyclerView recyclerViewPatientMedicalRecord;
    ArrayList<DocPatientViewMedicalRecordModel> arrayList = new ArrayList<>();
    ProgressDialog progressDialog;
    private PatientMedicalRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medical_record);

        initViews();
        clickListeners();
        setRecyclerView();
        getPatient();
        getPatientRecord();
    }

    private void getPatientRecord() {
        progressDialog.show();
        String uid = FirebaseAuth.getInstance().getUid();
        final Query patientQuery = FirebaseDatabase.getInstance().getReference().child("Patient Records").orderByChild("patientBookedSlotId");
        patientQuery.equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.hide();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    DocPatientViewMedicalRecordModel patientRecordModel = dataSnapshot.getValue(DocPatientViewMedicalRecordModel.class);
                    arrayList.add(patientRecordModel);
                }
                adapter=new PatientMedicalRecordAdapter(arrayList,PatientMedicalRecord.this);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(PatientMedicalRecord.this,1);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewPatientMedicalRecord.setLayoutManager(gridLayoutManager);
                recyclerViewPatientMedicalRecord.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPatient() {

    }

    private void setRecyclerView() {



    }

    private void clickListeners() {
        toolbarImageBackPatientRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });








    }

    private long getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        int ageS = ageInt;
        return ageS;
    }


    private void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        toolbarImageBackPatientRecord = findViewById(R.id.toolbar_image_back_patient_record);
        ivPatientRecordSearch = findViewById(R.id.iv_patient_record_search);
        edSearchbarPatientRecord = findViewById(R.id.ed_searchbar_patient_record);
        recyclerViewPatientMedicalRecord = findViewById(R.id.recycler_view_patient_medical_record);
        edSearchbarPatientRecord.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"]");

                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}