package com.company.covidtracker.Model.MedicalCollegeBedsModel;

public class MedicalCollege {
    private String state;
    private String name;
    private String city;
    private String ownership;
    private int admissionCapacity;
    private int hospitalBeds;

    public MedicalCollege() {

    }

    public MedicalCollege(String state, String name, String city, String ownership, int admissionCapacity, int hospitalBeds) {
        this.state = state;
        this.name = name;
        this.city = city;
        this.ownership = ownership;
        this.admissionCapacity = admissionCapacity;
        this.hospitalBeds = hospitalBeds;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public int getAdmissionCapacity() {
        return admissionCapacity;
    }

    public void setAdmissionCapacity(int admissionCapacity) {
        this.admissionCapacity = admissionCapacity;
    }

    public int getHospitalBeds() {
        return hospitalBeds;
    }

    public void setHospitalBeds(int hospitalBeds) {
        this.hospitalBeds = hospitalBeds;
    }
}
