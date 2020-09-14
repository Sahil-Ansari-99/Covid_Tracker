package com.company.covidtracker.Model.ContactModel;

import java.util.List;

public class Contacts {
    private Primary primary;
    private List<Regional> regional;

    public Contacts() {

    }

    public Contacts(Primary primary, List<Regional> regional) {
        this.primary = primary;
        this.regional = regional;
    }

    public Primary getPrimary() {
        return primary;
    }

    public void setPrimary(Primary primary) {
        this.primary = primary;
    }

    public List<Regional> getRegional() {
        return regional;
    }

    public void setRegional(List<Regional> regional) {
        this.regional = regional;
    }
}
