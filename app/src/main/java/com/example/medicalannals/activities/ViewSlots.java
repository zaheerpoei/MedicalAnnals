package com.example.medicalannals.activities;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalannals.R;
import com.example.medicalannals.adapters.AfternoonSlotsAdapter;
import com.example.medicalannals.adapters.EveningSlotsAdapter;
import com.example.medicalannals.adapters.MorningSlotsAdapter;
import com.example.medicalannals.adapters.ViewSlotsAdapter;
import com.example.medicalannals.models.BookTimeSlotModel;
import com.example.medicalannals.models.ViewSlotsModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);

        initViews();
        setRecyclerView();
        clickListeners();
    }

    private void clickListeners() {
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewSlots.this , MainActivity.class);
                startActivity(i);
                finish();
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

        arrayListMorningSlots.add(new BookTimeSlotModel("10:00AM"));
        arrayListMorningSlots.add(new BookTimeSlotModel("11:00AM"));
        arrayListMorningSlots.add(new BookTimeSlotModel("11:30AM"));

        MorningSlotsAdapter adapterMorningSlots=new MorningSlotsAdapter(arrayListMorningSlots,this);
        LinearLayoutManager linearLayoutManagerMorningSlots=new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerMorningSlots.setOrientation(HORIZONTAL);
        recyclerViewMorningSlots.setLayoutManager(linearLayoutManagerMorningSlots);
        recyclerViewMorningSlots.setAdapter(adapterMorningSlots);

        arrayListAfternoonSlots.add(new BookTimeSlotModel("1:00PM"));
        arrayListAfternoonSlots.add(new BookTimeSlotModel("2:30PM"));
        arrayListAfternoonSlots.add(new BookTimeSlotModel("3:15PM"));
        arrayListAfternoonSlots.add(new BookTimeSlotModel("3:30PM"));

        AfternoonSlotsAdapter adapterAfternoonSlots=new AfternoonSlotsAdapter(arrayListAfternoonSlots ,this);
        LinearLayoutManager linearLayoutManagerAfternoonSlots=new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerAfternoonSlots.setOrientation(linearLayoutManagerAfternoonSlots.HORIZONTAL);
        recyclerViewAfternoonSlots.setLayoutManager(linearLayoutManagerAfternoonSlots);
        recyclerViewAfternoonSlots.setAdapter(adapterAfternoonSlots);

        arrayListEveningSlots.add(new BookTimeSlotModel("8:00PM"));
        arrayListEveningSlots.add(new BookTimeSlotModel("9:00PM"));
        arrayListEveningSlots.add(new BookTimeSlotModel("10:00PM"));

        EveningSlotsAdapter adapterEveningSlots=new EveningSlotsAdapter(arrayListEveningSlots,this);
        LinearLayoutManager linearLayoutManagerEveningSlots=new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerEveningSlots.setOrientation(linearLayoutManagerEveningSlots.HORIZONTAL);
        recyclerViewEveningSlots.setLayoutManager(linearLayoutManagerEveningSlots);
        recyclerViewEveningSlots.setAdapter(adapterEveningSlots);
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