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

import com.company.covidtracker.Model.HospitalBedsModel.HospitalBedsRoot;
import com.company.covidtracker.Model.HospitalBedsModel.Regional;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HospitalBedsFragment extends Fragment {
    private RecyclerView recyclerView;
    private HospitalBedsAdapter adapter;
    private final String HOSPITAL_BEDS_API = "https://api.rootnet.in/covid19-in/hospitals/beds";
    private ProgressBar progressBar;
    private LinearLayout mainLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hospitalbeds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainLayout = view.findViewById(R.id.hospital_beds_layout_main);
        progressBar = view.findViewById(R.id.hospital_beds_progress_bar);

        recyclerView = view.findViewById(R.id.hospital_beds_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getHospitalData();
    }

    private void getHospitalData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(HOSPITAL_BEDS_API)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Some failure in hospital beds...");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                String responseString = response.body().string();
                final HospitalBedsRoot hospitalBedsRoot = gson.fromJson(responseString, HospitalBedsRoot.class);
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            mainLayout.setVisibility(View.VISIBLE);
                            setUpRecyclerView(hospitalBedsRoot.getData().getRegional());
                        }
                    });
                }
            }
        });
    }

    private void setUpRecyclerView(List<Regional> hospitalData) {
        adapter = new HospitalBedsAdapter(hospitalData, getContext());
        recyclerView.setAdapter(adapter);
    }
}
