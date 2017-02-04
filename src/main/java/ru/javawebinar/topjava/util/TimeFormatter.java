package ru.javawebinar.topjava.util;


import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class TimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        return parseLocalTime(s);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
