package com.example.medicalannals.activities;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.AfternoonSlotsAdapter;
import com.example.medicalannals.adapters.EveningSlotsAdapter;
import com.example.medicalannals.adapters.MorningSlotsAdapter;
import com.example.medicalannals.adapters.SlotsBookedAdapter;
import com.example.medicalannals.adapters.ViewSlotsAdapter;
import com.example.medicalannals.models.BookTimeSlotModel;
import com.example.medicalannals.models.DoctorSlotsBookedModel;
import com.example.medicalannals.models.ViewSlotsModel;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DoctorDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    ConstraintLayout constraintViewMedicalRecords , constraintSetAppointments;
    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    ImageView toolbarDrawerImage;
    RecyclerView recyclerViewBookedSlots;
    ArrayList<DoctorSlotsBookedModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        initViews();
        clickListeners();
        disableNavigationViewScrollbars(navView);
        setRecyclerView();
    }

    private void clickListeners() {
        toolbarDrawerImage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
                drawerLayout.bringToFront();

            }
        });
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home_doc:
                        Intent intent = new Intent(DoctorDashboard.this , DoctorDashboard.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_profile_management_doc:
                        Intent medicalIntent = new Intent(DoctorDashboard.this , DoctorProfileManagement.class);
                        startActivity(medicalIntent);
                        finish();
                        break;
                    case R.id.nav_set_appointments:
                        Intent setAppointmnet = new Intent(DoctorDashboard.this , DoctorSlots.class);
                        startActivity(setAppointmnet);
                        finish();
                        break;

                    case R.id.nav_view_patient_medical_record:
                        Intent patientRecordIntent = new Intent(DoctorDashboard.this , ViewMedicalRecords.class);
                        startActivity(patientRecordIntent);
                        finish();
                        break;

                    case R.id.nav_logout_doc:
                        SignIn.patient = false;
                        SignIn.doctor = false;
                        Intent intentSignOut = new Intent(DoctorDashboard.this , SignIn.class);
                        startActivity(intentSignOut);
                        finish();
//                        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START); return true;
            }
        });
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view_doc);
        toolbar = findViewById(R.id.home_toolbar);
        toolbarDrawerImage = findViewById(R.id.toolbar_drawer_image_doctor);
        recyclerViewBookedSlots = findViewById(R.id.recycler_view_booked_slots);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private void setRecyclerView() {
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));
        arrayList.add(new DoctorSlotsBookedModel(R.drawable.default_profile_pic , "Ali Ahmed" , "Feb 11,2022" , "11:00 AM"));



        SlotsBookedAdapter adapter=new SlotsBookedAdapter(arrayList,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewBookedSlots.setLayoutManager(linearLayoutManager);
        recyclerViewBookedSlots.setAdapter(adapter);

    }
}