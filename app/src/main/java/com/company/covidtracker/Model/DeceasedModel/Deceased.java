package com.company.covidtracker.Model.DeceasedModel;

public class Deceased {
    private String patientId;
    private String reportedOn;
    private String onsetEstimate;
    private String ageEstimate;
    private String gender;
    private String city;
    private String district;
    private String state;
    private String status;
    private String notes;
    private String contractedFrom;
    private int dayFromReference;

    public Deceased() {

    }

    public Deceased(String patientId, String reportedOn, String onsetEstimate, String ageEstimate, String gender, String city, String district, String state, String status, String notes, String contractedFrom) {
        this.patientId = patientId;
        this.reportedOn = reportedOn;
        this.onsetEstimate = onsetEstimate;
        this.ageEstimate = ageEstimate;
        this.gender = gender;
        this.city = city;
        this.district = district;
        this.state = state;
        this.status = status;
        this.notes = notes;
        this.contractedFrom = contractedFrom;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getReportedOn() {
        return reportedOn;
    }

    public void setReportedOn(String reportedOn) {
        this.reportedOn = reportedOn;
        this.dayFromReference = calculateDay();
    }

    public String getOnsetEstimate() {
        return onsetEstimate;
    }

    public void setOnsetEstimate(String onsetEstimate) {
        this.onsetEstimate = onsetEstimate;
    }

    public String getAgeEstimate() {
        return ageEstimate;
    }

    public void setAgeEstimate(String ageEstimate) {
        this.ageEstimate = ageEstimate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContractedFrom() {
        return contractedFrom;
    }

    public void setContractedFrom(String contractedFrom) {
        this.contractedFrom = contractedFrom;
    }

    public int getDayFromReference() {
        return dayFromReference;
    }

    public void setDayFromReference(int dayFromReference) {
        this.dayFromReference = dayFromReference;
    }

    private int calculateDay() {
        if (this.reportedOn.isEmpty()) return 0;
        String[] dateArr = this.reportedOn.split("/");
        if (dateArr.length < 3) return 0;
        int day = Integer.valueOf(dateArr[0]);
        int month = Integer.valueOf(dateArr[1]);
        int year = Integer.valueOf(dateArr[2]);
        int dayCount = (year - 2019) * 365 + (month - 1) * 31 + (day - 1);
        return dayCount;
    }
}
