package com.debugcoder.iquit.models;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AddictionUserModel {
    String purpose;
    ArrayList<DateTime> relapseHistory = new ArrayList<DateTime>();
    DateTime lastRelapse = new DateTime();
    AddictionType addiction;

    public AddictionUserModel(String purpose, DateTime lastRelapse, AddictionType addiction) {
        this.purpose = purpose;
        addRelapse(lastRelapse);
        this.addiction = addiction;
    }

    public ArrayList<DateTime> getRelapseHistory() {
        return relapseHistory;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public long getNumberOfDays(int findStreakPosition) {
        Log.i("AddictionUserModel", "getNumberOfDays="+findStreakPosition);
        DateTime previousDateTime = null;
        DateTime currentDateTime = null;
        int currentSize = relapseHistory.size()-1;

        if(currentSize >= findStreakPosition){
            if(findStreakPosition != 0) {
                previousDateTime = relapseHistory.get(findStreakPosition - 1);
                currentDateTime = relapseHistory.get(findStreakPosition);
            } else {
                return 0; // No Streak information available
            }
            Log.i("AddictionUserModel","findStreakPosition is not NULL, GREATER!");
        } else if(currentSize < findStreakPosition){
            currentDateTime = new DateTime();
            previousDateTime = relapseHistory.get(findStreakPosition);
            Log.i("AddictionUserModel","findStreakPosition is not NULL, LESS!");
        }

        try {
            Interval interval = new Interval(previousDateTime, currentDateTime);
            return interval.toDuration().getStandardDays();
        } catch(IllegalArgumentException e){
            return -1;
        }
    }

    public long getCurrentStreak(){
        try {
            Interval interval = new Interval(lastRelapse, new DateTime());
            return interval.toDuration().getStandardDays();
        } catch(IllegalArgumentException e){
            return -1;
        }
    }

    public void addRelapse(DateTime relapse){
        relapseHistory.add(relapse);
        Collections.sort(relapseHistory);
        lastRelapse = relapseHistory.get(relapseHistory.size()-1);
    }

    public void removeRelapse(int position){
        relapseHistory.remove(position);
        Collections.sort(relapseHistory);
        lastRelapse = relapseHistory.get(relapseHistory.size()-1);
    }
}
