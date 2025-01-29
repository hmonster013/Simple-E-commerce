package com.de013.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils {
    private final static Logger log = LoggerFactory.getLogger(DateUtils.class);

    public DateUtils() {

    }

    public static Date addDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        
        return calendar.getTime();
    }

    public static Date addDate(int day) {
        return addDate(new Date(), day);
    }

    public static String toddMMyyyyHHmmss(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(JConstants.ddMMyyyyHHmmss);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static Date toDateddMMyyyyHHmmss(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(JConstants.ddMMyyyyHHmmss);
        java.util.Date date;
        try {
            date = dateFormat.parse(dateString);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
           
            return null;
        }
    }

    public static String toString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(JConstants.DD_MM_YYYY);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();

            return "";
        }
    }
}
