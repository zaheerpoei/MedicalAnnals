package com.example.medicalannals.activities;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.AfternoonSlotsAdapter;
import com.example.medicalannals.adapters.EveningSlotsAdapter;
import com.example.medicalannals.adapters.MorningSlotsAdapter;
import com.example.medicalannals.adapters.ViewSlotsAdapter;
import com.example.medicalannals.models.BookTimeSlotModel;
import com.example.medicalannals.models.DoctorsModel;
import com.example.medicalannals.models.SlotsModel;
import com.example.medicalannals.models.ViewSlotsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewSlots extends AppCompatActivity {

    RecyclerView recyclerViewSlotsAvailable , recyclerViewMorningSlots , recyclerViewAfternoonSlots , recyclerViewEveningSlots;
    ArrayList<ViewSlotsModel> arrayList = new ArrayList<>();
    ArrayList<BookTimeSlotModel> arrayListMorningSlots = new ArrayList<>();
    ArrayList<BookTimeSlotModel> arrayListAfternoonSlots = new ArrayList<>();
    ArrayList<BookTimeSlotModel> arrayListEveningSlots = new ArrayList<>();
    ImageView ivToolbarBack;
    TextView tvChangeDoc;
    Button btnSlotsAvailable;
    String slotsValue;
    MorningSlotsAdapter adapterMorningSlots ;
    AfternoonSlotsAdapter adapterAfternoonSlots;
    EveningSlotsAdapter adappterEveningSlots;
    ArrayList<SlotsModel> morningList= new ArrayList<SlotsModel>();
    ArrayList<SlotsModel> afternoonList= new ArrayList<SlotsModel>();
    ArrayList<SlotsModel> eveningList= new ArrayList<SlotsModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);
        initViews();
        setRecyclerView();
        clickListeners();
        getSlotsData();
    }

    private void getSlotsData() {
        final Query userQuery = FirebaseDatabase.getInstance().getReference().child("Slots").orderByChild("docId");
        userQuery.equalTo("gHdGFCJc9LgaOJS5TzU1CAzgrXA2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext() , "check", Toast.LENGTH_SHORT).show();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    SlotsModel bookTimeSlotModel = dsp.getValue(SlotsModel.class);
                    String time = bookTimeSlotModel.getTime();
                    String[] array = time.split(":");
                    String[] space = time.split(" ");
                    if(array[0].equals("9") || array[0].equals("10") || array[0].equals("11")) {
                        if(space[1].equals("AM")) {
                            morningList.add(bookTimeSlotModel);
                        }
                    }

                    else if(array[0].equals("12") || array[0].equals("1") || array[0].equals("2") || array[0].equals("3") || array[0].equals("4")){
                        if(space[1].equals("PM")){
                            afternoonList.add(bookTimeSlotModel);
                        }
                    }

                    else if(array[0].equals("5") || array[0].equals("6") || array[0].equals("7") || array[0].equals("8") || array[0].equals("9")){
                        if(space[1].equals("PM")){
                            eveningList.add(bookTimeSlotModel);
                        }
                    }
                }
                adapterMorningSlots.notifyDataSetChanged();
                adapterAfternoonSlots.notifyDataSetChanged();
                adappterEveningSlots.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clickListeners() {
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSlotsAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ViewSlots.this);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow;
                Message = (TextView) dialogView.findViewById(R.id.tvMessage);
                btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);

                Message.setText("Appointment has been Booked.");

                btnAllow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent i = new Intent(ViewSlots.this , PatientDashboard.class);
                        startActivity(i);
                        finish();
                    }
                });

                dialog.show();
            }
        });
    }

    private void setRecyclerView() {
        arrayList.add(new ViewSlotsModel("26" , "Tomorrow"));
        arrayList.add(new ViewSlotsModel("27" , "Thu"));
        arrayList.add(new ViewSlotsModel("28" , "Fri"));
        arrayList.add(new ViewSlotsModel("29" , "Sat"));
        arrayList.add(new ViewSlotsModel("30" , "Sun"));
        arrayList.add(new ViewSlotsModel("31" , "Mon"));
        arrayList.add(new ViewSlotsModel("1" , "Tues"));
        arrayList.add(new ViewSlotsModel("2" , "Wed"));
        arrayList.add(new ViewSlotsModel("3" , "Thu"));
        arrayList.add(new ViewSlotsModel("4" , "Fri"));

        ViewSlotsAdapter adapter=new ViewSlotsAdapter(arrayList,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(HORIZONTAL);
        recyclerViewSlotsAvailable.setLayoutManager(linearLayoutManager);
        recyclerViewSlotsAvailable.setAdapter(adapter);

        adapterMorningSlots=new MorningSlotsAdapter(morningList,this);
        LinearLayoutManager linearLayoutManagerMorningSlots=new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerMorningSlots.setOrientation(HORIZONTAL);
        recyclerViewMorningSlots.setLayoutManager(linearLayoutManagerMorningSlots);
        recyclerViewMorningSlots.setAdapter(adapterMorningSlots);

         adapterAfternoonSlots=new AfternoonSlotsAdapter(afternoonList ,this);
        LinearLayoutManager linearLayoutManagerAfternoonSlots=new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerAfternoonSlots.setOrientation(linearLayoutManagerAfternoonSlots.HORIZONTAL);
        recyclerViewAfternoonSlots.setLayoutManager(linearLayoutManagerAfternoonSlots);
        recyclerViewAfternoonSlots.setAdapter(adapterAfternoonSlots);

         adappterEveningSlots=new EveningSlotsAdapter(eveningList,this);
        LinearLayoutManager linearLayoutManagerEveningSlots=new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerEveningSlots.setOrientation(linearLayoutManagerEveningSlots.HORIZONTAL);
        recyclerViewEveningSlots.setLayoutManager(linearLayoutManagerEveningSlots);
        recyclerViewEveningSlots.setAdapter(adappterEveningSlots);
    }

    private void initViews() {
        recyclerViewSlotsAvailable = findViewById(R.id.recycler_view_slits_available);
        ivToolbarBack = findViewById(R.id.toolbar_image_back);
        tvChangeDoc = findViewById(R.id.tv_change_doc);
        recyclerViewMorningSlots = findViewById(R.id.recycler_view_morning_slots);
        recyclerViewAfternoonSlots = findViewById(R.id.recycler_view_afternoon_slots);
        recyclerViewEveningSlots = findViewById(R.id.recycler_view_eveing_slots);
        btnSlotsAvailable = findViewById(R.id.btn_slots_available);
    }

}