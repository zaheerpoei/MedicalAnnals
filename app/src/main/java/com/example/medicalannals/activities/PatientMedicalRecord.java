package com.example.medicalannals.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
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
    ImageView ivPatientRecordCalender;
    EditText edSearchbarPatientRecord;
    private DatePickerDialog.OnDateSetListener patientRecordDateListener;
    long age;
    PatientModel patientModel;
    RecyclerView recyclerViewPatientMedicalRecord;
    ArrayList<DocPatientViewMedicalRecordModel> arrayList = new ArrayList<>();
    ProgressDialog progressDialog;

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
                PatientMedicalRecordAdapter adapter=new PatientMedicalRecordAdapter(arrayList,PatientMedicalRecord.this);
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

      /*  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Patient")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            patientModel = snapshot.getValue(PatientModel.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
    }

    private void setRecyclerView() {
/*
        arrayList.add(new PatientMedicalRecordModel("Ahmed Bilal" , "Feb 14,2022" , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop." , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop."));
        arrayList.add(new PatientMedicalRecordModel("Ahmed Bilal" , "Feb 14,2022" , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop." , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop."));
        arrayList.add(new PatientMedicalRecordModel("Ahmed Bilal" , "Feb 14,2022" , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop." , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop."));
        arrayList.add(new PatientMedicalRecordModel("Ahmed Bilal" , "Feb 14,2022" , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop." , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop."));
        arrayList.add(new PatientMedicalRecordModel("Ahmed Bilal" , "Feb 14,2022" , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop." , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop."));
        arrayList.add(new PatientMedicalRecordModel("Ahmed Bilal" , "Feb 14,2022" , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop." , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop."));
        arrayList.add(new PatientMedicalRecordModel("Ahmed Bilal" , "Feb 14,2022" , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop." , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop."));
*/



    }

    private void clickListeners() {
        toolbarImageBackPatientRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ivPatientRecordCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PatientMedicalRecord.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        patientRecordDateListener,
                        year, month, day);

                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        edSearchbarPatientRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = edSearchbarPatientRecord.getInputType(); // backup the input type
                edSearchbarPatientRecord.setInputType(InputType.TYPE_NULL); // disable soft input
                edSearchbarPatientRecord.onTouchEvent(event); // call native handler
                edSearchbarPatientRecord.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        patientRecordDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                Log.d("test", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String Days = "";
                if (day < 10) {
                    Days = "0" + day;
                } else {
                    Days = String.valueOf(day);
                }

                String mont = "";
                if (month < 10) {
                    mont = "0" + month;
                } else {
                    mont = String.valueOf(month);
                }

                String date = Days + "-" + mont + "-" + year;

                age = getAge(year, month, day);
                // Toast.makeText(getActivity(), String.valueOf(age), Toast.LENGTH_SHORT).show();
                edSearchbarPatientRecord.setText(date);
            }
        };


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
        ivPatientRecordCalender = findViewById(R.id.iv_patient_record_calender);
        edSearchbarPatientRecord = findViewById(R.id.ed_searchbar_patient_record);
        recyclerViewPatientMedicalRecord = findViewById(R.id.recycler_view_patient_medical_record);
    }
}