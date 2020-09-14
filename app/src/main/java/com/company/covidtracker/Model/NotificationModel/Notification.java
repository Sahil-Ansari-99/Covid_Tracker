package com.company.covidtracker.Model.NotificationModel;

public class Notification {
    private String title;
    private String link;
    private String date;

    public Notification() {

    }

    public Notification(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        String[] arr = title.split(" ");
        if (arr.length < 2) return title;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < arr.length; i++) {
            sb.append(arr[i]);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        String[] arr = title.split(" ");
        if (arr.length < 2) return "";
        else return arr[0];
    }

    public void setDate(String date) {
        this.date = date;
    }
}
