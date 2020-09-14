package com.company.covidtracker.Model.HospitalBedsModel;

public class Summary {
    private int ruralHospitals;
    private int ruralBeds;
    private int urbanHospitals;
    private int urbanBeds;
    private int totalHospitals;
    private int totalBeds;

    public Summary() {

    }

    public Summary(int ruralHospitals, int ruralBeds, int urbanHospitals, int urbanBeds, int totalHospitals, int totalBeds) {
        this.ruralHospitals = ruralHospitals;
        this.ruralBeds = ruralBeds;
        this.urbanHospitals = urbanHospitals;
        this.urbanBeds = urbanBeds;
        this.totalHospitals = totalHospitals;
        this.totalBeds = totalBeds;
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
}
