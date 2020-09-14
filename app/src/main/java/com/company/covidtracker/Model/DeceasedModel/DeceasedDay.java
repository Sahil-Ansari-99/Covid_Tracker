package com.company.covidtracker.Model.DeceasedModel;

public class DeceasedDay {
    private String date;
    private int deceased;
    private int dayFromReference;

    public DeceasedDay() {

    }

    public DeceasedDay(String date) {
        this.date = date;
        this.dayFromReference = calculateDay();
    }

    public DeceasedDay(String date, int deceased, int dayFromReference) {
        this.date = date;
        this.deceased = deceased;
        this.dayFromReference = dayFromReference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        this.dayFromReference = calculateDay();
    }

    public int getDeceased() {
        return deceased;
    }

    public void setDeceased(int deceased) {
        this.deceased = deceased;
    }

    public int getDayFromReference() {
        return dayFromReference;
    }

    public void setDayFromReference(int dayFromReference) {
        this.dayFromReference = dayFromReference;
    }

    public void increaseDeceased() {
        this.deceased++;
    }

    private int calculateDay() {
        if (this.date.isEmpty()) return 0;
        String[] dateArr = this.date.split("/");
        if (dateArr.length < 3) return 0;
        int day = Integer.valueOf(dateArr[0]);
        int month = Integer.valueOf(dateArr[1]);
        int year = Integer.valueOf(dateArr[2]);
        int dayCount = (year - 2019) * 365 + (month - 1) * 31 + (day - 1);
        return dayCount;
    }
}
