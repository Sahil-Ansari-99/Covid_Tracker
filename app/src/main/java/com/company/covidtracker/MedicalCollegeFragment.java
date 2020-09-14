package com.company.covidtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.covidtracker.Model.MedicalCollegeBedsModel.MedicalCollege;
import com.company.covidtracker.Model.MedicalCollegeBedsModel.MedicalCollegeRoot;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MedicalCollegeFragment extends Fragment {
    private RecyclerView recyclerView;
    private MedicalCollegesAdapter adapter;
    private final String COLLEGE_API = "https://api.rootnet.in/covid19-in/hospitals/medical-colleges";
    private Spinner stateSpinner, typeSpinner;
    private List<MedicalCollege> currList;
    private List<MedicalCollege> originalList;
    private CheckBox checkBox;
    private String currState, currType;
    private LinearLayout mainLayout;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medicalcolleges, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.medical_colleges_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        stateSpinner = view.findViewById(R.id.medical_colleges_spinner_state);
        typeSpinner = view.findViewById(R.id.medical_colleges_spinner_type);
        checkBox = view.findViewById(R.id.medical_colleges_checkbox);

        progressBar = view.findViewById(R.id.medical_colleges_progress_bar);
        mainLayout = view.findViewById(R.id.medical_colleges_layout_main);

        currList = new ArrayList<>();
        originalList = new ArrayList<>();

        currState = "All States";
        currType = "All Ownership";

        List<String> states = getStateList();
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        List<String> types = getTypeList();
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    currState = adapterView.getItemAtPosition(i).toString();
                    filterStateWise(currState, currType, checkBox.isChecked());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    currType = adapterView.getItemAtPosition(i).toString();
                    filterTypeWise(currType, currState, checkBox.isChecked());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                filterStateWise(currState, currType, b);
            }
        });
        getCollegeData();
    }

    private void getCollegeData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(COLLEGE_API)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Some failure in college request...");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseString = response.body().string();
                Gson gson = new Gson();
                final MedicalCollegeRoot root = gson.fromJson(responseString, MedicalCollegeRoot.class);
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            mainLayout.setVisibility(View.VISIBLE);
                            setUpRecyclerView(root.getData().getMedicalColleges());
                        }
                    });
                }
            }
        });
    }

    private void setUpRecyclerView(List<MedicalCollege> collegeList) {
        originalList.addAll(collegeList);
        currList.addAll(collegeList);
        adapter = new MedicalCollegesAdapter(currList, getContext());
        recyclerView.setAdapter(adapter);
    }

    private List<String> getStateList() {
        List<String> stateList = new ArrayList<>();
        stateList.add("Select State");
        stateList.add("All States");
        stateList.add("Andhra Pradesh");
        stateList.add("Arunachal Pradesh");
        stateList.add("Andaman and Nicobar");
        stateList.add("Assam");
        stateList.add("Bihar");
        stateList.add("Chhattisgarh");
        stateList.add("Dadra and Nagar Haveli");
        stateList.add("Daman & Diu");
        stateList.add("Delhi");
        stateList.add("Goa");
        stateList.add("Gujarat");
        stateList.add("Haryana");
        stateList.add("Himachal Pradesh");
        stateList.add("Jammu and Kashmir");
        stateList.add("Jharkhand");
        stateList.add("Karnataka");
        stateList.add("Kerela");
        stateList.add("Ladakh");
        stateList.add("Lakshadweep");
        stateList.add("Madhya Pradesh");
        stateList.add("Maharastra");
        stateList.add("Manipur");
        stateList.add("Meghalaya");
        stateList.add("Mizoram");
        stateList.add("Nagaland");
        stateList.add("Odisha");
        stateList.add("Puducherry");
        stateList.add("Punjab");
        stateList.add("Rajasthan");
        stateList.add("Sikkim");
        stateList.add("Tamil Nadu");
        stateList.add("Telengana");
        stateList.add("Tripura");
        stateList.add("Uttarakhand");
        stateList.add("Uttar Pradesh");
        stateList.add("West Bengal");
        return stateList;
    }

    private List<String> getTypeList() {
        List<String> types = new ArrayList<>();
        types.add("Ownership");
        types.add("All Ownership");
        types.add("Govt.");
        types.add("Trust");
        types.add("Society");
        types.add("University");
        types.add("Govt-Society");
        types.add("Private");
        return types;
    }

    private void filterStateWise(final String state, String type, boolean applyBoth) {
        if (originalList.size() == 0) {
            Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_LONG).show();
            return;
        }
        currList.clear();
        for (MedicalCollege college: originalList) {
            if (!applyBoth &&
                    (college.getState().equals(state) || state.equals("All States"))) currList.add(college);
            if (applyBoth
                    && (college.getState().equals(state) || state.equals("All States"))
                    && (college.getOwnership() != null && college.getOwnership().equals(type) || type.equals("All Ownership"))) currList.add(college);
        }
//        if (state.equals("All States")) currList.addAll(originalList);
        if (currList.size() == 0) Toast.makeText(getContext(), "No entries match given filter", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    private void filterTypeWise(String type, String state, boolean applyBoth) {
        if (originalList.size() == 0) {
            Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_LONG).show();
            return;
        }
        currList.clear();
        for (MedicalCollege college: originalList) {
            if (college.getOwnership() != null) {
                if (!applyBoth
                        && (college.getOwnership().equals(type) || type.equals("All Ownership"))) currList.add(college);
                if (applyBoth
                        && (college.getState().equals(state) || state.equals("All States"))
                        && (college.getOwnership().equals(type) || type.equals("All Ownership"))) currList.add(college);
            }
        }
//        if (type.equals("All Ownership")) currList.addAll(originalList);
        if (currList.size() == 0) Toast.makeText(getContext(), "No entries match given filter", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
}
