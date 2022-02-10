package com.example.medicalannals.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.activities.DocViewPatientMedicalRecords;
import com.example.medicalannals.models.PatientRecordModel;

import java.util.ArrayList;

public class PatientRecordAdapter  extends RecyclerView.Adapter<PatientRecordAdapter.MyViewHolder>{

    private ArrayList<PatientRecordModel> patientRecordModelArrayList;
    private Context mcontext;

    public PatientRecordAdapter(ArrayList<PatientRecordModel> patientRecordModelArrayList, Context mcontext) {
        this.patientRecordModelArrayList = patientRecordModelArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.patient_medical_record, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PatientRecordModel patientRecordModel = patientRecordModelArrayList.get(position);
        holder.ivPatientImage.setImageResource(patientRecordModel.getPatientImage());
        holder.tvPatientName.setText(patientRecordModel.getPatientName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mcontext , DocViewPatientMedicalRecords.class);
                mcontext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return patientRecordModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPatientImage , ivEdit , ivDelete;
        TextView tvPatientName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPatientImage = itemView.findViewById(R.id.iv_patient_pic);
            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            ivEdit = itemView.findViewById(R.id.iv_edit_patient);
            ivDelete = itemView.findViewById(R.id.iv_dustbin);
        }
    }
}
