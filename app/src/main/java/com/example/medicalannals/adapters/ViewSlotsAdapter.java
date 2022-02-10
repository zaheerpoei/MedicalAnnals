package com.example.medicalannals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.models.ViewSlotsModel;

import java.util.ArrayList;

public class ViewSlotsAdapter extends RecyclerView.Adapter<ViewSlotsAdapter.MyViewHolder> {

    private ArrayList<ViewSlotsModel> viewSlotsModelArrayList;
    private Context mcontext;

    public ViewSlotsAdapter(ArrayList<ViewSlotsModel> viewSlotsModelArrayList, Context mcontext) {
        this.viewSlotsModelArrayList = viewSlotsModelArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.recycler_view_view_slots_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewSlotsModel viewSlotsModel = viewSlotsModelArrayList.get(position);
        holder.tvDate.setText(viewSlotsModel.getDate());
        holder.tvDayName.setText(viewSlotsModel.getDay());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return viewSlotsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvDayName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDayName = itemView.findViewById(R.id.tv_day_name);
        }
    }
}

