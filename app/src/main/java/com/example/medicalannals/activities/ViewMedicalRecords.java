package com.example.medicalannals.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.PatientRecordAdapter;
import com.example.medicalannals.models.PatientRecordModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class ViewMedicalRecords extends AppCompatActivity {
    RecyclerView recyclerViewPatientRecord;
    Toolbar toolbar;
    ImageView toolbarBackImage;
    Adapter patientRecordAdapter;
    ArrayList<PatientRecordModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_records);
        initViews();
        clickListeners();
        setRecyclerView();
        }

    private void setRecyclerView() {
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Talha Dogar"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Ahmed Bilal"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Ali Afzal"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Hashim Shaheen"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Syed Waqar"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Muhammad Umer"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Sheryar Munawar"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Hadeed Ramzan"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Sachal Zaman"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Daniyal Azeem"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Saqib Bashir"));
        arrayList.add(new PatientRecordModel(R.drawable.default_profile_pic ,  "Awais Zohar"));


        PatientRecordAdapter adapter=new PatientRecordAdapter(arrayList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPatientRecord.setLayoutManager(gridLayoutManager);
        recyclerViewPatientRecord.setAdapter(adapter);

    }

    private void clickListeners() {
        toolbarBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewMedicalRecords.this , DoctorDashboard.class);
                startActivity(i);
            }
        });
    }

    private void initViews() {
        recyclerViewPatientRecord = findViewById(R.id.recycler_view_patient_record);
        toolbar = findViewById(R.id.home_toolbar);
        toolbarBackImage = findViewById(R.id.toolbar_iv_back);
    }
}