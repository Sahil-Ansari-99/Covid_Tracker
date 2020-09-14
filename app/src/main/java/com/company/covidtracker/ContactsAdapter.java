package com.company.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.covidtracker.Model.ContactModel.Regional;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder>{
    private List<Regional> contactList;
    private Context context;

    public ContactsAdapter(List<Regional> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_contacts, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        Regional curr = contactList.get(position);

        holder.state.setText(curr.getLoc());
        holder.number.setText(curr.getNumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}

class ContactsViewHolder extends RecyclerView.ViewHolder {
    public TextView state, number;

    public ContactsViewHolder(@NonNull View itemView) {
        super(itemView);

        state = itemView.findViewById(R.id.card_contacts_state);
        number = itemView.findViewById(R.id.card_contacts_number);
    }
}
