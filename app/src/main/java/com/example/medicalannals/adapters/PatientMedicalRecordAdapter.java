package com.example.medicalannals.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.models.DocPatientViewMedicalRecordModel;
import com.example.medicalannals.models.DoctorsModel;
import com.example.medicalannals.models.PatientMedicalRecordModel;

import java.util.ArrayList;
import java.util.List;

public class PatientMedicalRecordAdapter extends RecyclerView.Adapter<PatientMedicalRecordAdapter.MyViewHolder> implements Filterable {

    private ArrayList<DocPatientViewMedicalRecordModel> patientMedicalRecordModelArrayList;
    private ArrayList<DocPatientViewMedicalRecordModel> filteredData;
    private Context mcontext;


    public PatientMedicalRecordAdapter(ArrayList<DocPatientViewMedicalRecordModel> patientMedicalRecordModelArrayList,Context mcontext) {
        this.patientMedicalRecordModelArrayList = patientMedicalRecordModelArrayList;
        this.filteredData = patientMedicalRecordModelArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.patient_medical_record_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DocPatientViewMedicalRecordModel patientMedicalRecordModel = filteredData.get(position);
        holder.tvDocNamePatientRecordItem.setText(patientMedicalRecordModel.getTvDocNamePatientRecord());
        holder.tvDatePatientRecordItem.setText(patientMedicalRecordModel.getTvDatePatientRecord());
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

                Message.setText(patientMedicalRecordModel.getTvRemarksPatientRecord());

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

                Message.setText(patientMedicalRecordModel.getTvPrescriptionPatientRecord());

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
        return filteredData.size();
    }

    @Override
    public Filter getFilter() {
        return new ItemFilter();
    }
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<DocPatientViewMedicalRecordModel> list = patientMedicalRecordModelArrayList;

            int count = list.size();
            final ArrayList<DocPatientViewMedicalRecordModel> nlist = new ArrayList<DocPatientViewMedicalRecordModel>(count);

            DocPatientViewMedicalRecordModel filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getTvDocNamePatientRecord().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<DocPatientViewMedicalRecordModel>) results.values;
            notifyDataSetChanged();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDocNamePatientRecordItem , tvDatePatientRecordItem,tvRemarksData, tvPrescriptionData ;
        ConstraintLayout constraintPatientMedicalRecordSymptoms ,constraintPatientMedicalRecordPrescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDocNamePatientRecordItem = itemView.findViewById(R.id.tv_doc_name_patient_record_item);
            tvDatePatientRecordItem = itemView.findViewById(R.id.tv_date_patient_record_item);
            tvRemarksData = itemView.findViewById(R.id.tv_remarks_data);
            tvPrescriptionData = itemView.findViewById(R.id.tv_prescription_data);
            constraintPatientMedicalRecordSymptoms = itemView.findViewById(R.id.constraint_patient_medical_record_symptoms);
            constraintPatientMedicalRecordPrescription = itemView.findViewById(R.id.constraint_patient_medical_record_prescription);
        }
    }
}
