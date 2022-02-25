package com.example.medicalannals.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medicalannals.R;
import com.example.medicalannals.models.SlotsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class DoctorSlots extends AppCompatActivity {


    ImageView ivToolbarBack , ivCalenderSetSlots , ivTimeSetSlots;
    Button btnSetSlot;
    EditText edSetSlotsDate , edSetSlotsTime;
    private DatePickerDialog.OnDateSetListener doctorDashboardDateListener;
    long age;
    String stDate, stTime;


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
                onBackPressed();
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

                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
        btnSetSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkFields()){
                    SlotsModel slotsModel = new SlotsModel();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference reference = database.getReference("Slots");
                    slotsModel.setDate(stDate);
                    slotsModel.setTime(stTime);
                    slotsModel.setDocId(uid);
                    reference.push().setValue(slotsModel).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){

                            showDialog();
                        }else {
                            Toast.makeText(DoctorSlots.this,"Please Try again",Toast.LENGTH_LONG).show();
                        }
                    });
//                    reference.child(uid).setValue(arrayList);
                }

            }
        });
    }

    private void showDialog() {
        Dialog dialog = new Dialog(DoctorSlots.this);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View dialogView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        dialog.setContentView(dialogView);

        TextView Message, btnAllow;
        Message =  (TextView) dialogView.findViewById(R.id.tvMessage);
        btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);

        Message.setText("Slot Added.");

        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onBackPressed();
            }
        });

        dialog.show();
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

    private boolean checkFields() {
       edSetSlotsDate.setError(null);
       edSetSlotsTime.setError(null);

       stDate = edSetSlotsDate.getText().toString().trim();
       stTime = edSetSlotsTime.getText().toString().trim();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(stDate)) {
            ShowAlertDialog("Please Select Date");
            focusView = edSetSlotsDate;
            cancel = true;
        }
        else if(TextUtils.isEmpty(stTime)){
            ShowAlertDialog("Please Select Time");
            focusView = edSetSlotsTime;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;

    }

    protected void ShowAlertDialog(String stMessage){

        Dialog dialog = new Dialog(DoctorSlots.this);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View view  = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        dialog.setContentView(view);

        TextView Message,btnAllow;
        ImageView ivAlert;
        Message=(TextView)view.findViewById(R.id.tvMessage);
        btnAllow=(TextView)view.findViewById(R.id.btn_allow);
        ivAlert = (ImageView) view.findViewById(R.id.imageView16) ;

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
    };
}