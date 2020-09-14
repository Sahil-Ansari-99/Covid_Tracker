package com.company.covidtracker.Model.MedicalCollegeBedsModel;

import java.util.Date;

public class MedicalCollegeRoot {
    private boolean success;
    private Data data;
    private Date lastRefreshed;
    private Date lastOriginUpdate;

    public MedicalCollegeRoot() {

    }

    public MedicalCollegeRoot(boolean success, Data data, Date lastRefreshed, Date lastOriginUpdate) {
        this.success = success;
        this.data = data;
        this.lastRefreshed = lastRefreshed;
        this.lastOriginUpdate = lastOriginUpdate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Date getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(Date lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }

    public Date getLastOriginUpdate() {
        return lastOriginUpdate;
    }

    public void setLastOriginUpdate(Date lastOriginUpdate) {
        this.lastOriginUpdate = lastOriginUpdate;
    }
}
