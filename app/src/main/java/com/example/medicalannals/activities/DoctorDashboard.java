package com.example.medicalannals.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.SlotsBookedAdapter;
import com.example.medicalannals.models.DoctorSlotsBookedModel;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class DoctorDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    ConstraintLayout constraintViewMedicalRecords , constraintSetAppointments;
    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    ImageView toolbarDrawerImage , ivCalenderDoctorsDashboard;
    RecyclerView recyclerViewBookedSlots;
    EditText edSearchbarDateDoctorDashboard;
    ArrayList<DoctorSlotsBookedModel> arrayList = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener doctorDashboardDateListener;
    long age;

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
                        Intent patientRecordIntent = new Intent(DoctorDashboard.this , DocViewPatientMedicalRecords.class);
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
        ivCalenderDoctorsDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DoctorDashboard.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        doctorDashboardDateListener,
                        year, month, day);

                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        edSearchbarDateDoctorDashboard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = edSearchbarDateDoctorDashboard.getInputType(); // backup the input type
                edSearchbarDateDoctorDashboard.setInputType(InputType.TYPE_NULL); // disable soft input
                edSearchbarDateDoctorDashboard.onTouchEvent(event); // call native handler
                edSearchbarDateDoctorDashboard.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        doctorDashboardDateListener = new DatePickerDialog.OnDateSetListener() {
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
                edSearchbarDateDoctorDashboard.setText(date);
            }
        };
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view_doc);
        toolbar = findViewById(R.id.home_toolbar);
        toolbarDrawerImage = findViewById(R.id.toolbar_drawer_image_doctor);
        recyclerViewBookedSlots = findViewById(R.id.recycler_view_booked_slots);
        ivCalenderDoctorsDashboard = findViewById(R.id.iv_calender_doctors_dashboard);
        edSearchbarDateDoctorDashboard = findViewById(R.id.ed_searchbar_date_doctor_dashboard);
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
}