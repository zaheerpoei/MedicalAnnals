package com.example.medicalannals.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medicalannals.R;
import com.example.medicalannals.activities.DocViewPatientMedicalRecords;
import com.example.medicalannals.activities.EditPatientRecord;
import com.example.medicalannals.models.DocPatientViewMedicalRecordModel;
import com.example.medicalannals.models.DoctorsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DocPatientViewMedicalRecordAdapter extends RecyclerView.Adapter<DocPatientViewMedicalRecordAdapter.MyViewHolder>  implements Filterable {
    private ArrayList<DocPatientViewMedicalRecordModel> docPatientViewMedicalRecordModelArrayList;
    private ArrayList<DocPatientViewMedicalRecordModel> filteredArrayList;
    private Context mcontext;

    public DocPatientViewMedicalRecordAdapter(ArrayList<DocPatientViewMedicalRecordModel> docPatientViewMedicalRecordModelArrayList, Context mcontext) {
        this.docPatientViewMedicalRecordModelArrayList = docPatientViewMedicalRecordModelArrayList;
        this.mcontext = mcontext;
        this.filteredArrayList = docPatientViewMedicalRecordModelArrayList;
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
        DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel = filteredArrayList.get(position);
        holder.tvDocNamePatientRecordItem.setText(docPatientViewMedicalRecordModel.getTvPatientNamePatientRecord());
        holder.tvDatePatientRecordItem.setText(docPatientViewMedicalRecordModel.getTvDatePatientRecord());
//        holder.tvRemarksData.setText(docPatientViewMedicalRecordModel.getTvRemarksPatientRecord());
//        holder.tvPrescriptionData.setText(docPatientViewMedicalRecordModel.getTvPrescriptionPatientRecord());
        holder.constraintPatientMedicalRecordSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = ((FragmentActivity) mcontext).getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow, lineView;
                ImageView ivAlert;
                ivAlert = (ImageView) dialogView.findViewById(R.id.imageView16);
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

                View dialogView = ((FragmentActivity) mcontext).getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow, lineView;
                ImageView ivAlert;
                ivAlert = (ImageView) dialogView.findViewById(R.id.imageView16);
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

        holder.constraintDocViewPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = ((FragmentActivity) mcontext).getLayoutInflater().inflate(R.layout.alert_dialog_image, null);
                dialog.setContentView(dialogView);

                TextView btnAllow;
                ImageView ivPrescriptionPic;
                ivPrescriptionPic = (ImageView) dialogView.findViewById(R.id.iv_view_prescription);
                btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);

                Glide.with(mcontext).load(docPatientViewMedicalRecordModel.getPrescription()).into(ivPrescriptionPic);


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
                updatePatientRecord(docPatientViewMedicalRecordModel,position);

            }
        });

    }


    private void updatePatientRecord(DocPatientViewMedicalRecordModel docPatientViewMedicalRecordModel, int position) {

        FirebaseDatabase.getInstance().getReference()
                .child("Patient Records")
                .child(docPatientViewMedicalRecordModel.getPatientRecordId()).child("isdeleted").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                docPatientViewMedicalRecordModelArrayList.remove(docPatientViewMedicalRecordModel);
                notifyItemRemoved(position);
                Dialog dialog = new Dialog(mcontext);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                View dialogView = ((FragmentActivity) mcontext).getLayoutInflater().inflate(R.layout.alert_dialog, null);
                dialog.setContentView(dialogView);

                TextView Message, btnAllow, lineView;
                ImageView ivAlert;
                ivAlert = (ImageView) dialogView.findViewById(R.id.imageView16);
                Message = (TextView) dialogView.findViewById(R.id.tvMessage);
                btnAllow = (TextView) dialogView.findViewById(R.id.btn_allow);
                lineView = (TextView) dialogView.findViewById(R.id.textView71);
                ivAlert.setImageResource(R.drawable.warning);
                ivAlert.setColorFilter(mcontext.getResources().getColor(R.color.dark_blue_700));
                Message.setText("Record Deleted");

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
    public Filter getFilter() {
        return new ItemFilter();
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<DocPatientViewMedicalRecordModel> list = docPatientViewMedicalRecordModelArrayList;

            int count = list.size();
            final ArrayList<DocPatientViewMedicalRecordModel> nlist = new ArrayList<DocPatientViewMedicalRecordModel>(count);

            DocPatientViewMedicalRecordModel filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getTvPatientNamePatientRecord().toLowerCase().contains(filterString)) {
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
            filteredArrayList = (ArrayList<DocPatientViewMedicalRecordModel>) results.values;
            notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return filteredArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDocNamePatientRecordItem, tvDatePatientRecordItem, tvRemarksData, tvPrescriptionData, tvDoc;
        ConstraintLayout constraintPatientMedicalRecordSymptoms, constraintPatientMedicalRecordPrescription , constraintDocViewPrescription;
        ImageView ivDustbin;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDocNamePatientRecordItem = itemView.findViewById(R.id.tv_doc_patient_record_item);
            tvDoc = itemView.findViewById(R.id.tv_doc);
            tvDatePatientRecordItem = itemView.findViewById(R.id.tv_date_doc_patient_record_item);
            tvRemarksData = itemView.findViewById(R.id.tv_remarks_data);
            tvPrescriptionData = itemView.findViewById(R.id.tv_prescription_data);
            constraintPatientMedicalRecordSymptoms = itemView.findViewById(R.id.constraint_doc_patient_medical_record_symptoms);
            constraintPatientMedicalRecordSymptoms = itemView.findViewById(R.id.constraint_doc_patient_medical_record_symptoms);
            constraintPatientMedicalRecordPrescription = itemView.findViewById(R.id.constraint_doc_patient_medical_record_prescription);
            constraintDocViewPrescription = itemView.findViewById(R.id.constraint_doc_view_prescription);
            ivDustbin = itemView.findViewById(R.id.iv_dustbin);
        }
    }
}
