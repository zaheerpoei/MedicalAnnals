package com.example.medicalannals.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.models.DocPatientViewMedicalRecordModel;

import java.util.ArrayList;

public class DocPatientViewMedicalRecordAdapter extends RecyclerView.Adapter<DocPatientViewMedicalRecordAdapter.MyViewHolder> {
    private ArrayList<DocPatientViewMedicalRecordModel> docPatientViewMedicalRecordModelArrayList;
    private Context mcontext;

    public DocPatientViewMedicalRecordAdapter(ArrayList<DocPatientViewMedicalRecordModel> docPatientViewMedicalRecordModelArrayList, Context mcontext) {
        this.docPatientViewMedicalRecordModelArrayList = docPatientViewMedicalRecordModelArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.doc_patient_medical_record_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel = docPatientViewMedicalRecordModelArrayList.get(position);
        holder.tvDocNamePatientRecordItem.setText(docPatientViewMedicalRecordModel.getTvPatientNamePatientRecord());
        holder.tvDatePatientRecordItem.setText(docPatientViewMedicalRecordModel.getTvDatePatientRecord());
//        holder.tvRemarksData.setText(patientMedicalRecordModel.getRemarksPatientRecord());
//        holder.tvPrescriptionData.setText(patientMedicalRecordModel.getPrescriptionPatientRecord());
        holder.constraintPatientMedicalRecordSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = ((FragmentActivity)mcontext).getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow , lineView;
                ImageView ivAlert;
                ivAlert =(ImageView) dialogView.findViewById(R.id.imageView16);
                Message = (TextView) dialogView.findViewById(R.id.tvMessage);
                btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);
                lineView = (TextView) dialogView.findViewById(R.id.textView71);

                ivAlert.setVisibility(View.GONE);
                lineView.setVisibility(View.GONE);

                Message.setText(docPatientViewMedicalRecordModel.getTvRemarksPatientRecord());

                btnAllow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.constraintPatientMedicalRecordPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = ((FragmentActivity)mcontext).getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow , lineView;
                ImageView ivAlert;
                ivAlert =(ImageView) dialogView.findViewById(R.id.imageView16);
                Message = (TextView) dialogView.findViewById(R.id.tvMessage);
                btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);
                lineView = (TextView) dialogView.findViewById(R.id.textView71);

                ivAlert.setVisibility(View.GONE);
                lineView.setVisibility(View.GONE);

                Message.setText(docPatientViewMedicalRecordModel.getTvPrescriptionPatientRecord());

                btnAllow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.ivDustbin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(view.getContext());
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = ((FragmentActivity)mcontext).getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow , lineView;
                ImageView ivAlert;
                ivAlert =(ImageView) dialogView.findViewById(R.id.imageView16);
                Message = (TextView) dialogView.findViewById(R.id.tvMessage);
                btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);
                lineView = (TextView) dialogView.findViewById(R.id.textView71);

                Message.setText("Record Deleted.");

                btnAllow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return docPatientViewMedicalRecordModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDocNamePatientRecordItem , tvDatePatientRecordItem,tvRemarksData, tvPrescriptionData, tvDoc ;
        ConstraintLayout constraintPatientMedicalRecordSymptoms ,constraintPatientMedicalRecordPrescription;
        ImageView ivDustbin;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDocNamePatientRecordItem = itemView.findViewById(R.id.tv_doc_patient_record_item);
            tvDoc = itemView.findViewById(R.id.tv_doc);
            tvDatePatientRecordItem = itemView.findViewById(R.id.tv_date_doc_patient_record_item);
            tvRemarksData = itemView.findViewById(R.id.tv_remarks_data);
            tvPrescriptionData = itemView.findViewById(R.id.tv_prescription_data);
            constraintPatientMedicalRecordSymptoms = itemView.findViewById(R.id.constraint_doc_patient_medical_record_symptoms);
            constraintPatientMedicalRecordPrescription = itemView.findViewById(R.id.constraint_doc_patient_medical_record_prescription);
            ivDustbin = itemView.findViewById(R.id.iv_dustbin);
        }
    }
}
