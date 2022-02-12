package com.example.medicalannals.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.DoctorsAdapter;
import com.example.medicalannals.models.DoctorsModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    Menu menu;
    ImageView toolbarDrawerImage;
    RecyclerView recyclerView;
    ArrayList<DoctorsModel> arrayList = new ArrayList<>();
    ArrayList<DoctorsModel> userList= new ArrayList<DoctorsModel>();
    DoctorsModel doctorsModel = new DoctorsModel();
    String specializationValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           specializationValue = extras.getString("specializationName");
        }
        initViews();
        getDoctors();
        clickListeners();
        setRecyclerView();
    }

    private void setRecyclerView() {
//        arrayList.add(new DoctorsModel());
//        arrayList.add(new DoctorsModel());
//        arrayList.add(new DoctorsModel());
//        arrayList.add(new DoctorsModel());
//        arrayList.add(new DoctorsModel());
//        arrayList.add(new DoctorsModel());
//        arrayList.add(new DoctorsModel());
//        arrayList.add(new DoctorsModel());
//        arrayList.add(new DoctorsModel());
//        arrayList.add(new DoctorsModel());

        DoctorsAdapter adapter=new DoctorsAdapter(userList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void initViews() {

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.home_toolbar);
        toolbarDrawerImage = findViewById(R.id.toolbar_drawer_image_back);
        recyclerView = findViewById(R.id.recycler_view);
    }
    private void clickListeners() {
        toolbarDrawerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this , PatientDashboard.class);
                startActivity(i);
                finish();
            }
        });

    }
    private void getDoctors() {
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref2;
        ref2 = ref1.child("Doctor");

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    DataSnapshot specialization = dsp.child("specialization");
                    if(specialization.getValue().toString().equals(specializationValue)) {
                        Toast.makeText(MainActivity.this, "Matched"+"", Toast.LENGTH_SHORT).show();
                        DataSnapshot docName = dsp.child("name");
                        doctorsModel.setName(docName.getValue().toString());
                        userList.add(doctorsModel);
                    }
                }
                Log.d("Doctors" , userList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}