package com.company.covidtracker.Model.HospitalBedsModel;

import java.util.List;

public class Data {
    private Summary summary;
    private List<Source> sources;
    private List<Regional> regional;

    public Data() {

    }

    public Data(Summary summary, List<Source> sources, List<Regional> regional) {
        this.summary = summary;
        this.sources = sources;
        this.regional = regional;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public List<Regional> getRegional() {
        return regional;
    }

    public void setRegional(List<Regional> regional) {
        this.regional = regional;
    }
}
