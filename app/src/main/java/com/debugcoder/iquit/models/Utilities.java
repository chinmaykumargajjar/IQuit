package com.debugcoder.iquit.models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utilities {
    public static String getStringFromDate(DateTime dateTime){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMMM, yyyy");
        return dateTime.toString(fmt);
    }
}
