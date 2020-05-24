package com.debugcoder.iquit.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utilities {
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
}
