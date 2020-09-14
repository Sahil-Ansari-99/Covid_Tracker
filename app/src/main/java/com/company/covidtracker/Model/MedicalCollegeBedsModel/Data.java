package com.company.covidtracker.Model.MedicalCollegeBedsModel;

import java.util.List;

public class Data {
    private List<MedicalCollege> medicalColleges;
    private List<String> sources;

    public Data() {

    }

    public Data(List<MedicalCollege> medicalColleges, List<String> sources) {
        this.medicalColleges = medicalColleges;
        this.sources = sources;
    }

    public List<MedicalCollege> getMedicalColleges() {
        return medicalColleges;
    }

    public void setMedicalColleges(List<MedicalCollege> medicalColleges) {
        this.medicalColleges = medicalColleges;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }
}
