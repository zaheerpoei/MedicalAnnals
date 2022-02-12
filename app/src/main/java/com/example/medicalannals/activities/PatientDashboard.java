package com.example.medicalannals.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.SearchDoctorTypeAdapter;
import com.example.medicalannals.models.SearchSpecialistModel;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class PatientDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    Menu menu;
    ImageView toolbarDrawerImage;
    RecyclerView recyclerView;
    ArrayList<SearchSpecialistModel> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        initViews();
        clickListeners();
        setRecyclerView();
        disableNavigationViewScrollbars(navView);}


    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    private void setRecyclerView() {
        arrayList.add(new SearchSpecialistModel(R.drawable.childspecialist ,  "Child Specialist"));
        arrayList.add(new SearchSpecialistModel(R.drawable.skin ,  "Skin Specialist"));
        arrayList.add(new SearchSpecialistModel(R.drawable.physco ,  "Psychiatrist"));
        arrayList.add(new SearchSpecialistModel(R.drawable.diabetes ,  "Diabetes Specialist"));
        arrayList.add(new SearchSpecialistModel(R.drawable.eye ,  "Eye Specialist"));
        arrayList.add(new SearchSpecialistModel(R.drawable.dentist ,  "Dentist"));
        arrayList.add(new SearchSpecialistModel(R.drawable.ent ,  "ENT Specialist"));
        arrayList.add(new SearchSpecialistModel(R.drawable.kidney ,  "Kidney Specialist"));
        arrayList.add(new SearchSpecialistModel(R.drawable.heart ,  "Heart Specialist"));
        arrayList.add(new SearchSpecialistModel(R.drawable.gas ,  "Gastroenterologist"));

        SearchDoctorTypeAdapter adapter=new SearchDoctorTypeAdapter(arrayList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.home_toolbar);
        toolbarDrawerImage = findViewById(R.id.toolbar_drawer_image);
        recyclerView = findViewById(R.id.recycler_view_search_type);
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
                    case R.id.nav_home:
                        Intent intent = new Intent(PatientDashboard.this , PatientDashboard.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_medical_record:
                        Intent intentMedical = new Intent(PatientDashboard.this , PatientMedicalRecord.class);
                        startActivity(intentMedical);
                        finish();
                        break;
                    case R.id.nav_profile_management:
                        Intent i = new Intent(PatientDashboard.this , ProfileManagement.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        SignIn.patient = false;
                        SignIn.doctor = false;
                        Intent intentSignOut = new Intent(PatientDashboard.this , SignIn.class);
                        startActivity(intentSignOut);
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START); return true;
            }
        });
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}