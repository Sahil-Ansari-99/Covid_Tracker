package com.company.covidtracker.Model.ContactModel;

import java.util.List;

public class Primary {
    private String number;
    private String number_tollfree;
    private String email;
    private String twitter;
    private String facebook;
    private List<String> media;

    public Primary() {

    }

    public Primary(String number, String number_tollfree, String email, String twitter, String facebook, List<String> media) {
        this.number = number;
        this.number_tollfree = number_tollfree;
        this.email = email;
        this.twitter = twitter;
        this.facebook = facebook;
        this.media = media;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber_tollfree() {
        return number_tollfree;
    }

    public void setNumber_tollfree(String number_tollfree) {
        this.number_tollfree = number_tollfree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }
}
