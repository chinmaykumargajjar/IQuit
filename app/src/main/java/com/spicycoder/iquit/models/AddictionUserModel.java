package com.spicycoder.iquit.models;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Collections;

public class AddictionUserModel {
    String purpose;
    ArrayList<DateTime> relapseHistory = new ArrayList<DateTime>();
    DateTime lastRelapse = new DateTime();
    AddictionType addiction;
    String TAG = AddictionUserModel.class.toString();

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
        Log.i(TAG, "getNumberOfDays Enter");
        Log.i(TAG, "Position Asked = "+findStreakPosition);
        DateTime previousDateTime = null;
        DateTime currentDateTime = null;
        int currentSize = relapseHistory.size()-1;
        long ans = 0;

        if(currentSize >= findStreakPosition){
            if(findStreakPosition != relapseHistory.size()-1) {
                previousDateTime = relapseHistory.get(findStreakPosition+1);
                currentDateTime = relapseHistory.get(findStreakPosition);
                Log.i(TAG,"findStreakPosition is not NULL, GREATER!");
            } else {
                Log.i(TAG,"No Streak Info return 0");
                return 0; // No Streak information available
            }
        } else if(currentSize < findStreakPosition){
            currentDateTime = new DateTime();
            previousDateTime = relapseHistory.get(findStreakPosition);
            Log.i(TAG,"findStreakPosition is not NULL, LESS!");
        }

        ans = getStreakFromDates(previousDateTime, currentDateTime);

        Log.i(TAG,"getNumberOfDays Done = "+ans );
        return ans;
    }

    public long getCurrentStreak(){
        Log.i(TAG,"getCurrentStreak Enter");
        return getStreakFromDates(lastRelapse, new DateTime());
    }

    public long getStreakFromDates(DateTime updatedRelapseDate, DateTime dateTime){
        Log.i(TAG,"getStreakFromDates Enter");
        Log.i(TAG,"Start:"+updatedRelapseDate.toString()+" Zone="+updatedRelapseDate.getZone().getID());
        Log.i(TAG,"End:"+dateTime.toString()+" Zone="+dateTime.getZone().getID());
        long ans = 0;
        try {
            Interval interval = new Interval(updatedRelapseDate, dateTime);
            ans = interval.toDuration().getStandardDays()-1;
            Log.i(TAG,"ans="+ans);
        } catch(IllegalArgumentException e){
            Log.i(TAG,"Return -2");
            ans = -2;
        }
        if(ans == -1){
            ans=0;
        }
        return ans;
    }
    public void addRelapse(DateTime relapse){
        relapseHistory.add(relapse);
        sortRelapseHistory();
    }

    public void removeRelapse(int position){
        relapseHistory.remove(position);
        sortRelapseHistory();
    }

    public void sortRelapseHistory(){
        Collections.sort(relapseHistory, Collections.reverseOrder());
        lastRelapse = relapseHistory.get(0);
    }
}
