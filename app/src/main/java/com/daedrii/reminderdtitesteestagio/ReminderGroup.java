package com.daedrii.reminderdtitesteestagio;

import java.util.ArrayList;

public class ReminderGroup {

    private String date;
    private ArrayList<Reminder> reminders;

    public void setDate(String date) {
        this.date = date;
    }

    public void setReminders(ArrayList<Reminder> reminders) {
        this.reminders = reminders;
    }

    public ReminderGroup(String date){
        this.date = date;
        this.reminders = new ArrayList<Reminder>();
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }
}
