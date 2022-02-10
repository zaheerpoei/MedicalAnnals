package com.example.medicalannals.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.activities.EditPatientRecord;
import com.example.medicalannals.models.DoctorSlotsBookedModel;

import java.util.ArrayList;

public class SlotsBookedAdapter extends RecyclerView.Adapter<SlotsBookedAdapter.MyViewHolder>{


    private ArrayList<DoctorSlotsBookedModel> doctorSlotsBookedModelList;
    private Context mcontext;

    public SlotsBookedAdapter(ArrayList<DoctorSlotsBookedModel> doctorSlotsBookedModelList, Context mcontext) {
        this.doctorSlotsBookedModelList = doctorSlotsBookedModelList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.slots_booked, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DoctorSlotsBookedModel doctorSlotsBookedModel = doctorSlotsBookedModelList.get(position);
        holder.tvPatientNameBookedSlots.setText(doctorSlotsBookedModel.getPatientBookedSlotName());
        holder.constraintMainSlotsBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mcontext , EditPatientRecord.class);
                mcontext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorSlotsBookedModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientNameBookedSlots, tvBookedSlotsDate, tvBookedSlotsTime;
        ImageView ivPatientPicBookedSlots;
        ConstraintLayout constraintMainSlotsBooked;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivPatientPicBookedSlots = itemView.findViewById(R.id.iv_patient_pic_booked_slots);
            tvPatientNameBookedSlots = itemView.findViewById(R.id.tv_patient_name_booked_slots);
            tvBookedSlotsDate = itemView.findViewById(R.id.tv_booked_slots_date);
            tvBookedSlotsTime = itemView.findViewById(R.id.tv_booked_slots_time);
            constraintMainSlotsBooked = itemView.findViewById(R.id.constraint_main_slots_booked);
        }
    }
}
