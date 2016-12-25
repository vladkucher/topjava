package ru.javawebinar.topjava.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class DateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /*public static boolean isBetweenTime(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }*/

    public static boolean isBetween(LocalDateTime ld, LocalDateTime startDateTime, LocalDateTime endDateTime){
        return ld.compareTo(startDateTime) >=0 && ld.compareTo(endDateTime) <=0;
    }

    public static LocalDate parseToDate(String date, LocalDate localDate){
        if(date.equals(""))
            return localDate;
        else
            return LocalDate.parse(date,DATE_FORMATTER);
    }
    public static LocalTime parseToTime(String time, LocalTime localTime){
        if(time.equals(""))
            return localTime;
        else
            return LocalTime.parse(time,TIME_FORMATTER);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
