package com.company.covidtracker.Model.NotificationModel;

import java.util.List;

public class Data {
    private List<Notification> notifications;

    public Data() {

    }

    public Data(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
