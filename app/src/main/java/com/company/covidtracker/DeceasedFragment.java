package com.company.covidtracker;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.company.covidtracker.Model.DeceasedModel.Deceased;
import com.company.covidtracker.Model.DeceasedModel.DeceasedDay;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DeceasedFragment extends Fragment {
    private List<Deceased> dataList;
    private List<Deceased> currDataList;
    private List<DeceasedDay> dateWiseList;
    private LineChart lineChart;
    private Spinner stateSpinner, ageSpinner, genderSpinner;
    private String currState, currAge, currGender, fromDate, toDate;
    private int minAge, maxAge;
    private TextView fromDateTV, toDateTV;
    private Button fromDateBtn, toDateBtn, applyFilters, downloadButton, sendEmail;
    private final int WRITE_STORAGE = 1999;
    private LinearLayout graphLayout;
    private EditText emailET;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deceased, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lineChart = view.findViewById(R.id.deceased_line_chart);

        stateSpinner = view.findViewById(R.id.deceased_spinner_state);
        ageSpinner = view.findViewById(R.id.deceased_spinner_age);
        genderSpinner = view.findViewById(R.id.deceased_spinner_gender);

        fromDateTV = view.findViewById(R.id.deceased_date_from);
        toDateTV = view.findViewById(R.id.deceased_date_to);

        fromDateBtn = view.findViewById(R.id.deceased_button_from);
        toDateBtn = view.findViewById(R.id.deceased_button_to);
        applyFilters = view.findViewById(R.id.deceased_button_apply);
        downloadButton = view.findViewById(R.id.deceased_button_download);
        sendEmail = view.findViewById(R.id.deceased_button_email);

        emailET = view.findViewById(R.id.deceased_et_email);

        graphLayout = view.findViewById(R.id.deceased_layout_graph);

        currState = "All States";
        currAge = "All Age Groups";
        currGender = "All";
        minAge = 0;
        maxAge = 200;

        List<String> states = getStateList();
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        final List<String> ages = getAgeGroupList();
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ages);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);

        List<String> genders = getGenderList();
        ArrayAdapter<String> genderAdapter =  new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        dataList = readCSVData();
        currDataList = new ArrayList<>();
        dateWiseList = buildDateWiseList(dataList);
        sortDateWise(dateWiseList);

        fromDate = dateWiseList.get(0).getDate();
        toDate = dateWiseList.get(dateWiseList.size()-1).getDate();

        fromDateTV.setText(fromDate);
        toDateTV.setText(toDate);

        setUpGraphData(dateWiseList);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currState = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currAge = adapterView.getItemAtPosition(i).toString();
                if (i == 0) {
                    minAge = 0;
                    maxAge = 200;
                }
                else if (i == ages.size()-1) minAge = 70;
                else {
                    String ageGroup = adapterView.getItemAtPosition(i).toString();
                    String[] curr = ageGroup.split("-");
                    minAge = Integer.valueOf(curr[0]);
                    maxAge = Integer.valueOf(curr[1]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currGender = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fromDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(true);
            }
        });

        toDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(false);
            }
        });

        applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int from = calculateDay(fromDate);
                int to = calculateDay(toDate);
                buildFilteredList(currState, currGender, from, to, minAge, maxAge);
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    System.out.println("Permission granted already...");
                    saveImage(graphLayout, "graph");
                    savePDF();
                } else {
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_STORAGE);
                }
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                if (email.isEmpty()) Toast.makeText(getContext(), "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                else emailPDF(email);
            }
        });
    }

    private List<Deceased> readCSVData() {
        List<Deceased> data = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(getActivity().getAssets().open("covid19india.csv")));
            String[] nextLine;
            while((nextLine = csvReader.readNext()) != null) {
                Deceased curr = new Deceased();
                curr.setPatientId(nextLine[0]);
                curr.setReportedOn(nextLine[1]);
                curr.setOnsetEstimate(nextLine[2]);
                curr.setAgeEstimate(nextLine[3]);
                curr.setGender(nextLine[4]);
                curr.setCity(nextLine[5]);
                curr.setDistrict(nextLine[6]);
                curr.setState(nextLine[7]);
                curr.setStatus(nextLine[8]);
                curr.setNotes(nextLine[9]);
                curr.setContractedFrom(nextLine[10]);
                data.add(curr);
            }
            System.out.println(data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private List<DeceasedDay> buildDateWiseList(List<Deceased> data) {
        List<DeceasedDay> dateWiseList = new ArrayList<>();
        HashMap<String, Integer> dateIdx = new HashMap<>();

        for (int i = 1; i < data.size(); i++) {
            Deceased curr = data.get(i);
            int idx;
            if (dateIdx.containsKey(curr.getReportedOn())) idx = dateIdx.get(curr.getReportedOn());
            else {
                idx = dateWiseList.size();
                dateWiseList.add(new DeceasedDay(curr.getReportedOn()));
                dateIdx.put(curr.getReportedOn(), idx);
            }
            dateWiseList.get(idx).increaseDeceased();
        }

        return dateWiseList;
    }

    private void buildFilteredList(String state, String gender, int from, int to, int minAge, int maxAge) {
        int x = 0;
        currDataList.clear();
        for (int i = 1; i < dataList.size(); i++) {
            Deceased curr = dataList.get(i);
            String ageEst = curr.getAgeEstimate();
            int age;
            String[] ageArr = ageEst.split("-");
            if (ageArr.length > 1) {
                age = (Integer.valueOf(ageArr[0]) + Integer.valueOf(ageArr[1])) / 2;
            }
            else {
                try {
                    age = (curr.getAgeEstimate() == null || curr.getAgeEstimate().isEmpty()) ? minAge : Integer.valueOf(curr.getAgeEstimate());
                } catch (NumberFormatException e) {
                    age = 0;
                }
            }
            if ((state.equals("All States") || state.equals(curr.getState()))
            && (gender.equals("All") || gender.equals(curr.getGender()))
            && (curr.getDayFromReference() >= from && curr.getDayFromReference() <= to)
            && (age >= minAge && age <= maxAge)) {
                currDataList.add(curr);
            }
        }
        if (currDataList.size() == 0) Toast.makeText(getContext(), "No entries match your filters...", Toast.LENGTH_SHORT).show();
        System.out.println("Filtered List size:" + currDataList.size());
        System.out.println(x);
        plotFilteredData(currDataList);
    }

    private void plotFilteredData(List<Deceased> data) {
        List<DeceasedDay> dateWise = buildDateWiseList(data);
        sortDateWise(dateWise);
        setUpGraphData(dateWise);
    }

    private void sortDateWise(List<DeceasedDay> data) {
        Collections.sort(data, new Comparator<DeceasedDay>() {
            @Override
            public int compare(DeceasedDay t1, DeceasedDay t2) {
                return t1.getDayFromReference() - t2.getDayFromReference();
            }
        });
    }

    private void setUpGraphData(List<DeceasedDay> data) {
        List<Entry> deceased = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            deceased.add(new BarEntry(data.get(i).getDeceased(), i));
            dates.add(data.get(i).getDate());
        }
        buildLineChart(deceased, dates, lineChart);
    }

    private void buildLineChart(List<Entry> yData, List<String> xData, LineChart chartToDraw) {
        LineDataSet lineDataSet = new LineDataSet(yData, "label");
        lineDataSet.setColor(ContextCompat.getColor(getContext(), R.color.blue));
        lineDataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.grey));
        XAxis xAxis = chartToDraw.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LineData lineData = new LineData(xData, lineDataSet);
        lineData.setValueFormatter(new CustomValueFormatter());
        chartToDraw.setData(lineData);
        chartToDraw.getAxisLeft().setDrawGridLines(false);
        chartToDraw.getXAxis().setDrawGridLines(false);
        chartToDraw.setDescription("");
        chartToDraw.getLegend().setEnabled(false);
        chartToDraw.invalidate();
    }

    private List<String> getStateList() {
        List<String> stateList = new ArrayList<>();
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

    private List<String> getAgeGroupList() {
        List<String> ageList = new ArrayList<>();
        ageList.add("All Age Groups");
        ageList.add("0-9");
        ageList.add("10-19");
        ageList.add("20-29");
        ageList.add("30-39");
        ageList.add("40-49");
        ageList.add("50-59");
        ageList.add("60-69");
        ageList.add("70 and above");
        return ageList;
    }

    private List<String> getGenderList() {
        List<String> genders = new ArrayList<>();
        genders.add("All");
        genders.add("male");
        genders.add("female");
        return genders;
    }

    private void showDateDialog(final boolean isFrom) {
        String[] currArr;
        if (isFrom) currArr = fromDate.split("/");
        else currArr = toDate.split("/");

        int currDay = Integer.valueOf(currArr[0]);
        int currMonth = Integer.valueOf(currArr[1]);
        int currYear = Integer.valueOf(currArr[2]);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if (isFrom) {
                    fromDate = i2 + "/" + (i1 + 1) + "/" + i;
                    fromDateTV.setText(fromDate);
                } else {
                    toDate = i2 + "/" + (i1 + 1) + "/" + i;
                    toDateTV.setText(toDate);
                }
            }
        }, currYear, currMonth - 1, currDay);
        datePickerDialog.show();
    }

    private int calculateDay(String date) {
        if (date.isEmpty()) return 0;
        String[] dateArr = date.split("/");
        if (dateArr.length < 3) return 0;
        int day = Integer.valueOf(dateArr[0]);
        int month = Integer.valueOf(dateArr[1]);
        int year = Integer.valueOf(dateArr[2]);
        int dayCount = (year - 2019) * 365 + (month - 1) * 31 + (day - 1);
        return dayCount;
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{permission},
                requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission granted");
                } else {
                    Toast.makeText(getContext(), "Storage permission is required", Toast.LENGTH_LONG).show();
                }
        }
    }

    private void saveImage(View view, String name) {
        int width = view.getWidth();
        int height = view.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);

        Drawable bg = view.getBackground();

        if (bg != null) bg.draw(c);
        else c.drawColor(Color.WHITE);

        view.draw(c);

        System.out.println(c.getHeight());
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + name + ".jpg");
        if (f.exists()) {
            if (f.delete()) System.out.println("File deleted");
        }
        try {
            FileOutputStream fos = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
            System.out.println("Image saved");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void savePDF() {
        try {
            Document document = new Document();
            document.setPageSize(PageSize.A4);
            String dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + File.separator + "graph.pdf"));
            document.open();

            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "graph.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;

//            img.setRotationDegrees(90);
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);

            document.close();

            Toast.makeText(getActivity(), "PDF Generated successfully!...Check internal storage root directory.", Toast.LENGTH_SHORT).show();
            System.out.println("PDF generated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void emailPDF(String email) {
        String dirpath = android.os.Environment.getExternalStorageDirectory().toString();
        String pathToFile = dirpath + File.separator + "graph.pdf";
        File file = new File(pathToFile);
        if (!file.exists()) {
            Toast.makeText(getContext(), "Please download graph first!", Toast.LENGTH_LONG).show();
        } else {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            Uri uri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", file);
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(emailIntent, "Choose email provider..."));
        }
    }
}
