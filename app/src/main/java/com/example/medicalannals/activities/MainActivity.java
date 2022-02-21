package com.example.medicalannals.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.medicalannals.models.PatientModel;
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
    String specializationValue;
    private DoctorsAdapter adapter;
    ProgressDialog progressDialog;
    TextView tvNoRecord;
    EditText edSearchBar;

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
        adapter=new DoctorsAdapter(userList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void initViews() {

        drawerLayout = findViewById(R.id.drawer_layout);
        edSearchBar = findViewById(R.id.ed_searchbar);
        tvNoRecord = findViewById(R.id.tv_no_record);
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.home_toolbar);
        toolbarDrawerImage = findViewById(R.id.toolbar_drawer_image_back);
        recyclerView = findViewById(R.id.recycler_view);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");

        edSearchBar.addTextChangedListener(new TextWatcher() {

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
    private void clickListeners() {
        toolbarDrawerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    private void getDoctors() {
        progressDialog.show();
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref2;
        ref2 = ref1.child("Doctor");

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.dismiss();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    DataSnapshot specialization = dsp.child("specialization");
                    if(specialization.getValue().toString().equalsIgnoreCase(specializationValue)) {
                        DoctorsModel doctorsModel = dsp.getValue(DoctorsModel.class);
                        doctorsModel.setId(dsp.getKey());
                        userList.add(doctorsModel);
                    }
                }
                if (userList.size() >0) {
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    tvNoRecord.setVisibility(View.GONE);
                }else {
                    recyclerView.setVisibility(View.GONE);
                    tvNoRecord.setVisibility(View.VISIBLE);
                }
                Log.d("Doctors" , userList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

    }

}