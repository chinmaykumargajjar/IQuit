package com.spicycoder.iquit.models;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utilities {
    static String TAG = Utilities.class.toString();

    public static String getStringFromDate(DateTime dateTime){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMMM, yyyy");
        return dateTime.toString(fmt);
    }

    public static DateTime getDateFromNumbers( int day, int month, int year){
        Date date = new Date();   // given date
        Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        // gets hour in 24h format
        // calendar.get(Calendar.HOUR);        // gets hour in 12h format

        DateTimeFormatter fmt = DateTimeFormat.forPattern("M/d/yyyy HH:mm")
                .withZone(DateTimeZone.forID(TimeZone.getDefault().getID()));
        DateTime updatedRelapseDate = fmt.parseDateTime(month+"/"+day+"/"
                +year+ " "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));
        return updatedRelapseDate;
    }

    public static boolean saveToStoredData(AddictionManager addictionManager
            , SharedPreferences.Editor editor) {
        String json = null;
        try {
            json = fromManagerToJson(addictionManager);
            Log.i(TAG, "putting String " + json);
        } catch (Exception e){

        }
        if (json != null) {
            editor.putString("user_data", json);
            editor.commit();
        }
        return true;
    }

    private static String fromManagerToJson(AddictionManager addictionManager) throws JSONException {
        JSONArray addictionUserModels = new JSONArray();

        for(AddictionUserModel model:addictionManager.getAddictionUserModels()){
            JSONObject addictionUserModel = new JSONObject();

            addictionUserModel.put(StringsDef.modelPurpose, model.purpose);
            addictionUserModel.put(StringsDef.lastRelapse, model.lastRelapse.getMillis());
            addictionUserModel.put(StringsDef.addiction, model.addiction.getName());

            JSONArray relapseHistoryJson = new JSONArray();
            for(DateTime relapse:model.relapseHistory){
                JSONObject relapseDate = new JSONObject();
                relapseDate.put(StringsDef.relapseDate, relapse.getMillis());
                relapseHistoryJson.put(relapseDate);
            }
            addictionUserModel.put(StringsDef.relapseHistory, relapseHistoryJson);

            addictionUserModels.put(addictionUserModel);
        }

        return addictionUserModels.toString();
    }

    public static AddictionManager retrieveFromStoredData(SharedPreferences pref) {
        String addictionString = pref.getString("user_data", null);
        AddictionManager addictionManager = null;
        Log.i(TAG, "got String "+addictionString);

        if(addictionString != null) {
            try {
                addictionManager = fromJsonToManager(addictionString);
            } catch (JSONException e) {
                e.printStackTrace();
                addictionManager = new AddictionManager();
            }
        } else {
            addictionManager = new AddictionManager();
        }

        return addictionManager;
    }

    private static AddictionManager fromJsonToManager(String addictionString) throws JSONException {
        JSONArray addictionUserModels_Json = new JSONArray(addictionString);

        ArrayList<AddictionUserModel> addictionUserModels = new ArrayList<>();

        for(int i=0; i<addictionUserModels_Json.length(); i++){
            JSONObject addictionUserModel_Json = (JSONObject) addictionUserModels_Json.get(i);
            // Get Purpose
            String purpose = (String) addictionUserModel_Json.get(StringsDef.modelPurpose);

            // Get Relapse History
            ArrayList<DateTime> relapseHistory = new ArrayList<DateTime>();
            JSONArray relapseHistoryJsonArray = (JSONArray) addictionUserModel_Json.get(StringsDef.relapseHistory);
            for(int j=0; j<relapseHistoryJsonArray.length(); j++){
                JSONObject relapseItem = relapseHistoryJsonArray.getJSONObject(j);
                long relapseMills = (long) relapseItem.get(StringsDef.relapseDate);
                DateTime relapseDateTime = new DateTime(relapseMills);
                relapseHistory.add(relapseDateTime);
            }

            // Get Last Relapse
            long lastRelapseMills = (long) addictionUserModel_Json.get(StringsDef.lastRelapse);
            DateTime lastRelapse = new DateTime(lastRelapseMills);

            // Get Addiction Name
            String addictionName = (String) addictionUserModel_Json.get(StringsDef.addiction);
            AddictionType addiction = new AddictionType(addictionName);

            AddictionUserModel addictionUserModel = new AddictionUserModel(purpose, relapseHistory, lastRelapse, addiction);
            addictionUserModels.add(addictionUserModel);
        }

        AddictionManager addictionManager = new AddictionManager();
        addictionManager.setAddictionUserModels(addictionUserModels);

        return addictionManager;
    }
}
