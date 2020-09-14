package com.company.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.covidtracker.Model.HospitalBedsModel.Regional;

import java.util.List;

public class HospitalBedsAdapter extends RecyclerView.Adapter<HospitalBedsViewHolder> {
    private List<Regional> hospitalData;
    private Context context;

    public HospitalBedsAdapter(List<Regional> hospitalData, Context context) {
        this.hospitalData = hospitalData;
        this.context = context;
    }

    @NonNull
    @Override
    public HospitalBedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_hospitalbeds, parent, false);
        return new HospitalBedsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalBedsViewHolder holder, int position) {
        Regional curr = hospitalData.get(position);

        holder.state.setText(curr.getState());
        holder.ruralHospitals.setText(String.valueOf(curr.getRuralHospitals()));
        holder.ruralBeds.setText(String.valueOf(curr.getRuralBeds()));
        holder.urbanHospitals.setText(String.valueOf(curr.getUrbanHospitals()));
        holder.urbanBeds.setText(String.valueOf(curr.getUrbanBeds()));
        holder.totalHospitals.setText(String.valueOf(curr.getTotalHospitals()));
        holder.totalBeds.setText(String.valueOf(curr.getTotalBeds()));
        holder.date.setText(curr.getAsOn().toString());
    }

    @Override
    public int getItemCount() {
        return hospitalData.size();
    }
}

class HospitalBedsViewHolder extends RecyclerView.ViewHolder {
    public TextView state, ruralHospitals, ruralBeds, urbanHospitals, urbanBeds, totalHospitals, totalBeds, date;

    public HospitalBedsViewHolder(@NonNull View itemView) {
        super(itemView);

        state = itemView.findViewById(R.id.card_hospital_beds_state);
        ruralHospitals = itemView.findViewById(R.id.card_hospital_beds_rural_hosp);
        ruralBeds = itemView.findViewById(R.id.card_hospital_beds_rural_beds);
        urbanHospitals = itemView.findViewById(R.id.card_hospital_beds_urban_hosp);
        urbanBeds = itemView.findViewById(R.id.card_hospital_beds_urban_beds);
        totalHospitals = itemView.findViewById(R.id.card_hospital_beds_total_hosp);
        totalBeds = itemView.findViewById(R.id.card_hospital_beds_total_beds);
        date = itemView.findViewById(R.id.card_hospital_beds_date);
    }
}
