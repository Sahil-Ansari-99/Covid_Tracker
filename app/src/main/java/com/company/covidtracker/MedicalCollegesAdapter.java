package com.company.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.covidtracker.Model.MedicalCollegeBedsModel.MedicalCollege;

import java.util.List;

public class MedicalCollegesAdapter extends RecyclerView.Adapter<MedicalCollegesViewHolder> {
    private List<MedicalCollege> collegeList;
    private Context context;

    public MedicalCollegesAdapter(List<MedicalCollege> collegeList, Context context) {
        this.collegeList = collegeList;
        this.context = context;
    }

    @NonNull
    @Override
    public MedicalCollegesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_medicalcolleges, parent, false);
        return new MedicalCollegesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalCollegesViewHolder holder, int position) {
        MedicalCollege curr = collegeList.get(position);

        holder.state.setText(curr.getState());
        holder.name.setText(curr.getName());
        holder.city.setText(curr.getCity());
        holder.ownership.setText(curr.getOwnership());
        holder.admissionCapacity.setText(String.valueOf(curr.getAdmissionCapacity()));
        holder.hospitalBeds.setText(String.valueOf(curr.getHospitalBeds()));
    }

    @Override
    public int getItemCount() {
        return collegeList.size();
    }
}

class MedicalCollegesViewHolder extends RecyclerView.ViewHolder {
    public TextView state, name, city, ownership, admissionCapacity, hospitalBeds;

    public MedicalCollegesViewHolder(@NonNull View itemView) {
        super(itemView);

        state = itemView.findViewById(R.id.card_medical_colleges_state);
        name = itemView.findViewById(R.id.card_medical_colleges_name);
        city = itemView.findViewById(R.id.card_medical_colleges_city);
        ownership = itemView.findViewById(R.id.card_medical_colleges_owner);
        admissionCapacity = itemView.findViewById(R.id.card_medical_colleges_capacity);
        hospitalBeds = itemView.findViewById(R.id.card_medical_colleges_beds);
    }
}
