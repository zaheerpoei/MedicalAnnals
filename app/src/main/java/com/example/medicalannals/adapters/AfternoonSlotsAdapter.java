package com.example.medicalannals.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.models.BookTimeSlotModel;
import com.example.medicalannals.models.SlotsModel;

import java.util.ArrayList;

public class AfternoonSlotsAdapter extends RecyclerView.Adapter<AfternoonSlotsAdapter.MyViewHolder>{

    private ArrayList<SlotsModel> bookTimeSlotModelArrayList;
    private Context mcontext;
    private AfternoonSelectedTime afternoonSelectedTime;
    public static int selected_position = -1;

    public AfternoonSlotsAdapter(ArrayList<SlotsModel> bookTimeSlotModelArrayList, Context mcontext,AfternoonSelectedTime afternoonSelectedTime) {
        this.bookTimeSlotModelArrayList = bookTimeSlotModelArrayList;
        this.mcontext = mcontext;
        this.afternoonSelectedTime = afternoonSelectedTime;
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
                notifyDataSetChanged();
                selected_position = position;
                afternoonSelectedTime.onAfternoonTimeSelected(bookTimeSlotModel.getTime());
            }
        });
        if (selected_position == position) {
            holder.constraintChildCardView.setBackgroundColor(Color.parseColor("#e6e6e6"));
        } else {
            holder.constraintChildCardView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return bookTimeSlotModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        ConstraintLayout constraintChildCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            constraintChildCardView = itemView.findViewById(R.id.constraint_child_card_view);
        }
    }


    public interface AfternoonSelectedTime{
        void onAfternoonTimeSelected(String time);
    }
}
