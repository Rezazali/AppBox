package com.rezazali.qiba.qiba.utily;


import android.content.Context;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtils {

    Context context;
    long diff;

    private DateUtils() {

    }

    public DateUtils(Context context) {
        this.context = context;
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }





    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    /**
     * Used to Convert date to Calendar
     *
     * @param date get Date

     */
    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


    public static boolean holidayCheck(int month, int day) {
        boolean result = false;


        if (month == 9 && day == 27) {
            result = true;
        }
        if (month == 3 && day == 12) {
            result = true;
        }
        if (month == 1 && day == 1) {
            result = true;
        }
        if (month == 12 && day == 10) {
            result = true;
        }
        if (month == 1 && day == 10) {
            result = true;
        }
        if (month == 9 && day == 1) {
            result = true;
        }

        if (month == 7 && day == 27) {
            result = true;
        }


        return result;
    }

    /**
     * Used to Convert timeStamp to DateTime
     *
     * @param month Time stamp value ScrollView
     * @param day Time stamp value ScrollView
     */
    public static String holiday(int month, int day) {

        String messag = "";
        boolean result = holidayCheck(month, day);


        if (result == false) {
            messag = "";
        } else {


            if (month == 9 && day == 27) {
                messag = "Lailat al-Qadr";
            }
            if (month == 3 && day == 12) {
                messag = "The Prophet&apos;s Birthday";
            }
            if (month == 1 && day == 1) {
                messag = "Muharram";
            }
            if (month == 12 && day == 10) {
                messag = "Eid al-Adha";
            }
            if (month == 1 && day == 10) {
                messag = "Eid al-Fitr";
            }
            if (month == 9 && day == 1) {
                messag = "Ramadan starts";
            }

            if (month == 7 && day == 27) {
                messag = "Isra and Mi&apos;raj";
            }

        }


        return messag;
    }


/*    public static DateTime getDateTimeFromTimestamp(Long value) {
        TimeZone timeZone = TimeZone.getDefault();
        long offset = timeZone.getOffset(value);
        if (offset < 0) {
            value -= offset;
        } else {
            value += offset;
        }
        return new  DateTime(value);
    }*/


    public static Date getMyTime() {


        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date myDate = new Date(stamp.getTime());

        return myDate;

    }

    public static String getInstallDate() {


        Timestamp stamp = new Timestamp(System.currentTimeMillis());

        return stamp.toString();

    }

    public static int getDiffrenceDates(String currentDate,String installDate) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date d1 = null;
        Date d2 = null;

        d1 = format.parse(currentDate);
        d2 = format.parse(installDate);

        //in milliseconds
        long diff = d2.getTime() - d1.getTime();

        long diffDays = diff / (24 * 60 * 60 * 1000);


        return (int)diffDays;

    }


    public static int checkDate(String dayName){

        int day = 1;

        if(dayName.equals("یکشنبه")){
            day= 1;
        }
        else if(dayName.equals("یکشنبه")){
            day= 2;
        }
        else if(dayName.equals("دوشنبه")){
            day= 3;
        }
        else if(dayName.equals("سه شنبه")){
            day= 4;
        }
        else if(dayName.equals("چهارشنبه")){
            day= 5;
        }
        else if(dayName.equals("پنج شنبه")){
            day= 6;
        }
        else if(dayName.equals("جمعه")){
            day= 7;
        }


        return day;

    }



}

