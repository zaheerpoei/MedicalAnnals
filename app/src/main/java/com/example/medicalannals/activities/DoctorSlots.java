package com.example.medicalannals.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalannals.R;

import java.util.Calendar;

public class DoctorSlots extends AppCompatActivity {


    ImageView ivToolbarBack , ivCalenderSetSlots , ivTimeSetSlots;
    Button btnSetSlot;
    EditText edSetSlotsDate , edSetSlotsTime;
    private DatePickerDialog.OnDateSetListener doctorDashboardDateListener;
    long age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_slots);

        initViews();
        clickListeners();
    }

    private void clickListeners() {
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorSlots.this , DoctorDashboard.class);
                startActivity(i);
                finish();
            }
        });

        btnSetSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(DoctorSlots.this);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow;
                Message = (TextView) dialogView.findViewById(R.id.tvMessage);
                btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);

                Message.setText("Slot Added.");

                btnAllow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent i = new Intent(DoctorSlots.this , DoctorDashboard.class);
                        startActivity(i);
                        finish();
                    }
                });

                dialog.show();
            }
        });
        edSetSlotsDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = edSetSlotsDate.getInputType(); // backup the input type
                edSetSlotsDate.setInputType(InputType.TYPE_NULL); // disable soft input
                edSetSlotsDate.onTouchEvent(event); // call native handler
                edSetSlotsDate.setInputType(inType); // restore input type
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
                edSetSlotsDate.setText(date);
            }
        };
        ivCalenderSetSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DoctorSlots.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        doctorDashboardDateListener,
                        year, month, day);

                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        ivTimeSetSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                // Create a new instance of TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(DoctorSlots.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String strAM_PM;
                        if(selectedHour==0){
                            selectedHour+=12;   strAM_PM="AM";
                        }else if(selectedHour==12){
                            strAM_PM="PM";
                        }else if(selectedHour>12){
                            selectedHour=selectedHour-12;   strAM_PM="PM";
                        }else{
                            strAM_PM="AM";
                        }

                        edSetSlotsTime.setText( selectedHour + ":" + selectedMinute + " " + strAM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

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

    private void initViews() {
        ivToolbarBack = findViewById(R.id.toolbar_image_back);
        btnSetSlot = findViewById(R.id.btn_set_slot);
        ivCalenderSetSlots = findViewById(R.id.iv_calender_set_alots);
        ivTimeSetSlots = findViewById(R.id.iv_time_set_slots);
        edSetSlotsDate = findViewById(R.id.ed_set_slots_date);
        edSetSlotsTime = findViewById(R.id.ed_set_slots_time);

    }
}