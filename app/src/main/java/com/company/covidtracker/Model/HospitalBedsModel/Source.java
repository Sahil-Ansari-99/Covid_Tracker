package com.company.covidtracker.Model.HospitalBedsModel;

import java.util.Date;

public class Source {
    private String url;
    private Date lastUpdated;

    public Source() {

    }

    public Source(String url, Date lastUpdated) {
        this.url = url;
        this.lastUpdated = lastUpdated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
