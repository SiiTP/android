package com.dbtest.ivan.app.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ivan on 24.05.2016.
 */
public class DateUtils {
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime();
    }
}
