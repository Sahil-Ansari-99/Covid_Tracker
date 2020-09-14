package com.company.covidtracker;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class CustomValueFormatter implements ValueFormatter {
    private DecimalFormat decimalFormat;

    public CustomValueFormatter() {
//        decimalFormat = new DecimalFormat("###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        int val = (int)value;
        return String.valueOf(val);
    }
}