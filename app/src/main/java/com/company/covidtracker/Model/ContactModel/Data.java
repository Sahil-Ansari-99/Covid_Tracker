package com.company.covidtracker.Model.ContactModel;

public class Data {
    private Contacts contacts;

    public Data() {

    }

    public Data(Contacts contacts) {
        this.contacts = contacts;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }
}
