package com.company.covidtracker.Model.ContactModel;

public class Regional {
    private String loc;
    private String number;

    public Regional() {

    }

    public Regional(String loc, String number) {
        this.loc = loc;
        this.number = number;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
