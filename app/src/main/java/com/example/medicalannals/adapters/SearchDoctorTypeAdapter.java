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
import com.example.medicalannals.activities.MainActivity;
import com.example.medicalannals.models.SearchSpecialistModel;

import java.util.ArrayList;

public class SearchDoctorTypeAdapter extends RecyclerView.Adapter<SearchDoctorTypeAdapter.MyViewHolder> {

    private ArrayList<SearchSpecialistModel> searchDoctorTypeAdapterArrayList;
    private Context mcontext;


    public SearchDoctorTypeAdapter(ArrayList<SearchSpecialistModel> searchDoctorTypeAdapterArrayList, Context mcontext) {
        this.searchDoctorTypeAdapterArrayList = searchDoctorTypeAdapterArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.recycler_view_item_search_doctor_type, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SearchSpecialistModel searchSpecialistModel = searchDoctorTypeAdapterArrayList.get(position);
        holder.ivSpecializationImage.setImageResource(searchSpecialistModel.getSpecializationImage());
        holder.tvSpecializationName.setText(searchSpecialistModel.getSpecialization());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mcontext , MainActivity.class);
                mcontext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchDoctorTypeAdapterArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSpecializationImage;
        TextView tvSpecializationName;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivSpecializationImage = itemView.findViewById(R.id.iv_doc_type);
            tvSpecializationName = itemView.findViewById(R.id.tv_specialization_name);
        }
    }
}
