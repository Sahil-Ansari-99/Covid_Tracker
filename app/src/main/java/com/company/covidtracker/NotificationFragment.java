package com.company.covidtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.covidtracker.Model.NotificationModel.Notification;
import com.company.covidtracker.Model.NotificationModel.NotificationRoot;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private final String NOTIFICATION_API = "https://api.rootnet.in/covid19-in/notifications";
    private ProgressBar progressBar;
    private LinearLayout mainLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainLayout = view.findViewById(R.id.notification_layout_main);
        progressBar = view.findViewById(R.id.notification_progress_bar);

        recyclerView = view.findViewById(R.id.notifications_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getNotifications();
    }

    private void getNotifications() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(NOTIFICATION_API)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Some failure in Notifications call...");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                String responseString = response.body().string();
                final NotificationRoot notificationRoot = gson.fromJson(responseString, NotificationRoot.class);
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            mainLayout.setVisibility(View.VISIBLE);
                            setUpRecyclerview(notificationRoot.getData().getNotifications());
                        }
                    });
                }
            }
        });
    }

    private void setUpRecyclerview(List<Notification> notificationList) {
        adapter = new NotificationAdapter(notificationList, getContext());
        recyclerView.setAdapter(adapter);
    }
}
