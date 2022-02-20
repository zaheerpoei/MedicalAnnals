package com.example.medicalannals.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.models.BookTimeSlotModel;
import com.example.medicalannals.models.SlotsModel;

import java.util.ArrayList;

public class EveningSlotsAdapter extends RecyclerView.Adapter<EveningSlotsAdapter.MyViewHolder>{

    private ArrayList<SlotsModel> bookTimeSlotModelArrayList;
    private Context mcontext;
    private EveningSelectedTime eveningSelectedTime;

    public EveningSlotsAdapter(ArrayList<SlotsModel> bookTimeSlotModelArrayList, Context mcontext,EveningSelectedTime eveningSelectedTime) {
        this.bookTimeSlotModelArrayList = bookTimeSlotModelArrayList;
        this.mcontext = mcontext;
        this.eveningSelectedTime = eveningSelectedTime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.slot_available, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SlotsModel bookTimeSlotModel = bookTimeSlotModelArrayList.get(position);
        holder.tvTime.setText(bookTimeSlotModel.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eveningSelectedTime.onEveningTimeSelected(bookTimeSlotModel.getTime());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookTimeSlotModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }


    public interface EveningSelectedTime{
        void onEveningTimeSelected(String time);
    }
}
