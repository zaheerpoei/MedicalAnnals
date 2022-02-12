package com.example.medicalannals.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalannals.R;
import com.example.medicalannals.activities.ViewSlots;
import com.example.medicalannals.models.DoctorsModel;

import java.util.ArrayList;
import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.MyViewHolder> implements Filterable {

    private ArrayList<DoctorsModel> doctorsDataList;
    private ArrayList<DoctorsModel> filteredData;
    private Context mcontext;

    public DoctorsAdapter(ArrayList<DoctorsModel> doctorsDataList, Context mcontext) {
        this.doctorsDataList = doctorsDataList;
        this.filteredData = doctorsDataList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.recycler_view_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DoctorsModel doctorsModel = filteredData.get(position);
        holder.tvDocName.setText(doctorsModel.getName());
        holder.tvViewSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext , ViewSlots.class);
                mcontext.startActivity(i);

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

            final List<DoctorsModel> list = doctorsDataList;

            int count = list.size();
            final ArrayList<DoctorsModel> nlist = new ArrayList<DoctorsModel>(count);

            DoctorsModel filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getName().toLowerCase().contains(filterString)) {
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
            filteredData = (ArrayList<DoctorsModel>) results.values;
            notifyDataSetChanged();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDocName;
        TextView tvViewSlots;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvDocName = itemView.findViewById(R.id.tv_doc_name);
            tvViewSlots = itemView.findViewById(R.id.tvview_slots);
        }
    }
}
