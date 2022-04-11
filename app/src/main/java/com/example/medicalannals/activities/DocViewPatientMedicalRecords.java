package com.example.medicalannals.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.DocPatientViewMedicalRecordAdapter;
import com.example.medicalannals.models.DocPatientViewMedicalRecordModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class DocViewPatientMedicalRecords extends AppCompatActivity {

    ImageView toolbarImageBackDocPatientRecord;
    ImageView ivDocPatientRecordCalender;
    EditText edSearchbarDocPatientRecord;
    private DatePickerDialog.OnDateSetListener docPatientRecordDateListener;
    long age;
    RecyclerView recyclerViewDocPatientMedicalRecord;
    ArrayList<DocPatientViewMedicalRecordModel> arrayList = new ArrayList<>();
    ProgressDialog progressDialog;
    private DocPatientViewMedicalRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_view_patient_medical_records);

        initViews();
        clickListeners();
        getDoctorViewPatientRecord();
    }

    private void getDoctorViewPatientRecord() {
        progressDialog.show();
        String uid = FirebaseAuth.getInstance().getUid();
        final Query doctorQuery = FirebaseDatabase.getInstance().getReference().child("Patient Records").orderByChild("doctorBookedSlotId");

        doctorQuery.equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.hide();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel = dataSnapshot.getValue(DocPatientViewMedicalRecordModel.class);
                    if (!docPatientViewMedicalRecordModel.getIsdeleted()) {
                        docPatientViewMedicalRecordModel.setPatientRecordId(dataSnapshot.getKey());
                        arrayList.add(docPatientViewMedicalRecordModel);
                    }
                }

                adapter = new DocPatientViewMedicalRecordAdapter(arrayList, DocViewPatientMedicalRecords.this);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(DocViewPatientMedicalRecords.this, 1);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewDocPatientMedicalRecord.setLayoutManager(gridLayoutManager);
                recyclerViewDocPatientMedicalRecord.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void clickListeners() {
        toolbarImageBackDocPatientRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                Intent i = new Intent(DocViewPatientMedicalRecords.this , DoctorDashboard.class);
//                startActivity(i);
//                finish();
            }
        });

/*        ivDocPatientRecordCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DocViewPatientMedicalRecords.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        docPatientRecordDateListener,
                        year, month, day);

                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        edSearchbarDocPatientRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = edSearchbarDocPatientRecord.getInputType(); // backup the input type
                edSearchbarDocPatientRecord.setInputType(InputType.TYPE_NULL); // disable soft input
                edSearchbarDocPatientRecord.onTouchEvent(event); // call native handler
                edSearchbarDocPatientRecord.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        docPatientRecordDateListener = new DatePickerDialog.OnDateSetListener() {
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
                edSearchbarDocPatientRecord.setText(date);
            }
        };*/


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
        progressDialog.setMessage("Loading...");
        toolbarImageBackDocPatientRecord = findViewById(R.id.toolbar_image_back_doc_patient_record);
        ivDocPatientRecordCalender = findViewById(R.id.iv_doc_patient_record_calender);
        edSearchbarDocPatientRecord = findViewById(R.id.ed_searchbar_doc_patient_record);
        recyclerViewDocPatientMedicalRecord = findViewById(R.id.recycler_view_doc_patient_medical_record);

        edSearchbarDocPatientRecord.addTextChangedListener(new TextWatcher() {

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