package com.codepath.apps.restclienttemplate.utils;

import android.net.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DhwaniShah on 3/21/17.
 */

public class TimeCalculation {
    int years;
    int months;
    int days;
    int hours;
    int minutes;
    int seconds;
    String differenceString;

    public static Date getTwitterDate(String date) throws ParseException {

        final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
        sf.setLenient(true);
        try {
            return sf.parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TimeCalculation(Date curdate, Date olddate) {

        float diff=curdate.getTime() - olddate.getTime();
        if (diff >= 0) {
            int yearDiff = Math.round( ( diff/ (365l*2592000000f))>=1?( diff/ (365l*2592000000f)):0);
            if (yearDiff > 0) {
                years = yearDiff;
                setDifferenceString(years + "yr");
            } else {
                int monthDiff = Math.round((diff / 2592000000f)>=1?(diff / 2592000000f):0);
                if (monthDiff > 0) {
                    if (monthDiff > 11)
                        monthDiff = 11;

                    months = monthDiff;
                    setDifferenceString(months + "mo");
                } else {
                    int dayDiff = Math.round((diff / (86400000f))>=1?(diff / (86400000f)):0);
                    if (dayDiff > 0) {
                        days = dayDiff;
                        if(days==30)
                            days=29;
                        setDifferenceString(days + "d");
                    } else {
                        int hourDiff = Math.round((diff / (3600000f))>=1?(diff / (3600000f)):0);
                        if (hourDiff > 0) {
                            hours = hourDiff;
                            setDifferenceString( hours + "h");
                        } else {
                            int minuteDiff = Math.round((diff / (60000f))>=1?(diff / (60000f)):0);
                            if (minuteDiff > 0) {
                                minutes = minuteDiff;
                                setDifferenceString(minutes + "m");
                            } else {
                                int secondDiff =Math.round((diff / (1000f))>=1?(diff / (1000f)):0);
                                if (secondDiff > 0)
                                    seconds = secondDiff;
                                else
                                    seconds = 1;
                                setDifferenceString(seconds + "s");
                            }
                        }
                    }

                }
            }

        }

    }
    public String getDifferenceString() {
        return differenceString;
    }

    public void setDifferenceString(String differenceString) {
        this.differenceString = differenceString;
    }
    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
