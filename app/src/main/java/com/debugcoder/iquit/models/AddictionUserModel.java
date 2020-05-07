package com.debugcoder.iquit.models;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;

import java.util.ArrayList;

public class AddictionUserModel {
    String purpose;
    ArrayList<DateTime> relapseHistory = new ArrayList<DateTime>();
    DateTime lastRelapse = new DateTime();
    AddictionType addiction;

    public AddictionUserModel(String purpose, ArrayList<DateTime> relapseHistory, DateTime lastRelapse, AddictionType addiction) {
        this.purpose = purpose;
        this.relapseHistory = relapseHistory;
        this.lastRelapse = lastRelapse;
        this.addiction = addiction;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public long getNumberOfDays() {
        Interval interval = new Interval(lastRelapse, new Instant());
        return interval.toDuration().getStandardDays();
    }
}
