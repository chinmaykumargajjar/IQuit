package com.debugcoder.iquit.models;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;

import java.util.ArrayList;

public class AddictionUserModel {
    String name;
    String purpose;

    DateTime startDate = new DateTime();
    ArrayList<DateTime> relapseHistory = new ArrayList<DateTime>();
    DateTime lastRelapse = new DateTime();
    AddictionType addiction;

    public AddictionUserModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public ArrayList<DateTime> getRelapseHistory() {
        return relapseHistory;
    }

    public void setRelapseHistory(ArrayList<DateTime> relapseHistory) {
        this.relapseHistory = relapseHistory;
    }

    public AddictionType getAddiction() {
        return addiction;
    }

    public void setAddiction(AddictionType addiction) {
        this.addiction = addiction;
    }

    public DateTime getLastRelapse() {
        return lastRelapse;
    }

    public void setLastRelapse(DateTime lastRelapse) {
        this.lastRelapse = lastRelapse;
    }

    public int getNumberOfDays() {
        Interval interval = new Interval(lastRelapse, new Instant());
        return interval.toPeriod().getDays();
    }
}
