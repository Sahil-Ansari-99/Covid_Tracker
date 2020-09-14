package com.company.covidtracker.Model.HospitalBedsModel;

import java.util.Date;

public class Regional {
    private String state;
    private int ruralHospitals;
    private int ruralBeds;
    private int urbanHospitals;
    private int urbanBeds;
    private int totalHospitals;
    private int totalBeds;
    private Date asOn;

    public Regional() {

    }

    public Regional(String state, int ruralHospitals, int ruralBeds, int urbanHospitals, int urbanBeds, int totalHospitals, int totalBeds, Date asOn) {
        this.state = state;
        this.ruralHospitals = ruralHospitals;
        this.ruralBeds = ruralBeds;
        this.urbanHospitals = urbanHospitals;
        this.urbanBeds = urbanBeds;
        this.totalHospitals = totalHospitals;
        this.totalBeds = totalBeds;
        this.asOn = asOn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRuralHospitals() {
        return ruralHospitals;
    }

    public void setRuralHospitals(int ruralHospitals) {
        this.ruralHospitals = ruralHospitals;
    }

    public int getRuralBeds() {
        return ruralBeds;
    }

    public void setRuralBeds(int ruralBeds) {
        this.ruralBeds = ruralBeds;
    }

    public int getUrbanHospitals() {
        return urbanHospitals;
    }

    public void setUrbanHospitals(int urbanHospitals) {
        this.urbanHospitals = urbanHospitals;
    }

    public int getUrbanBeds() {
        return urbanBeds;
    }

    public void setUrbanBeds(int urbanBeds) {
        this.urbanBeds = urbanBeds;
    }

    public int getTotalHospitals() {
        return totalHospitals;
    }

    public void setTotalHospitals(int totalHospitals) {
        this.totalHospitals = totalHospitals;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
    }

    public Date getAsOn() {
        return asOn;
    }

    public void setAsOn(Date asOn) {
        this.asOn = asOn;
    }
}
