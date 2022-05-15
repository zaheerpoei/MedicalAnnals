package com.example.medicalannals.activities;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medicalannals.R;
import com.example.medicalannals.adapters.AfternoonSlotsAdapter;
import com.example.medicalannals.adapters.EveningSlotsAdapter;
import com.example.medicalannals.adapters.MorningSlotsAdapter;
import com.example.medicalannals.adapters.ViewSlotsAdapter;
import com.example.medicalannals.models.BookTimeSlotModel;
import com.example.medicalannals.models.DoctorSlotsBookedModel;
import com.example.medicalannals.models.DoctorsModel;
import com.example.medicalannals.models.PatientModel;
import com.example.medicalannals.models.SlotsModel;
import com.example.medicalannals.models.ViewSlotsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewSlots extends AppCompatActivity implements ViewSlotsAdapter.SelectedDate,
        EveningSlotsAdapter.EveningSelectedTime, AfternoonSlotsAdapter.AfternoonSelectedTime, MorningSlotsAdapter.MorningSelectedTime {

    RecyclerView recyclerViewSlotsAvailable, recyclerViewMorningSlots, recyclerViewAfternoonSlots, recyclerViewEveningSlots;
    ArrayList<ViewSlotsModel> arrayList = new ArrayList<>();
    ArrayList<BookTimeSlotModel> arrayListMorningSlots = new ArrayList<>();
    ArrayList<BookTimeSlotModel> arrayListAfternoonSlots = new ArrayList<>();
    ArrayList<BookTimeSlotModel> arrayListEveningSlots = new ArrayList<>();
    ImageView ivToolbarBack;
    TextView tvChangeDoc;
    Button btnSlotsAvailable;
    String slotsValue;
    MorningSlotsAdapter adapterMorningSlots;
    AfternoonSlotsAdapter adapterAfternoonSlots;
    EveningSlotsAdapter adappterEveningSlots;
    ArrayList<SlotsModel> morningList = new ArrayList<SlotsModel>();
    ArrayList<SlotsModel> afternoonList = new ArrayList<SlotsModel>();
    ArrayList<SlotsModel> eveningList = new ArrayList<SlotsModel>();
    private DoctorsModel doctor;
    ProgressDialog progressDialog;
    private String stDate = "";
    private String stTime = "";
    CircleImageView ivDocPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);
        Bundle bundle = getIntent().getExtras();
        doctor = (DoctorsModel) bundle.getSerializable("doctor");
        initViews();
        setRecyclerView();
        clickListeners();

    }

       private void getSlotsData(String date) {
        stDate = date;
        morningList.clear();
        afternoonList.clear();
        eveningList.clear();
        progressDialog.show();

        final Query userQuery = FirebaseDatabase.getInstance().getReference().child("Slots").orderByChild("docId");
        userQuery.equalTo(doctor.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.hide();
//                Toast.makeText(getApplicationContext() , "check", Toast.LENGTH_SHORT).show();
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    SlotsModel bookTimeSlotModel = dsp.getValue(SlotsModel.class);
                    if (date.equals(bookTimeSlotModel.getDate())) {
                        String time = bookTimeSlotModel.getTime();
                        String[] array = time.split(":");
                        String[] space = time.split(" ");
                        if (space[1].equals("AM") && (Integer.valueOf(array[0]) >= 9 && Integer.valueOf(array[0]) <= 12)/*|| array[0].equals("10") || array[0].equals("11")*/) {
                            if (space[1].equals("AM")) {
                                morningList.add(bookTimeSlotModel);
                            }
                        } else if (space[1].equals("PM") && (array[0].equals("12") || (Integer.valueOf(array[0]) >= 1 && Integer.valueOf(array[0]) <= 4))/*array[0].equals("2") || array[0].equals("3") || array[0].equals("4")*/) {
                            if (space[1].equals("PM")) {
                                afternoonList.add(bookTimeSlotModel);
                            }
                        } else if (space[1].equals("PM") && (Integer.valueOf(array[0]) >= 5 && Integer.valueOf(array[0]) <= 12)/*array[0].equals("5") || array[0].equals("6") || array[0].equals("7") || array[0].equals("8") || array[0].equals("9")*/) {
                            if (space[1].equals("PM")) {
                                eveningList.add(bookTimeSlotModel);
                            }
                        }
                    }
                    adapterMorningSlots.notifyDataSetChanged();
                    adapterAfternoonSlots.notifyDataSetChanged();
                    adappterEveningSlots.notifyDataSetChanged();
                }

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
                if (!stDate.isEmpty() && !stTime.isEmpty()){
                    progressDialog.show();
                    DoctorSlotsBookedModel doctorSlotsBookedModel = new DoctorSlotsBookedModel();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Patient")
                            .child(uid)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        PatientModel patientModel = snapshot.getValue(PatientModel.class);
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference reference = database.getReference("Appointments");
                                        doctorSlotsBookedModel.setPatientBookedSlotDate(stDate);
                                        doctorSlotsBookedModel.setPatientBookedSlotTime(stTime);
                                        doctorSlotsBookedModel.setPatientBookedSlotId(uid);
                                        doctorSlotsBookedModel.setDoctorBookedSlotId(doctor.getId());
                                        doctorSlotsBookedModel.setPatientName(patientModel.getName());
                                        doctorSlotsBookedModel.setPatientProfilePic(patientModel.getPatientProfilePic());

                                        final Query userQuery = FirebaseDatabase.getInstance().getReference().child("Appointments").orderByChild("doctorBookedSlotId");

                                        userQuery.equalTo(doctor.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Boolean check = false;
                                                if (snapshot.hasChildren()) {

                                                    for (DataSnapshot dsp : snapshot.getChildren()) {
                                                        DoctorSlotsBookedModel doctorSlotsBookedModel = dsp.getValue(DoctorSlotsBookedModel.class);
                                                        if (stDate.equals(doctorSlotsBookedModel.getPatientBookedSlotDate()) && stTime.equals(doctorSlotsBookedModel.getPatientBookedSlotTime())) {
                                                            check= true;
                                                            break;
                                                        }
                                                    }
                                                    if (check){
                                                        progressDialog.dismiss();
                                                        ShowAlertDialog("Please Select different Date and Time.It is already Booked");
                                                    }else {
                                                        reference.push().setValue(doctorSlotsBookedModel).addOnCompleteListener(task -> {

                                                            progressDialog.dismiss();
                                                            if (task.isSuccessful()){
                                                                showDialog();
                                                            }else {
                                                                Toast.makeText(ViewSlots.this,"Please try again",Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }
                                                } else {
                                                    reference.push().setValue(doctorSlotsBookedModel).addOnCompleteListener(task -> {

                                                        progressDialog.dismiss();
                                                        if (task.isSuccessful()){
                                                            showDialog();
                                                        }else {
                                                            Toast.makeText(ViewSlots.this,"Please try again",Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });







                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });

                }else {
                    Toast.makeText(ViewSlots.this,"Please select time for booking",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void showDialog() {
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
                onBackPressed();
            }
        });

        dialog.show();
    }

    private void setRecyclerView() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy EEE", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String[] days = new String[7];
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            Log.d("Days" + i, "date :" + days[i]);
        }
        for (int i = 0; i < days.length; i++) {
            String[] data = days[i].split(" ");
            if (i == 0) {
                arrayList.add(new ViewSlotsModel(data[0], "Tomorrow", true));
            } else {
                arrayList.add(new ViewSlotsModel(data[0], data[1], false));
            }
        }

        getSlotsData(arrayList.get(0).date);

        ViewSlotsAdapter adapter = new ViewSlotsAdapter(arrayList, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(HORIZONTAL);
        recyclerViewSlotsAvailable.setLayoutManager(linearLayoutManager);
        recyclerViewSlotsAvailable.setAdapter(adapter);

        adapterMorningSlots = new MorningSlotsAdapter(morningList, this,this);
        LinearLayoutManager linearLayoutManagerMorningSlots = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerMorningSlots.setOrientation(HORIZONTAL);
        recyclerViewMorningSlots.setLayoutManager(linearLayoutManagerMorningSlots);
        recyclerViewMorningSlots.setAdapter(adapterMorningSlots);

        adapterAfternoonSlots = new AfternoonSlotsAdapter(afternoonList, this,this);
        LinearLayoutManager linearLayoutManagerAfternoonSlots = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerAfternoonSlots.setOrientation(linearLayoutManagerAfternoonSlots.HORIZONTAL);
        recyclerViewAfternoonSlots.setLayoutManager(linearLayoutManagerAfternoonSlots);
        recyclerViewAfternoonSlots.setAdapter(adapterAfternoonSlots);

        adappterEveningSlots = new EveningSlotsAdapter(eveningList, this,this);
        LinearLayoutManager linearLayoutManagerEveningSlots = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerEveningSlots.setOrientation(linearLayoutManagerEveningSlots.HORIZONTAL);
        recyclerViewEveningSlots.setLayoutManager(linearLayoutManagerEveningSlots);
        recyclerViewEveningSlots.setAdapter(adappterEveningSlots);
    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");

        recyclerViewSlotsAvailable = findViewById(R.id.recycler_view_slits_available);
        ivToolbarBack = findViewById(R.id.toolbar_image_back);
        tvChangeDoc = findViewById(R.id.tv_change_doc);
        ivDocPic = findViewById(R.id.iv_doc_pic);
        recyclerViewMorningSlots = findViewById(R.id.recycler_view_morning_slots);
        recyclerViewAfternoonSlots = findViewById(R.id.recycler_view_afternoon_slots);
        recyclerViewEveningSlots = findViewById(R.id.recycler_view_eveing_slots);
        btnSlotsAvailable = findViewById(R.id.btn_slots_available);
        TextView tv_doc_name = findViewById(R.id.tv_doc_name);
        TextView tv_hospital_name = findViewById(R.id.tv_hospital_name);
        TextView tv_specialization = findViewById(R.id.tv_specialization);
        TextView tv_years = findViewById(R.id.tv_years);
        TextView tv_education = findViewById(R.id.tv_education);
        TextView tv_fee_amount = findViewById(R.id.tv_fee_amount);

        if (doctor != null) {
            tv_doc_name.setText(doctor.getName());
            tv_hospital_name.setText(doctor.getHospital());
            tv_specialization.setText(doctor.getSpecialization());
            tv_years.setText(doctor.getExperience());
            tv_education.setText(doctor.getQualification());
            tv_fee_amount.setText(doctor.getFees());
            Glide.with(this).load(doctor.getDoctorProfilePic()).into(ivDocPic);
        }
    }

    @Override
    public void onDateSelected(String date) {
        getSlotsData(date);
    }

    @Override
    public void onEveningTimeSelected(String time) {
        stTime = time;
    }

    @Override
    public void onAfternoonTimeSelected(String time) {
        stTime = time;
    }

    @Override
    public void onMorningTimeSelected(String time) {
        stTime = time;
    }


    protected void ShowAlertDialog(String stMessage) {

        Dialog dialog = new Dialog(ViewSlots.this);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View view = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        dialog.setContentView(view);

        TextView Message, btnAllow;
        ImageView ivAlert;
        Message = (TextView) view.findViewById(R.id.tvMessage);
        btnAllow = (TextView) view.findViewById(R.id.btn_allow);
        ivAlert = (ImageView) view.findViewById(R.id.imageView16);

        ivAlert.setImageResource(R.drawable.warning);
        ivAlert.setColorFilter(ContextCompat.getColor(this, R.color.dark_blue_700), android.graphics.PorterDuff.Mode.SRC_IN);

        Message.setText(stMessage);

        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}