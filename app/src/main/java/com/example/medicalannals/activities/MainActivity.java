package com.example.medicalannals.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.DoctorsAdapter;
import com.example.medicalannals.models.DoctorsModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    Menu menu;
    ImageView toolbarDrawerImage;
    RecyclerView recyclerView;
    ArrayList<DoctorsModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        clickListeners();
        setRecyclerView();

    }

    private void setRecyclerView() {
        arrayList.add(new DoctorsModel());
        arrayList.add(new DoctorsModel());
        arrayList.add(new DoctorsModel());
        arrayList.add(new DoctorsModel());
        arrayList.add(new DoctorsModel());
        arrayList.add(new DoctorsModel());
        arrayList.add(new DoctorsModel());
        arrayList.add(new DoctorsModel());
        arrayList.add(new DoctorsModel());
        arrayList.add(new DoctorsModel());

        DoctorsAdapter adapter=new DoctorsAdapter(arrayList,this);
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


}