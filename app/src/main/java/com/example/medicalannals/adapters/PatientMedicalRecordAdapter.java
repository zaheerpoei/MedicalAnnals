package com.example.medicalannals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.models.PatientMedicalRecordModel;
import com.example.medicalannals.models.SearchSpecialistModel;

import java.util.ArrayList;

public class PatientMedicalRecordAdapter extends RecyclerView.Adapter<PatientMedicalRecordAdapter.MyViewHolder> {

    private ArrayList<PatientMedicalRecordModel> patientMedicalRecordModelArrayList;
    private Context mcontext;

    public PatientMedicalRecordAdapter(ArrayList<PatientMedicalRecordModel> patientMedicalRecordModelArrayList, Context mcontext) {
        this.patientMedicalRecordModelArrayList = patientMedicalRecordModelArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.patient_medical_record_item, parent, false);
        PatientMedicalRecordAdapter.MyViewHolder vh = new PatientMedicalRecordAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PatientMedicalRecordModel patientMedicalRecordModel = patientMedicalRecordModelArrayList.get(position);
        holder.tvDocNamePatientRecordItem.setText(patientMedicalRecordModel.getDocNamePatientRecord());
        holder.tvDatePatientRecordItem.setText(patientMedicalRecordModel.getDatePatientRecord());
        holder.tvRemarksData.setText(patientMedicalRecordModel.getRemarksPatientRecord());
        holder.tvPrescriptionData.setText(patientMedicalRecordModel.getPrescriptionPatientRecord());

    }

    @Override
    public int getItemCount() {
        return patientMedicalRecordModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDocNamePatientRecordItem , tvDatePatientRecordItem,tvRemarksData, tvPrescriptionData ;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDocNamePatientRecordItem = itemView.findViewById(R.id.tv_doc_name_patient_record_item);
            tvDatePatientRecordItem = itemView.findViewById(R.id.tv_date_patient_record_item);
            tvRemarksData = itemView.findViewById(R.id.tv_remarks_data);
            tvPrescriptionData = itemView.findViewById(R.id.tv_prescription_data);
        }
    }
}
