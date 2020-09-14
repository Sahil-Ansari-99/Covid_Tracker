package com.company.covidtracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.company.covidtracker.Model.NotificationModel.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder>{
    private List<Notification> notifications;
    private Context context;

    public NotificationAdapter(List<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;

    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final Notification curr = notifications.get(position);

        holder.title.setText(curr.getTitle());
        holder.link.setText(curr.getLink());
        holder.date.setText(curr.getDate());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowserURL(curr.getLink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    private void openBrowserURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}

class NotificationViewHolder extends RecyclerView.ViewHolder {
    public TextView title, link, date;
    public CardView cardView;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.card_notification_title);
        link = itemView.findViewById(R.id.card_notification_link);
        date = itemView.findViewById(R.id.card_notification_date);

        cardView = itemView.findViewById(R.id.card_notification_card);
    }
}
